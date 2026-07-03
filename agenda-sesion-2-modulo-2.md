# Agenda — Sesión 2 (4 horas): Módulo 2 completo

**Contexto:** En la sesión 1 se cubrió ~1 h del Módulo 2 (`var`, switch expressions, text blocks).
Esta sesión **termina el Módulo 2** (no se avanza al Módulo 3).

**Material de ejercicios:** `modulo-2-evolucion-lenguaje/ejercicios/` (Maven + JUnit 5).
**Soluciones (solo facilitador, no en el repo):** `soluciones-modulo-2/` — `mvn test` debe quedar en verde.

**Dinámica en cada bloque de práctica:** los participantes intentan solos → si se traban, pistas breves → al cerrar el bloque, el facilitador proyecta la solución y responde dudas.

---

## Minuto a minuto (240 min)

| Minuto | Duración | Actividad |
|--------|----------|-----------|
| 0:00–0:10 | **10 min** | Apertura: agenda del día, recap verbal de la sesión 1, cómo abrir el proyecto Maven y correr `mvn test` / un test suelto. |
| 0:10–0:45 | **35 min** | **Bloque A — Recap + Ejercicio 01** |
| | 5 min | Recap rápido + demos `01-var`, `02-switch-expressions`, `03-text-blocks` (solo puntos clave). |
| | **15 min** | **Práctica:** `RecapLenguaje` — `mvn test -Dtest=RecapLenguajeTest` |
| | 15 min | Solución en vivo + preguntas. |
| 0:45–1:35 | **50 min** | **Bloque B — Records y Sealed Classes** |
| | 15 min | Teoría (sección 4 de `teoria.md`) + demo `04-records-sealed`. |
| | **20 min** | **Práctica:** Ejercicio 02 (`Producto`, `FormaPago` / records) — `mvn test -Dtest=RecordsSealedTest` |
| | 15 min | Solución en vivo + preguntas. |
| 1:35–1:45 | **10 min** | **Descanso** |
| 1:45–2:35 | **50 min** | **Bloque C — Pattern Matching** |
| | 15 min | Teoría (sección 5) + demo `05-pattern-matching`. |
| | **20 min** | **Práctica:** Ejercicio 03 — `mvn test -Dtest=PatternMatchingExercisesTest` |
| | 15 min | Solución en vivo (énfasis en exhaustividad con sealed, sin `default`). |
| 2:35–3:10 | **35 min** | **Bloque D — Streams, Optional, colecciones** |
| | 10 min | Teoría (sección 6) + demo `06-streams-optional`. |
| | **15 min** | **Práctica:** Ejercicio 04 — `mvn test -Dtest=StreamsOptionalExercisesTest` |
| | 10 min | Solución en vivo (`toList()`, `List.of`, `getFirst`/`getLast`). |
| 3:10–3:50 | **40 min** | **Bloque E — Laboratorio integrador** |
| | 5 min | Enunciado: modernizar `OrderService` (estilo Java 8 → Java 21). Checklist en la clase. |
| | **25 min** | **Práctica:** Ejercicio 05 — `mvn test -Dtest=OrderServiceTest` |
| | 10 min | Solución en vivo. |
| 3:50–4:00 | **10 min** | **Bloque F — Comentarios / JavaDoc moderno (Ej. 06)** |
| | 3 min | Demo `07-comentarios-javadoc` (`/**`+HTML vs `{@snippet}` vs `///`). |
| | **5 min** | **Práctica rápida:** `mvn test -Dtest=ComentariosModernosTest` |
| | 2 min | Solución + cierre. Anticipo Módulo 3 (Virtual Threads). |

**Total práctica de alumnos:** ~100 min · **Total explicación/demos:** ~130 min · **Descanso:** 10 min.

---

## Guion rápido por ejercicio (facilitador)

### Ejercicio 01 — Recap
- `tipoDeDia`: switch expression, varias etiquetas, sin fall-through.
- `horasDeTrabajo`: bloque + `yield` en VIERNES.
- `consultaActivos`: text block + `formatted`.
- `totalesPorRegion`: `var` + `Map.of` (inmutable).

### Ejercicio 02 — Records / Sealed
- Constructor compacto en `Producto` (validación).
- `FormaPago` → `sealed` + `permits`; implementaciones → `record`.
- Los tests usan reflexión (`isRecord()`, `isSealed()`, `getPermittedSubclasses()`).
- **Orden del `permits`:** `Efectivo, Tarjeta, Yape` (como en el enunciado).

### Ejercicio 03 — Pattern Matching
- `medir`: `instanceof` con pattern variable (+ guard `!s.isBlank()`).
- `area` / `describir`: `switch` + record patterns, exhaustivo sin `default`.

### Ejercicio 04 — Streams / Optional
- `toList()` inmutable (el test intenta `.add` y espera `UnsupportedOperationException`).
- `Optional.map` / `orElse` (o `ifPresentOrElse` si prefieren).
- Sequenced Collections: `getFirst()` / `getLast()`.

### Ejercicio 05 — Laboratorio
- Al inicio la **lógica de negocio ya pasa**; fallan los tests de modernización de tipos.
- Meta: records + sealed + (idealmente) streams, switch expression, text block en el recibo.
- Cifras de negocio: subtotal **4390**, 10% → **3951**.

### Ejercicio 06 — Comentarios / JavaDoc
- `formatearNombre`: documentar con `///` (Markdown, JEP 467).
- `porcentaje`: JavaDoc `/** */` con `{@snippet` (JEP 413, Java 18).
- Recordar: `///` compila en cualquier JDK; `javadoc` lo trata como doc desde **JDK 23**.
- Trampa: dentro de text blocks, `//` es texto del string.

---

## Comandos útiles (facilitador)

```bash
# Participantes
cd modulo-2-evolucion-lenguaje/ejercicios && mvn test

# Tus soluciones (carpeta local, gitignored)
cd soluciones-modulo-2 && mvn test
```

Si Maven del entorno apunta a un Nexus inaccesible, usa Maven Central:

```bash
mvn -s /ruta/a/settings-solo-central.xml test
```
