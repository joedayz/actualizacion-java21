# Módulo 5. Modularidad, Seguridad y Cambios de Plataforma (4 horas)

> Objetivo: comprender el **Java Platform Module System (JPMS)**, la **encapsulación fuerte** del JDK, las restricciones de **reflection**, los cambios en **TLS/criptografía** y las **APIs removidas/deprecadas** — y evaluar su impacto al migrar aplicaciones empresariales de Java 8 a Java 21.
>
> Ejercicios Maven + JUnit 5: [`ejercicios/`](./ejercicios/).
> Soluciones del facilitador (local, no en el repo): `soluciones-modulo-5/`.

---

## Sección 1: Java Platform Module System — JPMS (60 min)

### Por qué existen los módulos (Project Jigsaw, Java 9)
Antes de Java 9, el JDK era un **monolito plano**: todo `java.*` y casi todo `jdk.*` era accesible si conocías el paquete. Eso generaba:
- Acoplamiento a APIs internas (`sun.*`, `com.sun.*`).
- JARs “todo en uno” sin límites claros.
- JDK difícil de reducir (no había forma oficial de armar un runtime mínimo).

**JPMS** introduce módulos con fronteras explícitas: qué se exporta, qué se oculta, qué depende de qué.

### Conceptos clave

| Concepto | Significado |
|----------|-------------|
| **Módulo** | Conjunto de paquetes + dependencias declaradas en `module-info.java` |
| **exports** | Paquetes visibles a otros módulos que `require` este módulo |
| **opens** | Permite reflection sobre paquetes (frameworks ORM, JSON, DI) |
| **requires** | Dependencia explícita a otro módulo |
| **requires transitive** | Si A requiere transitivamente B, quien requiere A también ve B |
| **unnamed module** | JARs clásicos sin `module-info` en el classpath (modo compatibilidad) |
| **automatic module** | JAR sin `module-info` en el **module path** (nombre derivado del JAR) |

### module-info.java mínimo

```java
module com.empresa.pedidos {
    requires java.sql;
    requires java.net.http;
    requires com.empresa.comun;

    exports com.empresa.pedidos.api;
    opens com.empresa.pedidos.dto to com.fasterxml.jackson.databind;
}
```

### Classpath vs Module path

```
Classpath (clásico)     → JARs en unnamed module; compatibilidad Java 8
Module path (JPMS)      → módulos con fronteras; encapsulación real
```

**Mensaje para la clase:** la mayoría de apps Spring Boot / Maven siguen en classpath sin modularizar el código propio. Pero **el JDK sí está modularizado** desde Java 9 — y eso afecta reflection, APIs internas y herramientas.

### jlink — runtime mínimo

```bash
jlink --add-modules java.base,java.logging,java.sql,com.empresa.app \
      --output runtime-minimo
```

Útil para contenedores y despliegues donde el tamaño importa.

### Demo
Ver `demos/01-jpms-runtime/ModulosEnRuntime.java` — lista módulos cargados en la JVM actual.

### Práctica
```bash
cd ejercicios && mvn test -Dtest=ModuleInfoExercisesTest
```

---

## Sección 2: Encapsulación fuerte y restricciones de Reflection (50 min)

### La evolución de la encapsulación

| Versión | Comportamiento |
|---------|----------------|
| Java 8 | Reflection sobre casi cualquier cosa del JDK |
| Java 9–16 | Warnings de *illegal reflective access*; `--illegal-access=permit` como parche |
| Java 17+ | **Encapsulación fuerte por defecto**; `--illegal-access` eliminado |
| Java 21 | Misma línea; frameworks deben declarar `--add-opens` explícitos |

### Qué está encapsulado
- Paquetes `jdk.internal.*`
- Muchos paquetes `com.sun.*`, `sun.*`
- APIs que nunca fueron públicas pero usadas “por necesidad” en librerías legacy

### InaccessibleObjectException

```java
Field f = String.class.getDeclaredField("value");
f.setAccessible(true); // puede lanzar InaccessibleObjectException en Java 17+
```

### Soluciones (en orden de preferencia)

1. **Usar API pública** — siempre la opción correcta a largo plazo.
2. **`--add-opens`** — abrir un paquete de un módulo JDK a otro módulo (o `ALL-UNNAMED`):

```bash
java --add-opens java.base/java.lang=ALL-UNNAMED -jar app.jar
```

3. **`opens` en module-info** — si tú controlas el módulo y un framework necesita reflection.

### Impacto en frameworks
Spring, Hibernate, Jackson, etc. documentan los `--add-opens` necesarios para Java 17+. Al migrar, revisar la matriz de compatibilidad de tu versión de framework.

### Demo
Ver `demos/02-reflection-ilegal/ReflectionIlegal.java` — intenta acceder a un miembro interno y muestra el error + el flag de migración.

### Práctica
```bash
mvn test -Dtest=EncapsulacionExercisesTest
mvn test -Dtest=ReflectionMigracionTest
```

---

## Sección 3: Illegal Reflective Access (40 min)

### Qué era “illegal reflective access”
Cuando código en el **classpath** (unnamed module) hacía reflection sobre paquetes **no exportados** del JDK. La JVM lo permitía con warning:

