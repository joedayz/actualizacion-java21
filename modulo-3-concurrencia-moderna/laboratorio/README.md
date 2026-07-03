# Laboratorio: Migración a Virtual Threads

## Objetivo
Migrar `LegacyConcurrentService.java` (pool fijo de platform threads) a virtual threads
manteniendo el mismo resultado de negocio.

## Pasos
1. Ejecuta `java LegacyConcurrentService.java` y observa la salida.
2. Reemplaza `Executors.newFixedThreadPool(200)` por `Executors.newVirtualThreadPerTaskExecutor()`.
3. Usa try-with-resources sobre el executor.
4. Compara con `SolucionVirtualThreads.java` al final.

## Versión con tests (recomendada en clase)
Ver `../ejercicios/` — `MigracionVirtualThreadsTest`.
