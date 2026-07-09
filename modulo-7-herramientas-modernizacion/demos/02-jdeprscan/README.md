# Demo: jdeprscan

Escaneo de uso de APIs deprecadas en bytecode.

## Requisitos

- JDK 21+ (`jdeprscan` viene con el JDK)

## Flujo

```bash
cd modulo-7-herramientas-modernizacion
mvn -q package -pl demos -DskipTests

jdeprscan demos/target/modulo-7-demos-1.0.0-SNAPSHOT.jar
jdeprscan --for-removal demos/target/modulo-7-demos-1.0.0-SNAPSHOT.jar
```

La clase `UsaApiDeprecada` usa el constructor deprecado de `java.util.Date`.
Eso aparece en `jdeprscan` (sin `--for-removal`). La opción `--for-removal` solo
lista APIs marcadas para eliminación — puede salir vacío en este demo.

## Sobre app-legacy

```bash
cd ../app-legacy
mvn -q package -DskipTests
jdeprscan target/modulo-7-app-legacy-1.0.0-SNAPSHOT.jar
# esperado: Date.<init>(III)V deprecated
jdeprscan --for-removal target/modulo-7-app-legacy-1.0.0-SNAPSHOT.jar
```

## Mensaje clave

`jdeprscan` no arregla el código: **prioriza** qué APIs tocar antes de subir el JDK en producción.
