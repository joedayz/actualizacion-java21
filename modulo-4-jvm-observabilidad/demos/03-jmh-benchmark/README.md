# Demo JMH — concatenación de strings

Compara `String +=` en bucle vs `StringBuilder`.

## Requisitos

- JDK 21+
- Maven 3.9+

## Ejecutar

```bash
cd modulo-4-jvm-observabilidad/demos/03-jmh-benchmark
mvn package
java -jar target/benchmarks.jar StringConcatBenchmark
```

Opcional: filtrar un solo benchmark:

```bash
java -jar target/benchmarks.jar StringConcatBenchmark.concatWithBuilder
```

## Qué observar en clase

- La primera corrida incluye **warmup**; JMH descarta esas iteraciones.
- `concatWithPlus` suele ser órdenes de magnitud más lento por allocations intermedias.
- Sin JMH, el JIT podría optimizar el bucle de forma engañosa (dead code, constant folding).
