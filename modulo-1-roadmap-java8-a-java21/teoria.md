# Módulo 1. Roadmap de Java 8 a Java 21 (3 horas)

## Objetivo
Comprender la evolución de la plataforma Java y los cambios más relevantes introducidos en las versiones LTS, para poder argumentar (técnica y comercialmente) por qué modernizar.

---

## 1. Evolución de Java desde Java 8 (45 min)

### Contexto histórico
- **Java 8 (marzo 2014)**: el último "gran salto" antes del cambio de modelo. Introdujo Lambdas, Streams, `Optional`, nueva API de fecha/hora (`java.time`). Se convirtió en el estándar de facto de la industria durante casi una década.
- **Java 9 (septiembre 2017)**: introduce el **Java Platform Module System (JPMS / Project Jigsaw)** — cambia la forma en que se empaquetan y encapsulan las aplicaciones. También es la versión donde Oracle anuncia el cambio de modelo de liberación.
- A partir de aquí Oracle abandona los ciclos de "cuando esté listo" (que tomaban años) y adopta **releases cada 6 meses**.

### Línea de tiempo (para dibujar/proyectar)

```
2014        2017   2018   2018   2019   2019   2020   2021   2021   2022   2023   2023   2024   2025
 8    ...    9      10   11(LTS) 12     13     14   15    17(LTS)  16    18    19     20    21(LTS)
```

Puntos clave a resaltar:
- Entre Java 8 y Java 9 pasaron **3 años**. Entre cada versión desde Java 9, solo pasan **6 meses**.
- Esto significa que "saltar" de Java 8 directo a Java 21 implica saltar **13 versiones**, no solo "una actualización".
- Muchas empresas se quedaron ancladas en Java 8 porque fue LTS y funcionaba bien — hoy eso es deuda técnica.

### Preguntas para la clase
- "¿Cuántos de ustedes siguen en producción con Java 8?"
- "¿Por qué creen que Oracle cambió el modelo de liberación?" (Respuesta esperada: seguir el ritmo de otros lenguajes/plataformas, entregar features más rápido, reducir el riesgo de cada release individual).

---

## 2. Ciclo de liberación semestral y versiones LTS (30 min)

### El modelo actual
- Una **nueva versión cada 6 meses** (marzo y septiembre), siempre con feature-freeze estricto: lo que no llega a tiempo, espera al siguiente release.
- No todas las versiones reciben soporte a largo plazo. Cada **~2 años** (antes cada 3), una versión se designa **LTS (Long-Term Support)**.

### Versiones LTS hasta ahora

| Versión | Año | Soporte extendido (Oracle) |
|---|---|---|
| Java 8 | 2014 | Hasta ~2030 (pago) |
| Java 11 | 2018 | Hasta ~2026 |
| Java 17 | 2021 | Hasta ~2029 |
| Java 21 | 2023 | Hasta ~2031 |
| Java 25 | 2025 | Hasta ~2033 |

### Versiones "puente" (no LTS)
- Java 9, 10, 12, 13, 14, 15, 16, 18, 19, 20, 22, 23, 24...
- Solo tienen soporte hasta que sale la siguiente versión (6 meses). **No se recomienda quedarse en ellas en producción**, pero sí son importantes porque ahí "incuban" muchas features como *preview* antes de estabilizarse en la siguiente LTS.

### Concepto clave: Preview Features
- Muchas features grandes (records, pattern matching, virtual threads) salieron primero como **preview** (requieren `--enable-preview`) en una versión no-LTS, y se confirman/estabilizan 1-2 versiones después.
- Ejemplo: Pattern Matching for switch fue preview en Java 17, 18, 19, 20 y se volvió estable en **Java 21**.
- Esto explica por qué Java 21 "acumula" tantas features nuevas: es la convergencia de todo lo que se incubó desde Java 17.

---

## 3. Principales novedades de Java 11, 17 y 21 (60 min)

Aquí es solo un **preview conceptual** — el detalle de código se profundiza en el Módulo 2, 3 y 4. La idea es que sepan "qué existe" y en qué versión llegó.

