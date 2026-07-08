# Demo Multi-Release JAR

Un JAR con implementaciones distintas por versión de Java (`META-INF/versions/11`, `17`, `21`). La JVM carga la más reciente compatible en runtime.

## Ejecutar

Desde `modulos-ejemplo/`:

```bash
./run-demo.sh multi         # compilar y ejecutar
./run-demo.sh multi-build   # solo compilar
./run-demo.sh multi-verify  # inspeccionar el JAR
```

O manualmente:

```bash
cd com.banking.multirelease
./build-multirelease.sh
java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar \
    com.banking.multirelease.MultiReleaseDemo
```

## Qué observar

- En Java 21 debería cargarse `Java21VersionImpl` (virtual threads).
- `jar tf target/*.jar | grep versions` muestra las clases por versión.
- `META-INF/MANIFEST.MF` incluye `Multi-Release: true`.

## Estructura

```
src/main/java/      → base (Java 9+)
src/main/java-11/   → optimización Java 11
src/main/java-17/   → records / pattern matching
src/main/java-21/   → virtual threads
```

Parte opcional del módulo 5; completa primero la demo banking (`../README.md`).