```
WARNING: Illegal reflective access by com.legacy.Lib ...
```

### Timeline de eliminación

```
Java 9   → warnings
Java 11  → más módulos encapsulados
Java 16  → illegal-access=deny por defecto (con escape hatch)
Java 17  → sin escape hatch global; solo --add-opens puntual
Java 21  → frameworks y apps deben estar alineados
```

### Cómo diagnosticar en tu aplicación

1. Ejecutar tests y arranque con `-Xlog:reflect=warn` (o revisar logs de arranque).
2. Buscar en el código: `setAccessible(true)`, `sun.`, `com.sun.`, `jdk.internal.`.
3. Revisar dependencias con `jdeps --jdk-internals mi-app.jar`.

```bash
jdeps --jdk-internals --multi-release 21 target/mi-app.jar
```

### Estrategia de migración

| Situación | Acción |
|-----------|--------|
| Tu código accede a internals | Refactorizar a API pública |
| Librería de terceros sin actualizar | Actualizar versión o `--add-opens` temporal |
| Framework (Spring, etc.) | Seguir su guía de migración a Java 17/21 |
| `--add-opens` en producción | Documentar, minimizar, plan de salida |

### Demo
Ver `demos/03-jdeps-internals/README.md` — flujo con `jdeps` sobre un JAR.

---

## Sección 4: Cambios en TLS y criptografía (35 min)

### TLS deshabilitado por defecto
Desde Java 11 (y reforzado en versiones posteriores):
- **TLS 1.0 y TLS 1.1** deshabilitados por defecto.
- Algoritmos débiles (3DES, RC4, MD5 en certificados) rechazados o limitados.

### Impacto en empresas
- Servicios legacy (mainframe, AS/400, APIs viejas) que solo hablan TLS 1.0 → **dejan de conectar** al subir JDK.
- Certificados con claves cortas o firmas obsoletas → errores de handshake.

### Verificar protocolos habilitados

```java
SSLContext context = SSLContext.getDefault();
String[] protocols = context.getDefaultSSLParameters().getProtocols();
```

### Si necesitas compatibilidad temporal (último recurso)

```bash
# NO recomendado en producción moderna — solo puente de migración
-Djdk.tls.client.protocols=TLSv1.2,TLSv1.1
```

**Mejor:** actualizar el servidor remoto a TLS 1.2+.

### Cambios adicionales en criptografía
- Límites de tamaño de clave RSA en jarsigner.
- Políticas de exportación ya no requieren `unlimited strength` manual (desde Java 8u161+).
- Algoritmos post-cuánticos en roadmap del JDK (awareness, no profundizar).

### Demo
Ver `demos/04-tls-defaults/TlsDefaults.java`.

### Práctica
```bash
mvn test -Dtest=TlsCriptografiaTest
```

---

## Sección 5: APIs removidas, deprecadas e impacto empresarial (35 min)

### Grandes remociones Java 8 → 21

| API / componente | Estado en Java 21 | Alternativa |
|------------------|-------------------|-------------|
| Java EE en el JDK (`javax.xml.bind`, `javax.annotation`, etc.) | Removido desde Java 11 | Dependencias Jakarta EE / standalone |
| CORBA | Removido Java 11 | Librería externa o eliminar |
| Nashorn (JavaScript) | Removido Java 15 | GraalJS u otro motor |
| Security Manager | Deprecado 17, **removido Java 24** | Políticas a nivel OS/contenedor |
| Applet API | Muerta | No migrar |
| `finalize()` | Deprecado | `Cleaner`, try-with-resources |
| `Thread.stop()` / `suspend()` | Deprecado hace años | Interrupción cooperativa |

### javax → jakarta (el dolor de Spring Boot 2 → 3)

```xml
<!-- Antes (Java EE en el nombre) -->
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
</dependency>

<!-- Después (Jakarta) -->
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
</dependency>
```

### Checklist de impacto empresarial (para el equipo)

1. **Inventario de dependencias** — `mvn dependency:tree` / Gradle equivalent.
2. **Bytecode y APIs internas** — `jdeps --jdk-internals`.
3. **TLS hacia sistemas externos** — probar handshakes en staging con JDK 21.
4. **Agentes y reflection** — Hibernate, Jackson, Mockito, ByteBuddy: versiones compatibles.
5. **CI/CD** — build y tests en JDK 21 antes de producción.
6. **Flags temporales** — documentar cada `--add-opens` con ticket y fecha de retiro.

### Demo
Ver `demos/05-apis-cambiadas/ApisCambiadas.java`.

### Laboratorio
Migrar `LegacyPlatformService` (reflection interna + patrones legacy) a APIs públicas.

```bash
mvn test -Dtest=MigracionEmpresarialTest
```

Referencia sin Maven: carpeta `laboratorio/`.

### Cierre del módulo
"Modularizar tu app completa no es obligatorio para llegar a Java 21, pero **vivir en un JDK modular** sí lo es. La migración empresarial exitosa combina: dependencias actualizadas, TLS moderno, eliminar APIs muertas y minimizar `--add-opens` a lo estrictamente necesario."
