# Módulo 2. Evolución del Lenguaje Java (6 horas totales)

> **Sesión 1 (ya cubierta, ~1 h):** Secciones 1–3 (`var`, Switch Expressions, Text Blocks).
> **Sesión 2 (hoy, 4 h):** Recap con práctica + Secciones 4–6 + laboratorio con tests JUnit 5.
> Agenda detallada: [`../agenda-sesion-2-modulo-2.md`](../agenda-sesion-2-modulo-2.md).
> Ejercicios Maven: [`ejercicios/`](./ejercicios/).

---

## SESIÓN 1 — Sección 1: Inferencia de tipos con `var` (15 min)

### Teoría
- Introducido en **Java 10** (JEP 286).
- `var` es inferencia de tipo **local**, resuelta en tiempo de **compilación** — Java sigue siendo fuertemente tipado, no es como `var`/`let` en JavaScript.
- Solo aplica a **variables locales** (dentro de métodos, bloques, for-loops, try-with-resources). NO aplica a:
  - Atributos de clase (fields)
  - Parámetros de métodos (con excepción de lambdas desde Java 11)
  - Tipos de retorno
- Reglas: siempre necesita un inicializador (`var x;` sin valor no compila), y no puede inferir `null` sin un cast.

### Cuándo usarlo (y cuándo no) — buena práctica
- ✅ Cuando el tipo es obvio por el lado derecho: `var lista = new ArrayList<Cliente>();`
- ✅ Cuando el tipo es muy largo/genérico: `var resultado = Map.<String, List<Pedido>>of();`
- ❌ Cuando reduce la legibilidad: `var x = obtenerDatos();` (¿qué retorna `obtenerDatos()`? no se sabe a simple vista)

### Demo en vivo
Ver `demos/01-var/Var.java`.

---

## SESIÓN 1 — Sección 2: Switch Expressions (25 min)

### Teoría
- Estables desde **Java 14** (JEP 361), preview desde Java 12.
- El `switch` clásico es una **sentencia** (statement); el nuevo `switch` puede ser una **expresión** (retorna un valor).
- Sintaxis con flecha (`->`) en vez de `case ... :` — elimina el "fall-through" accidental (uno de los bugs más clásicos de Java 8).
- Permite múltiples etiquetas por rama: `case LUNES, MARTES, MIERCOLES ->`.
- `yield` reemplaza a `return`/`break` cuando el cuerpo de una rama necesita más de una expresión (bloque `{}`).
- El compilador exige **exhaustividad**: si es un `enum` y falta un caso (sin `default`), no compila — detecta bugs en tiempo de compilación en vez de runtime.

### Demo en vivo
Ver `demos/02-switch-expressions/SwitchExpressions.java` — muestra lado a lado el switch clásico de Java 8 (con fall-through bug incluido a propósito) vs. la expresión moderna.

---

## SESIÓN 1 — Sección 3: Text Blocks (15 min)

### Teoría
- Estables desde **Java 15** (JEP 378), preview desde Java 13.
- Delimitados por `"""` (triple comilla), pensados para strings multilínea: JSON, SQL, HTML, mensajes.
- Reglas de indentación: el compilador quita automáticamente el espacio en blanco "incidental" común a todas las líneas (basado en la línea de cierre `"""`).
- Soportan interpolación "manual" vía `.formatted(...)` (Java no tiene interpolación nativa de strings hasta versiones más recientes con String Templates, que fue preview y luego se retiró).
- Se pueden combinar con métodos existentes de `String` (`.strip()`, `.lines()`, etc.)

### Demo en vivo
Ver `demos/03-text-blocks/TextBlocks.java` — comparación de un JSON/SQL construido con concatenación (Java 8) vs. Text Block.

---

## SESIÓN 2 — Recap práctico (Ejercicio 01)

Antes de seguir con records y sealed, los participantes consolidan la sesión 1 en el proyecto Maven:

```bash
cd ejercicios
mvn test -Dtest=RecapLenguajeTest
```

---

## SESIÓN 2 — Sección 4: Records y Sealed Classes

### Records (Java 16 estable, JEP 395)
- Clases inmutables de datos con una sola línea: constructor, getters, `equals`, `hashCode` y `toString` generados automáticamente.
- Reemplazan gran parte del boilerplate de POJOs/DTOs, y de librerías como Lombok en muchos casos.
- Pueden tener constructores compactos para validación, métodos adicionales e implementar interfaces — pero **no pueden extender otras clases** (son implícitamente `final`) ni tener atributos de instancia mutables adicionales.

### Sealed Classes (Java 17 estable, JEP 409)
- Permiten declarar explícitamente **qué clases pueden extender/implementar** una clase o interfaz (`permits`).
- Cierran el conjunto de subtipos posibles — el compilador lo sabe, lo que habilita exhaustividad en pattern matching (ver siguiente sección).
- Modelan muy bien jerarquías de dominio cerradas (ej. tipos de pago, estados de una orden, resultados de una operación).