### Java 11 (LTS, 2018)
- `var` para variables locales (llegó realmente en Java 10, pero 11 es la primera LTS que la trae)
- Nuevos métodos en `String` (`isBlank`, `strip`, `lines`, `repeat`)
- HTTP Client estándar (`java.net.http.HttpClient`), reemplazo del viejo `HttpURLConnection`
- Eliminación de Java EE y CORBA del JDK (empiezan las "remociones")
- Single-file source-code execution (`java Archivo.java`)

### Java 17 (LTS, 2021)
- **Sealed Classes** (finales, JEP 409)
- **Pattern Matching for `instanceof`** (finales)
- **Records** (finales desde Java 16)
- **Text Blocks** (finales desde Java 15)
- Nuevos Garbage Collectors: **G1** , **ZGC** y **Shenandoah** ya estables
- Fuerte encapsulación de APIs internas del JDK (`--illegal-access` ya no disponible) — primer gran punto de fricción de migración

### Java 21 (LTS, 2023)
- **Virtual Threads (Project Loom)** — finales, cambia el paradigma de concurrencia (Módulo 3)
- **Pattern Matching for `switch`** — finales
- **Record Patterns** — finales
- **Sequenced Collections** (nuevo tipo de interfaz para colecciones con orden definido)
- **Structured Concurrency** y **Scoped Values** — todavía preview en 21, se van estabilizando después
- String Templates — preview (más adelante removido/reemplazado, buen ejemplo de que no todo preview llega a estable)

### Ejercicio rápido (5 min, sin código)
Muestra 3 fragmentos de código (uno con Java 8 puro, uno con features de 11/17, uno con features de 21) y pregúntales: "¿en qué versión mínima corre esto?"

---

## 4. Compatibilidad hacia atrás (30 min + demo)

### Por qué "no es solo cambiar el número de versión"
- Java mantiene compatibilidad binaria y de código fuente como principio de diseño, **pero no es absoluta**.
- Tres fuentes de ruptura al migrar Java 8 → 21:
  1. **APIs removidas**: ej. Java EE/CORBA (removidos en 11), `SecurityManager` (deprecado para remoción en 17, removido en 24), `Applet API`.
  2. **Encapsulación fuerte del JPMS**: código que hacía `reflection` sobre clases internas de `sun.*` o `com.sun.*` deja de funcionar o lanza warnings/excepciones (`InaccessibleObjectException`).
  3. **Cambios de comportamiento por defecto**: ej. TLS 1.0/1.1 deshabilitados por defecto, cambios en el GC por defecto (de Parallel GC a G1GC desde Java 9), cambios en formateo de fechas/locales (de la data de ICU/CLDR).

### Demo: ver la ruptura en acción
Vamos a compilar el mismo código con distintos niveles de compatibilidad para mostrar en vivo cuándo algo se rompe.

Ver carpeta `demos/compatibilidad/` — instrucciones abajo.

---

## 5. Estrategias de actualización (15 min)

### Enfoques posibles
1. **Big bang**: saltar directo de 8 a 21 en un solo esfuerzo. Rápido pero riesgoso, requiere gran cobertura de pruebas (conecta con Módulo 8).
2. **Incremental por LTS**: 8 → 11 → 17 → 21. Cada salto es más pequeño y manejable, pero el proyecto vive más tiempo en modo "migración".
3. **Incremental por capas**: primero JDK, luego build tool (Maven/Gradle), luego frameworks (Spring Boot, Hibernate), luego librerías de terceros, y al final se adoptan features nuevas del lenguaje.

### Recomendación general (la que se usará en el Módulo 9 - taller)
- Primero, "solo compilar y correr" en la nueva versión sin cambiar código (levantar todos los errores/warnings).
- Luego, actualizar dependencias y frameworks a versiones compatibles.
- Recién al final, refactorizar el código para aprovechar features modernas del lenguaje (esto es justamente lo que se practica en el Módulo 2).

### Cierre de Módulo 1
Conectar con lo que viene: "Ya vimos el **qué** y el **por qué** de migrar. Ahora, en el Módulo 2 vamos a empezar a ver el **cómo** se ve el código moderno."
