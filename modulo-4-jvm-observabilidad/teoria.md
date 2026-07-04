# Módulo 4. JVM Moderna y Observabilidad (4 horas)

> Objetivo: entender cómo evolucionó la JVM en Java 11/17/21, elegir y configurar **garbage collectors modernos**, usar **JFR/JMC** para observar la aplicación en runtime, medir rendimiento con **JMH** y aplicar un flujo de **diagnóstico y tuning**.
>
> Ejercicios Maven + JUnit 5: [`ejercicios/`](./ejercicios/).
> Soluciones del facilitador (local, no en el repo): `soluciones-modulo-4/`.

---

## Sección 1: Evolución de la JVM (45 min)

### De Java 8 a Java 21: qué cambió “debajo del capó”
En el Módulo 1 viste el roadmap de versiones. Aquí profundizamos en la **plataforma de ejecución**, no en el lenguaje.

| Área | Java 8 (referencia) | Java 11+ | Java 17/21 |
|------|---------------------|----------|------------|
| GC por defecto | Parallel GC | **G1GC** (desde Java 9) | G1; ZGC/Shenandoah estables |
| Arranque | lento en apps grandes | **CDS** (Class Data Sharing) | CDS dinámico, mejor startup |
| Observabilidad | herramientas externas (VisualVM, etc.) | **JFR** integrado y accesible | JFR por defecto, bajo overhead |
| Concurrencia | platform threads | mejoras en `ForkJoinPool` | **Virtual Threads** (Módulo 3) |

### Compilación JIT (idea para la clase)
1. **Interpretado** — el bytecode se ejecuta línea a línea (lento).
2. **C1 (client)** — compilación rápida, optimizaciones ligeras.
3. **C2 (server)** — compilación más lenta, optimizaciones agresivas (inlining, escape analysis, etc.).

**Tiered compilation** (default): empieza interpretado, sube a C1 y luego a C2 en métodos “calientes”.

> Mensaje clave: por eso un benchmark sin **warmup** miente (JMH lo resuelve en la Sección 4).

### CDS — Class Data Sharing
Permite compartir metadatos de clases entre procesos JVM (menos RAM, arranque más rápido).
En producción: `-XX:ArchiveClassesAtExit=app.jsa` + `-XX:SharedArchiveFile=app.jsa` (flujo simplificado; ver documentación del JDK).

### Demo
Ver `demos/01-gc-overview/GcOverview.java` — imprime versión de JVM, GC activo, memoria y dispara allocations para observar GC en consola.

---

## Sección 2: Garbage Collectors modernos — G1GC, ZGC, Shenandoah (60 min)

### Por qué importa el GC al migrar Java 8 → 21
Desde Java 9 el GC por defecto es **G1**, no Parallel. Al subir de versión **cambia el comportamiento de pausas y throughput** aunque no toques flags.

### G1GC (Garbage-First) — default general
- Divide el heap en **regiones** (no generaciones contiguas rígidas).
- Prioriza regiones con más basura (“garbage-first”).
- Objetivo típico: `-XX:MaxGCPauseMillis=200` (orientativo, no garantía).

```bash
java -XX:+UseG1GC -Xms512m -Xmx512m -Xlog:gc*:stdout:time,uptime,level,tags -jar app.jar
```

**Cuándo:** aplicaciones generales, heaps medianos, buen equilibrio latencia/throughput.

### ZGC (Z Garbage Collector)
- Pausas **sub-milisegundo** en la mayoría de casos (Java 17+ productivo).
- Escala a heaps **muy grandes** (terabytes teóricos).
- Trabajo casi todo **concurrente** con la aplicación.

```bash
java -XX:+UseZGC -Xms2g -Xmx2g -Xlog:gc -jar app.jar
```

**Cuándo:** latencia crítica (APIs síncronas, trading, gaming backend), heaps grandes.

### Shenandoah
- Filosofía similar a ZGC: pausas muy bajas, compactación concurrente.
- Disponible en builds OpenJDK / Red Hat; no en todos los JDK vendors.

```bash
java -XX:+UseShenandoahGC -Xms2g -Xmx2g -jar app.jar
```

**Cuándo:** alternativa a ZGC cuando el vendor lo soporta y el perfil es latencia.

### Mapa de decisión (para proyectar)

```
¿Cuál es tu prioridad?
├── Throughput batch / ETL        → G1GC o Parallel (legacy)
├── Latencia P99 en servicio HTTP → ZGC o Shenandoah
└── Heap pequeño, recursos justos → G1GC con heap fijo (-Xms = -Xmx)
```

### Leer logs de GC (Java 9+ unified logging)
Ejemplo G1:
```
[0.456s][info][gc] GC(12) Pause Young (Normal) (G1 Evacuation Pause) 24M->8M(64M) 3.456ms
```

Campos útiles:
- **Young vs Old** — presión de objetos de corta vs larga vida.
- **Antes->Después(Max)** — cuánto heap se usa y el tope.
- **Duración** — pausa stop-the-world.

### Práctica
```bash
cd ejercicios && mvn test -Dtest=GcFlagsExercisesTest
cd ejercicios && mvn test -Dtest=GcLogParserTest
```

---

## Sección 3: Java Flight Recorder (JFR) y Java Mission Control (JMC) (60 min)

