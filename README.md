# Material de clase — Modernización Java 8 → Java 21

Basado en la propuesta comercial `Propuesta Comercial - Java 21.pdf` (JOEDAYZ academy, 40 horas, 9 módulos).

## Empieza aquí
- [`agenda-clase-4h.md`](./agenda-clase-4h.md) — Agenda minuto a minuto de la sesión de hoy (4 horas: Módulo 1 completo + inicio de Módulo 2).

## Módulo 1. Roadmap de Java 8 a Java 21 (3h) — HOY
- [`modulo-1-roadmap-java8-a-java21/teoria.md`](./modulo-1-roadmap-java8-a-java21/teoria.md) — notas del expositor, línea de tiempo, tabla de versiones LTS.
- [`modulo-1-roadmap-java8-a-java21/demos/compatibilidad/`](./modulo-1-roadmap-java8-a-java21/demos/compatibilidad/) — demo de compatibilidad hacia atrás (`javac --release 8/11/21`).

## Módulo 2. Evolución del Lenguaje Java (6h) — HOY solo la primera hora
- [`modulo-2-evolucion-lenguaje/teoria.md`](./modulo-2-evolucion-lenguaje/teoria.md) — teoría completa del módulo, marcado qué se ve **HOY** vs. **PRÓXIMA SESIÓN**.
- Demos (todas ejecutables con `java Archivo.java`, sin compilar aparte, usando JDK 21+):
  - **Hoy:** `demos/01-var/`, `demos/02-switch-expressions/`, `demos/03-text-blocks/`
  - **Próxima sesión:** `demos/04-records-sealed/`, `demos/05-pattern-matching/`, `demos/06-streams-optional/`
  - **Próxima sesión (laboratorio):** [`laboratorio/`](./modulo-2-evolucion-lenguaje/laboratorio/) — refactorización de `LegacyOrderService.java` con solución de referencia en `SolucionModerna.java`.

## Cómo correr cualquier demo
Todas las demos son archivos `.java` de un solo archivo (sin dependencias externas), pensadas para ejecutarse directamente:

```bash
java ruta/al/Archivo.java
```

Requiere JDK 21+ (verificado con JDK 25 usando `--release 21` donde aplica, ver demo de compatibilidad).
