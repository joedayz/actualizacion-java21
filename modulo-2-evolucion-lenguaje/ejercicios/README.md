# Módulo 2 — Ejercicios (participantes)

Proyecto Maven con **6 ejercicios** y tests **JUnit 5**. Completa los `TODO` hasta que `mvn test` quede en verde.

## Requisitos

- JDK **21+**
- Maven **3.9+**

## Cómo trabajar

```bash
cd modulo-2-evolucion-lenguaje/ejercicios

# Todos los tests (al inicio fallan: es lo esperado)
mvn test

# Solo un ejercicio
mvn test -Dtest=RecapLenguajeTest
mvn test -Dtest=RecordsSealedTest
mvn test -Dtest=PatternMatchingExercisesTest
mvn test -Dtest=StreamsOptionalExercisesTest
mvn test -Dtest=OrderServiceTest
mvn test -Dtest=ComentariosModernosTest
```

En el IDE: abre esta carpeta como proyecto Maven e importa las dependencias.

## Ejercicios

| # | Paquete | Tema | Tests |
|---|---------|------|-------|
| 01 | `pe.joedayz.modulo2.ejercicio01` | Recap: `var`, switch expressions, text blocks | `RecapLenguajeTest` |
| 02 | `pe.joedayz.modulo2.ejercicio02` | Records + sealed classes | `RecordsSealedTest` |
| 03 | `pe.joedayz.modulo2.ejercicio03` | Pattern matching (`instanceof` y `switch`) | `PatternMatchingExercisesTest` |
| 04 | `pe.joedayz.modulo2.ejercicio04` | Streams, Optional, colecciones inmutables | `StreamsOptionalExercisesTest` |
| 05 | `pe.joedayz.modulo2.ejercicio05` | Laboratorio: modernizar `OrderService` | `OrderServiceTest` |
| 06 | `pe.joedayz.modulo2.ejercicio06` | JavaDoc moderno: `///` y `{@snippet}` | `ComentariosModernosTest` |

Cada clase en `src/main/java` tiene comentarios con el enunciado. **No mires la solución del facilitador** hasta que él la explique en clase.

## Criterio de éxito

- Los tests de comportamiento validan el resultado de negocio.
- En los ejercicios 02 y 05 también se valida (por reflexión) que uses `record` y `sealed` donde corresponde.
- En el ejercicio 06 se valida el **estilo de documentación** leyendo el código fuente (`///` y `{@snippet}`).
