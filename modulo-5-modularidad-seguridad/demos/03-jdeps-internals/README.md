# Demo: jdeps --jdk-internals

Detecta dependencias de tu aplicación sobre APIs **internas** del JDK.

## Requisitos

- JDK 21+
- Un JAR compilado (ej. ejercicios del curso)

## Pasos

```bash
cd modulo-5-modularidad-seguridad/ejercicios
mvn -q package -DskipTests

jdeps --jdk-internals --multi-release 21 target/modulo-5-ejercicios-1.0.0-SNAPSHOT.jar
```

## Qué buscar en la salida

- Referencias a `sun.*`, `com.sun.*`, `jdk.internal.*`
- Librerías de terceros que aún no migraron → actualizar versión o `--add-opens`

## En aplicaciones reales

```bash
jdeps --jdk-internals --class-path 'lib/*' target/mi-empresa-app.jar
```

Documenta cada hallazgo con ticket de migración.
