# Laboratorio: Refactorización de código Java 8

## Objetivo
Refactorizar `LegacyOrderService.java` (estilo Java 8) aplicando las características modernas
del lenguaje vistas en el Módulo 2: `var`, switch expressions, text blocks, records, sealed
classes, pattern matching y las mejoras de Streams/Optional/colecciones inmutables.

## Instrucciones para los participantes
1. Ejecuta `java LegacyOrderService.java` y observa la salida.
2. Identifica al menos 5 puntos del código que se pueden modernizar.
3. Refactoriza tu propia copia SIN mirar `SolucionModerna.java` todavía.
4. Ejecuta tu versión y compara la salida contra la original — debe ser equivalente en el resultado de negocio.
5. Al final, compara con `SolucionModerna.java` (solución de referencia del facilitador).

## Puntos esperados a modernizar
- El `enum` + `switch` clásico de estado de la orden → switch expression exhaustivo.
- La clase `Cliente` (POJO con boilerplate) → `record`.
- La jerarquía de `Descuento` (clases con `instanceof` en cascada) → `sealed interface` + pattern
  matching con record patterns.
- La construcción del "recibo" con concatenación de Strings → text block.
- El `for` clásico para filtrar/transformar la lista de items → Stream + `toList()`.
- Declaraciones de variables con tipos obvios y largos → `var`.

## Cómo ejecutar
```bash
java LegacyOrderService.java
# luego de refactorizar:
java SolucionModerna.java
```