### JFR — grabación de eventos integrada en el JDK
- Eventos de CPU, memoria, locks, I/O, métodos calientes, GC, virtual threads (pinning), etc.
- Overhead bajo en producción (típicamente < 1–2 % con perfil default).
- Desde Java 11+ accesible sin agentes comerciales.

#### Grabar desde línea de comandos
```bash
# Grabación de 60 segundos al arrancar
java -XX:StartFlightRecording=duration=60s,filename=app.jfr -jar app.jar

# Grabación continua, dumpear bajo demanda
java -XX:StartFlightRecording=disk=true,maxsize=100m,dumponexit=true,filename=app.jfr -jar app.jar
```

#### Grabar programáticamente
```java
try (var recording = new Recording()) {
    recording.enable("jdk.GCPhasePause");
    recording.enable("jdk.ExecutionSample");
    recording.start();
    Thread.sleep(Duration.ofSeconds(5));
    recording.stop();
    recording.dump(Path.of("app.jfr"));
}
```

### JMC — analizar el `.jfr`
1. Descargar [Eclipse Mission Control](https://adoptium.net/jmc/) o usar el embebido en algunos JDKs.
2. Abrir el archivo `.jfr`.
3. Vistas clave:
   - **Method Profiling** — métodos que más CPU consumen.
   - **Memory** — allocation rate, presión de heap.
   - **Java Application** — threads, locks.
   - **JVM Internals** — GC, compilación.

### Conexión con Módulo 3
En Java 21, JFR expone eventos de **virtual threads** y **pinning** (`jdk.VirtualThreadPinned`). Si migraste a VT y la latencia no mejora, JFR es el primer lugar donde mirar.

### Demo
Ver `demos/02-jfr-recording/JfrRecording.java`.

### Práctica
```bash
mvn test -Dtest=JfrRecordingExercisesTest
```

---

## Sección 4: Benchmarking con JMH (45 min)

### Por qué no basta con un `for` y `System.nanoTime()`
1. **JIT warmup** — las primeras iteraciones son interpretadas; luego C1/C2 cambian todo.
2. **Dead code elimination** — el compilador puede eliminar código cuyo resultado no se usa.
3. **Constant folding** — el compilador puede calcular en compile-time si los inputs son constantes.
4. **GC en medio** — una pausa de GC distorsiona una medición puntual.

### JMH (Java Microbenchmark Harness)
Herramienta estándar de la comunidad Java para microbenchmarks reproducibles.

```java
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class MiBenchmark {

    @Benchmark
    public String concatenarNaive() {
        String s = "";
        for (int i = 0; i < 100; i++) {
            s += i;
        }
        return s;
    }
}
```

Modos comunes:
- `Mode.Throughput` — operaciones por unidad de tiempo.
- `Mode.AverageTime` — tiempo promedio por operación.
- `Mode.SampleTime` — distribución de tiempos (percentiles).

### Demo
Ver `demos/03-jmh-benchmark/` — proyecto Maven listo para `mvn package` y ejecutar el JAR sombreado.

### Práctica
```bash
mvn test -Dtest=OptimizacionMedibleTest
```

Benchmark JMH (manual, demo separada):
```bash
cd demos/03-jmh-benchmark && mvn package && java -jar target/benchmarks.jar StringConcatBenchmark
```

---

## Sección 5: Diagnóstico, análisis de rendimiento y tuning (30 min)

### Flujo de trabajo recomendado
1. **Reproducir** — carga, dataset, versión exacta del JDK.
2. **Observar** — JFR, logs GC, métricas (Micrometer/Prometheus si hay), `jcmd`, `jstat`.
3. **Hipótesis** — ¿GC? ¿lock contention? ¿allocation rate? ¿I/O?
4. **Validar** — JMH para micro-cambios; prueba de carga para macro.
5. **Ajustar** — flags JVM y/o código; medir de nuevo.

### Herramientas de línea de comandos (JDK)
```bash
jcmd <pid> VM.flags          # flags activos
jcmd <pid> GC.heap_info        # heap
jcmd <pid> Thread.print        # thread dump
jstat -gc <pid> 1s             # GC en tiempo real
```

### Problemas frecuentes al migrar

| Síntoma | Causa probable | Acción |
|---------|----------------|--------|
| Pausas largas P99 | GC stop-the-world | Probar ZGC; revisar allocation rate |
| OutOfMemoryError: Java heap | heap pequeño o leak | heap dump; JFR allocations |
| OutOfMemoryError: Metaspace | demasiadas clases / classloaders | revisar frameworks, hot deploy |
| CPU alto sin carga | compilación JIT, GC thrashing | warmup; JFR Method Profiling |
| Throughput bajo post-migración | GC default cambió | comparar flags; unified GC logging |

### Demo
Ver `demos/04-diagnostico/DiagnosticoJvm.java`.

### Práctica
```bash
mvn test -Dtest=TuningLaboratorioTest
```

---

## Laboratorio: reducir presión de memoria en código legado

Código que concatena strings en un bucle caliente genera muchas allocations y presiona al GC.

```bash
mvn test -Dtest=TuningLaboratorioTest
```

Referencia sin Maven: carpeta `laboratorio/`.

### Cierre del módulo
"Modernizar a Java 21 no termina en el código fuente: la JVM trae GCs, observabilidad y herramientas de medición que permiten **demostrar** mejoras y **diagnosticar** problemas en producción. JFR + JMH forman el par observar/medir antes de tunear a ciegas."
