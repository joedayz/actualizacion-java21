# Material de clase — Modernización Java 8 → Java 21

Basado en la propuesta comercial `Propuesta Comercial - Java 21.pdf` (JOEDAYZ academy, 40 horas, 9 módulos).

## Empieza aquí (Sesión 2 — hoy)

- [`agenda-sesion-2-modulo-2.md`](./agenda-sesion-2-modulo-2.md) — Agenda de **4 horas** para terminar el Módulo 2 (ejercicios Maven + JUnit 5).
- **Ejercicios (participantes):** [`modulo-2-evolucion-lenguaje/ejercicios/`](./modulo-2-evolucion-lenguaje/ejercicios/) — `mvn test`.
- **Soluciones (solo facilitador):** carpeta local `soluciones-modulo-2/` (gitignored, no va al repo).

## Módulo 1. Roadmap de Java 8 a Java 21 (3h) — sesión anterior
- [`modulo-1-roadmap-java8-a-java21/teoria.md`](./modulo-1-roadmap-java8-a-java21/teoria.md)
- [`modulo-1-roadmap-java8-a-java21/demos/compatibilidad/`](./modulo-1-roadmap-java8-a-java21/demos/compatibilidad/)

## Módulo 2. Evolución del Lenguaje Java (6h) — se completa hoy
- [`modulo-2-evolucion-lenguaje/teoria.md`](./modulo-2-evolucion-lenguaje/teoria.md)
- Demos (ejecutables con `java Archivo.java`, JDK 21+):
  - Sesión 1 (ya vista): `demos/01-var/`, `demos/02-switch-expressions/`, `demos/03-text-blocks/`
  - Sesión 2: `demos/04-records-sealed/`, `demos/05-pattern-matching/`, `demos/06-streams-optional/`
- Laboratorio clásico (referencia sin Maven): [`laboratorio/`](./modulo-2-evolucion-lenguaje/laboratorio/)
- **Ejercicios con tests (usar en clase):** [`ejercicios/`](./modulo-2-evolucion-lenguaje/ejercicios/)

## Cómo correr cualquier demo

```bash
java ruta/al/Archivo.java
```

Requiere JDK 21+.

## Cómo correr los ejercicios

```bash
cd modulo-2-evolucion-lenguaje/ejercicios
mvn test
# o un ejercicio:
mvn test -Dtest=RecapLenguajeTest
```
