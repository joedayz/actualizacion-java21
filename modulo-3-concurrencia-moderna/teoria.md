# Módulo 3. Concurrencia Moderna con Java 21 (5 horas)

> Objetivo: pasar de la concurrencia “clásica” (threads de plataforma, pools, `CompletableFuture`) a **Virtual Threads** (Project Loom) y conocer **Structured Concurrency** y **Scoped Values** (preview en Java 21).
>
> Ejercicios Maven + JUnit 5: [`ejercicios/`](./ejercicios/).
> Soluciones del facilitador (local, no en el repo): `soluciones-modulo-3/`.

---

## Sección 1: Revisión de concurrencia en Java 8 (45 min)

### El modelo clásico
- Cada `Thread` de Java se mapea a un **thread del sistema operativo** (platform thread).
- Crear miles de threads es **caro** (stack ~1 MB, scheduling del OS).
- Por eso en Java 8 se usan **pools** (`ExecutorService`) y se evita `new Thread()` a mansalva.

### Piezas que ya conocen
| API | Rol |
|-----|-----|
| `Thread` / `Runnable` | Unidad de ejecución |
| `synchronized` / locks | Exclusión mutua |
| `ExecutorService` | Pool de workers reutilizables |
| `Future` | Resultado diferido de una tarea |
| `CompletableFuture` (Java 8) | Composición asíncrona sin bloquear el hilo llamador (idealmente) |

### El problema de fondo (I/O-bound)
En servidores típicos (HTTP, JDBC, llamadas a APIs) el hilo **pasa la mayor parte del tiempo bloqueado** esperando I/O.
Con platform threads, un pool de 200 hilos = como máximo ~200 requests bloqueantes en paralelo, aunque la CPU esté casi idle.

### Demo
Ver `demos/01-concurrencia-java8/ConcurrenciaJava8.java`.

---

## Sección 2: ExecutorService y CompletableFuture (50 min)

### ExecutorService
```java
ExecutorService pool = Executors.newFixedThreadPool(10);
Future<String> f = pool.submit(() -> llamarApi());
String resultado = f.get(); // bloquea el hilo actual hasta tener el valor
pool.shutdown();
```

Patrones habituales:
- `newFixedThreadPool(n)` — tamaño fijo (CPU-bound: ~núcleos; I/O-bound: se infla y duele).
- `newCachedThreadPool()` — crece sin límite (peligroso bajo carga).
- `invokeAll` / `invokeAny` — varias tareas a la vez.

### CompletableFuture
Permite **encadenar** trabajo asíncrono:

```java
CompletableFuture.supplyAsync(() -> fetchUser(id))
    .thenApply(user -> user.name())
    .thenCompose(name -> fetchOrders(name))
    .thenAccept(orders -> System.out.println(orders));
```

Métodos clave:
- `supplyAsync` / `runAsync` — arrancan trabajo (por defecto en `ForkJoinPool.commonPool()`).
- `thenApply` — transforma el valor (como `map`).
- `thenCompose` — encadena otro `CompletableFuture` (como `flatMap`).
- `thenCombine` / `allOf` — combina varios futures.
- `exceptionally` / `handle` — errores.

**Limitación:** si dentro de un stage haces I/O bloqueante en el `commonPool()`, puedes **agotar** el pool compartido de la JVM. Por eso en código real se pasa un `Executor` propio.

### Demo
Ver `demos/02-executor-completablefuture/ExecutorCompletableFuture.java`.

### Práctica
```bash
cd ejercicios && mvn test -Dtest=ConcurrenciaClasicaTest
cd ejercicios && mvn test -Dtest=FuturesModernosTest
```

---

## Sección 3: Project Loom — Virtual Threads (70 min)

### Idea central
Un **virtual thread** es un thread “ligero” gestionado por la JVM, no por el OS uno-a-uno.
- Puedes tener **millones** de virtual threads.
- Cuando el código hace I/O bloqueante, la JVM **desmonta** (unmount) el virtual thread del platform thread carrier y libera el carrier para otro trabajo.
- El código se escribe de forma **secuencial/imperativa** (más simple que callbacks o reactors), pero escala como lo asíncrono.

```java
Thread.startVirtualThread(() -> {
    var body = httpClient.send(...); // bloquea solo el VT, no “quema” un OS thread
    System.out.println(body);
});

// o con executor (recomendado en servidores):
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    Future<String> f = executor.submit(() -> llamarApi());
    System.out.println(f.get());
}
```

### Cómo detectarlos
```java
Thread.currentThread().isVirtual(); // true en un VT
```

### Platform vs Virtual (mensaje para la clase)

```
Platform thread:  1 Java thread  ≈  1 OS thread   (caro, pocos)
Virtual thread:   N Java threads ≈  M OS carriers (barato, muchos)
```