Ver `demos/04-records-sealed/RecordsSealed.java`.

### Práctica
```bash
mvn test -Dtest=RecordsSealedTest
```

---

## SESIÓN 2 — Sección 5: Pattern Matching for `instanceof` y `switch`

### Pattern Matching for instanceof (Java 16 estable, JEP 394)
- Elimina el cast redundante después de un `instanceof`.
- Antes: `if (obj instanceof String) { String s = (String) obj; ... }`
- Ahora: `if (obj instanceof String s) { ... uso "s" directamente ... }`

### Pattern Matching for switch (Java 21 estable, JEP 441)
- El `switch` puede hacer *pattern matching* sobre el tipo, no solo sobre valores constantes.
- Combinado con **Record Patterns** (JEP 440, también Java 21) permite "deconstruir" records directamente en el `case`.
- Combinado con **Sealed Classes**, el compilador valida exhaustividad total sin necesitar `default`.

Ver `demos/05-pattern-matching/PatternMatching.java`.

### Práctica
```bash
mvn test -Dtest=PatternMatchingExercisesTest
```

---

## SESIÓN 2 — Sección 6: Mejoras en Streams, Optional y colecciones inmutables

- `Stream.toList()` (Java 16) como atajo a `collect(Collectors.toList())`.
- Nuevos métodos en `Optional`: `ifPresentOrElse`, `or`, `stream()` (Java 9-11).
- Métodos factoría inmutables: `List.of()`, `Set.of()`, `Map.of()` (Java 9) — colecciones verdaderamente inmutables (lanzan `UnsupportedOperationException`), a diferencia de `Collections.unmodifiableList`.
- **Sequenced Collections** (Java 21, JEP 431): métodos nuevos como `getFirst()`, `getLast()`, `reversed()` disponibles directamente en `List`, `Deque`, `LinkedHashSet`, etc.

Ver `demos/06-streams-optional/StreamsOptional.java`.

### Práctica
```bash
mvn test -Dtest=StreamsOptionalExercisesTest
```

---

## SESIÓN 2 — Sección 7: Cambios en comentarios / JavaDoc (extra pedido en clase)

No es un cambio del *compilador* de Java 21, pero sí de cómo documentamos APIs en el ecosistema moderno. Conviene conocerlo porque aparece en código y en IDEs recientes.

### Antes (Java 8): `/** ... */` + HTML
```java
/**
 * Saluda a una persona.
 * <p>Ejemplo:
 * <pre>{@code
 * saludar("Ana");
 * }</pre>
 * @param nombre no debe ser {@code null}
 * @return saludo
 */
```
Funciona, pero mezclar HTML dentro del fuente es ruidoso y frágil.

### Java 18 (JEP 413): `{@snippet}`
Ejemplos de código embebidos en JavaDoc sin el lío de `<pre>{@code ...}</pre>`:

```java
/**
 * Calcula un porcentaje.
 * {@snippet :
 * int p = porcentaje(1, 4); // 25
 * }
 */
```

La herramienta `javadoc` resalta y valida mejor esos ejemplos.

### Java 23 (JEP 467): documentación Markdown con `///`
En lugar de `/** ... */` con HTML, puedes escribir **líneas consecutivas** que empiezan con `///`:

```java
/// Saluda a una persona usando **Markdown**.
///
/// Ejemplo: `saludar("Ana")`
///
/// @param nombre no debe ser `null`
/// @return saludo
public static String saludar(String nombre) { ... }
```

Puntos clave para la clase:
- `///` **siempre compiló** (es un comentario de fin de línea con una `/` extra).
- Lo nuevo es que **`javadoc` (JDK 23+)** trata esas líneas como comentario de documentación y renderiza Markdown (CommonMark).
- Los tags `@param`, `@return`, `{@link}`, etc. **siguen válidos** dentro de `///`.
- No dejes una línea en blanco *sin* `///` en medio del bloque: corta el comentario de documentación.

### Trampa con text blocks
Dentro de un text block (`""" ... """`), las secuencias `//` o `/*` son **texto del string**, no comentarios de Java.

### Demo y práctica
Ver `demos/07-comentarios-javadoc/ComentariosModernos.java`.

```bash
mvn test -Dtest=ComentariosModernosTest
```

---

## SESIÓN 2 — Laboratorio: Refactorización de código Java 8

Proyecto Maven con tests (recomendado en clase):

```bash
mvn test -Dtest=OrderServiceTest
```

Referencia histórica sin Maven (misma idea de negocio): carpeta `laboratorio/` — `LegacyOrderService.java` y `SolucionModerna.java`.

### Cierre del módulo
"Hoy cerramos la evolución del **lenguaje**. La próxima sesión entramos a concurrencia moderna con Java 21: Virtual Threads (Project Loom) — Módulo 3."
