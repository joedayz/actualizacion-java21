# Módulo 4 — Ejercicios (participantes)

Proyecto Maven con **5 ejercicios** y tests **JUnit 5**. Completa los `TODO` hasta que `mvn test` quede en verde.

## Requisitos

- JDK **21+**
- Maven **3.9+**

## Cómo trabajar

```bash
cd modulo-4-jvm-observabilidad/ejercicios

mvn test

# Por ejercicio
mvn test -Dtest=GcFlagsExercisesTest
mvn test -Dtest=GcLogParserTest
mvn test -Dtest=JfrRecordingExercisesTest
mvn test -Dtest=OptimizacionMedibleTest
mvn test -Dtest=TuningLaboratorioTest
```

## Benchmark JMH (ejercicio 04, manual)

Tras implementar `concatenarOptimizado`, compara rendimiento con la demo JMH:

```bash
cd ../demos/03-jmh-benchmark
mvn package
java -jar target/benchmarks.jar StringConcatBenchmark
```

## Ejercicios

| # | Paquete | Tema | Tests |
|---|---------|------|-------|
| 01 | `ejercicio01` | Flags de GC según perfil | `GcFlagsExercisesTest` |
| 02 | `ejercicio02` | Lectura de logs GC (G1) | `GcLogParserTest` |
| 03 | `ejercicio03` | Grabación programática JFR | `JfrRecordingExercisesTest` |
| 04 | `ejercicio04` | Optimización medible (base para JMH en demo) | `OptimizacionMedibleTest` |
| 05 | `ejercicio05` | Laboratorio: reducir allocations | `TuningLaboratorioTest` |

En el ejercicio 05, la firma del método ya está definida; debes reemplazar la concatenación naive por una versión eficiente.
