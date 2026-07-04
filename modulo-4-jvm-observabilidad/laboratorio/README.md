# Laboratorio: reducir presión de memoria

## Objetivo
Optimizar `LegacyAllocationService.java`, que concatena strings en un bucle caliente y genera muchas allocations innecesarias.

## Pasos
1. Ejecuta `java LegacyAllocationService.java` y observa el tiempo.
2. Abre el código y localiza el anti-patrón (`result += ...` en bucle).
3. Reemplázalo por `StringBuilder` (o equivalente eficiente).
4. Compara con `SolucionOptimizada.java` al final.

## Versión con tests (recomendada en clase)
Ver `../ejercicios/` — `TuningLaboratorioTest`.

## Tip para la clase
Después de corregir el código, graba un JFR antes/después (`demos/02-jfr-recording/`) y compara **allocation rate** en JMC.
