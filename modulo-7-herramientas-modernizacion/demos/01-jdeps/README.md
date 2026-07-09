# Demo: jdeps

Análisis de dependencias de bytecode con la herramienta del JDK.

## Requisitos

- JDK 21+
- JAR compilado de `app-legacy` o de este submódulo demos

## Caso 1: app-legacy (inventario)

```bash
cd modulo-7-herramientas-modernizacion/app-legacy
mvn -q package -DskipTests

jdeps target/modulo-7-app-legacy-1.0.0-SNAPSHOT.jar
jdeps --jdk-internals --multi-release 21 target/modulo-7-app-legacy-1.0.0-SNAPSHOT.jar
```

## Caso 2: demo con API interna

```bash
cd modulo-7-herramientas-modernizacion
mvn -q package -pl demos -DskipTests

jdeps --jdk-internals --multi-release 21 demos/target/modulo-7-demos-1.0.0-SNAPSHOT.jar
```

La clase `UsaUnsafeLegacy` importa `sun.misc.Unsafe` → `jdeps` reporta `jdk.unsupported`.

## Qué discutir en clase

1. Diferencia entre dependencia a `java.base` (OK) vs `jdk.unsupported` (acción).
2. Por qué `--multi-release 21` importa en JARs multi-release.
3. Cómo documentar cada hallazgo como ticket de migración.