### Cuándo SÍ usar Virtual Threads
- Servidores con mucho **I/O bloqueante** (JDBC síncrono, HTTP client bloqueante, archivos).
- Tareas que antes pedían pools enormes (`newFixedThreadPool(400)`).
- Código estilo “un request = un thread” (el modelo de servlets clásico vuelve a ser viable a escala).

### Cuándo NO (o con cuidado)
- Tareas **CPU-bound** puras (no ganas: la CPU es el límite; usa un pool acotado al número de núcleos).
- Código que hace `synchronized` muy contencioso alrededor de I/O largo — en Java 21 un VT dentro de `synchronized` **no se puede desmontar** (pinning). Preferir `ReentrantLock` en código nuevo muy caliente.
- Librerías que asumen “pocos threads” o thread-locals masivos sin control.

### Demo
Ver `demos/03-virtual-threads/VirtualThreads.java` — lanza miles de tareas bloqueantes con platform pool vs virtual threads.

### Práctica
```bash
mvn test -Dtest=VirtualThreadsExercisesTest
```

---

## Sección 4: Structured Concurrency y Scoped Values (50 min)

> En **Java 21** ambas APIs están en **preview**. Hay que compilar y ejecutar con `--enable-preview`.
> El `pom.xml` de `ejercicios/` ya lo activa. En demos sueltas:
> `java --source 21 --enable-preview Archivo.java`.

### Structured Concurrency (JEP 453)
Problema clásico: lanzas 3 `CompletableFuture` o tasks en un executor, una falla, y las otras siguen vivas (fugas, trabajo inútil, errores difíciles de cancelar).

**Structured concurrency** trata un grupo de tareas concurrentes como una **unidad de trabajo** con ciclo de vida claro (como un bloque `try`):

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Subtask<User>  user  = scope.fork(() -> findUser(id));
    Subtask<Order> order = scope.fork(() -> findOrder(id));
    scope.join();           // espera a que terminen las subtareas
    scope.throwIfFailed();  // si alguna falló, propaga y cancela el resto
    return new Response(user.get(), order.get());
} // al salir del try, no quedan subtareas huérfanas
```

Políticas útiles:
- `ShutdownOnFailure` — si una falla, cancela las demás.
- `ShutdownOnSuccess` — con la primera que tenga éxito basta (ej. consultar réplicas).

### Scoped Values (JEP 446)
Reemplazo moderno (y preferible) de muchos usos de `ThreadLocal` en código con virtual threads:
- Inmutables mientras están bound.
- Pensados para **heredar** el valor a virtual threads hijos de forma controlada.
- Se “deshacen” al salir del `where(...).run/call`.

```java
private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

ScopedValue.where(REQUEST_ID, "req-42").run(() -> {
    // cualquier código aquí (y VTs hijos en el mismo binding) ve REQUEST_ID.get()
    procesar();
});
```

### Demo
- `demos/04-structured-concurrency/StructuredConcurrencyDemo.java`
- `demos/05-scoped-values/ScopedValuesDemo.java`

### Práctica
```bash
mvn test -Dtest=StructuredConcurrencyExercisesTest
```

---

## Sección 5: Casos de uso y limitaciones (25 min)

### Mapa mental para el equipo

| Escenario | Enfoque recomendado |
|-----------|---------------------|
| Muchas llamadas I/O bloqueantes | Virtual threads (`newVirtualThreadPerTaskExecutor`) |
| Cálculo CPU intensivo | Pool fijo ≈ núcleos (`newFixedThreadPool`) |
| Componer async sin bloquear al caller | `CompletableFuture` (con executor adecuado) |
| Varias subtareas de un mismo request | Structured concurrency |
| Contexto de request (user, trace id) | Scoped values (mejor que ThreadLocal masivo) |

### Limitaciones a mencionar en clase
1. **Pinning** con `synchronized` + I/O largo (mejorar en versiones posteriores; en 21 es un punto de observación con JFR).
2. Preview APIs cambian: Structured/Scoped en 21 requieren `--enable-preview`; en LTS posteriores se estabilizan.
3. Virtual threads no magia: un deadlock o un pool JDBC mal dimensionado sigue siendo un problema.
4. Migración gradual: no hace falta reescribir todo a reactive; a menudo basta cambiar el executor.

### Demo de cierre
Ver `demos/06-casos-uso/CasosUsoLimitaciones.java`.

---

## Laboratorio: Migración a Virtual Threads

Código legado con pool fijo grande simulando I/O → migrar a virtual threads manteniendo el mismo resultado de negocio.

```bash
mvn test -Dtest=MigracionVirtualThreadsTest
```

Referencia sin Maven: carpeta `laboratorio/`.

### Cierre del módulo
"La concurrencia en Java 21 deja de obligarte a elegir entre ‘código simple’ y ‘escala’. Con virtual threads escribes código secuencial que escala en I/O; con structured concurrency controlas el ciclo de vida de las subtareas."
