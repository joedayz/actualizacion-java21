# Módulo 3 — Ejercicios (participantes)

Proyecto Maven con **5 ejercicios** y tests **JUnit 5**. Completa los `TODO` hasta que `mvn test` quede en verde.

> Structured Concurrency y Scoped Values son **preview en Java 21**. El `pom.xml` ya activa `--enable-preview`.

## Requisitos

- JDK **21+**
- Maven **3.9+**

## Cómo trabajar

```bash
cd modulo-3-concurrencia-moderna/ejercicios

mvn test

# Por ejercicio
mvn test -Dtest=ConcurrenciaClasicaTest
mvn test -Dtest=FuturesModernosTest
mvn test -Dtest=VirtualThreadsExercisesTest
mvn test -Dtest=StructuredConcurrencyExercisesTest
mvn test -Dtest=MigracionVirtualThreadsTest
```

## Ejercicios

| # | Paquete | Tema | Tests |
|---|---------|------|-------|
| 01 | `ejercicio01` | ExecutorService (concurrencia Java 8) | `ConcurrenciaClasicaTest` |
| 02 | `ejercicio02` | CompletableFuture | `FuturesModernosTest` |
| 03 | `ejercicio03` | Virtual Threads | `VirtualThreadsExercisesTest` |
| 04 | `ejercicio04` | Structured Concurrency + Scoped Values | `StructuredConcurrencyExercisesTest` |
| 05 | `ejercicio05` | Laboratorio: migrar a Virtual Threads | `MigracionVirtualThreadsTest` |

En el ejercicio 05, la lógica de negocio ya funciona; solo debes cambiar `crearExecutor()` para usar virtual threads.
