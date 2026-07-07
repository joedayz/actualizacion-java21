# Demo: jdeps --jdk-internals

Detecta dependencias de tu aplicación sobre APIs **internas** del JDK (`sun.*`, `jdk.internal.*`, etc.).

## Requisitos

- JDK 21+
- Un JAR compilado

## Caso 1: JAR de ejercicios (salida vacía = buena señal)

```bash
cd modulo-5-modularidad-seguridad/ejercicios
mvn -q package -DskipTests

jdeps --jdk-internals --multi-release 21 target/modulo-5-ejercicios-1.0.0-SNAPSHOT.jar
```

**Si no sale nada**, es correcto: ese JAR solo usa APIs públicas (`java.base`). Los textos
`"jdk.internal.misc.Unsafe"` en los tests son **strings**, no referencias en bytecode.

Para confirmar que el JAR se analizó:

```bash
jdeps target/modulo-5-ejercicios-1.0.0-SNAPSHOT.jar
```

Deberías ver dependencias hacia `java.base`.

## Caso 2: JAR con API interna (salida con hallazgos)

El submódulo `demos` incluye `UsaApiInternaLegacy.java`, que importa `sun.misc.Unsafe`
(patrón legacy típico en apps Java 8).

```bash
cd modulo-5-modularidad-seguridad
mvn -q package -pl demos -DskipTests

jdeps --jdk-internals --multi-release 21 demos/target/modulo-5-demos-1.0.0-SNAPSHOT.jar
```

Salida esperada (resumen):

```
modulo-5-demos-....jar -> jdk.unsupported
   UsaApiInternaLegacy -> sun.misc.Unsafe    JDK internal API (jdk.unsupported)
```

## Qué buscar en la salida

- Referencias a `sun.*`, `com.sun.*`, `jdk.internal.*`
- Librerías de terceros que aún no migraron → actualizar versión o `--add-opens`

## En aplicaciones reales

```bash
jdeps --jdk-internals --class-path 'lib/*' target/mi-empresa-app.jar
```

Documenta cada hallazgo con ticket de migración.
