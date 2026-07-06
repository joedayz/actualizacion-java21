# Material de clase — Modernización Java 8 → Java 21

Basado en la propuesta comercial `Propuesta Comercial - Java 21.pdf` (JOEDAYZ academy, 40 horas, 9 módulos).

## Sesión en curso

- **Módulo 2 (sesión 2):** [`agenda-sesion-2-modulo-2.md`](./agenda-sesion-2-modulo-2.md) · ejercicios en [`modulo-2-evolucion-lenguaje/ejercicios/`](./modulo-2-evolucion-lenguaje/ejercicios/)
- **Módulo 3 (preparado):** [`modulo-3-concurrencia-moderna/teoria.md`](./modulo-3-concurrencia-moderna/teoria.md) · ejercicios en [`modulo-3-concurrencia-moderna/ejercicios/`](./modulo-3-concurrencia-moderna/ejercicios/)
- **Módulo 4 (preparado):** [`modulo-4-jvm-observabilidad/teoria.md`](./modulo-4-jvm-observabilidad/teoria.md) · ejercicios en [`modulo-4-jvm-observabilidad/ejercicios/`](./modulo-4-jvm-observabilidad/ejercicios/)
- **Módulo 5 (preparado):** [`modulo-5-modularidad-seguridad/teoria.md`](./modulo-5-modularidad-seguridad/teoria.md) · ejercicios en [`modulo-5-modularidad-seguridad/ejercicios/`](./modulo-5-modularidad-seguridad/ejercicios/)

Soluciones del facilitador (locales, gitignored): `soluciones-modulo-2/`, `soluciones-modulo-3/`, `soluciones-modulo-4/`, `soluciones-modulo-5/`.

## Módulo 1. Roadmap de Java 8 a Java 21 (3h)
- [`modulo-1-roadmap-java8-a-java21/teoria.md`](./modulo-1-roadmap-java8-a-java21/teoria.md)
- [`modulo-1-roadmap-java8-a-java21/demos/compatibilidad/`](./modulo-1-roadmap-java8-a-java21/demos/compatibilidad/)

## Módulo 2. Evolución del Lenguaje Java (6h)
- [`modulo-2-evolucion-lenguaje/teoria.md`](./modulo-2-evolucion-lenguaje/teoria.md)
- Demos: `demos/01-var/` … `demos/06-streams-optional/`, `demos/07-comentarios-javadoc/`
- Laboratorio clásico: [`laboratorio/`](./modulo-2-evolucion-lenguaje/laboratorio/)
- **Ejercicios con tests:** [`ejercicios/`](./modulo-2-evolucion-lenguaje/ejercicios/) (incluye Ej. 06: JavaDoc `///` y `{@snippet}`)

## Módulo 3. Concurrencia Moderna con Java 21 (5h)
- [`modulo-3-concurrencia-moderna/teoria.md`](./modulo-3-concurrencia-moderna/teoria.md)
- Demos:
  - `demos/01-concurrencia-java8/`
  - `demos/02-executor-completablefuture/`
  - `demos/03-virtual-threads/`
  - `demos/04-structured-concurrency/` (preview: `java --source 21 --enable-preview …`)
  - `demos/05-scoped-values/` (preview)
  - `demos/06-casos-uso/`
- Laboratorio clásico: [`laboratorio/`](./modulo-3-concurrencia-moderna/laboratorio/)
- **Ejercicios con tests:** [`ejercicios/`](./modulo-3-concurrencia-moderna/ejercicios/)

## Módulo 4. JVM Moderna y Observabilidad (4h)
- [`modulo-4-jvm-observabilidad/teoria.md`](./modulo-4-jvm-observabilidad/teoria.md)
- Demos:
  - `demos/01-gc-overview/`
  - `demos/02-jfr-recording/`
  - `demos/03-jmh-benchmark/` (proyecto Maven)
  - `demos/04-diagnostico/`
- Laboratorio clásico: [`laboratorio/`](./modulo-4-jvm-observabilidad/laboratorio/)
- **Ejercicios con tests:** [`ejercicios/`](./modulo-4-jvm-observabilidad/ejercicios/)

## Módulo 5. Modularidad, Seguridad y Cambios de Plataforma (4h)
- [`modulo-5-modularidad-seguridad/teoria.md`](./modulo-5-modularidad-seguridad/teoria.md)
- Demos:
  - `demos/01-jpms-runtime/`
  - `demos/02-reflection-ilegal/`
  - `demos/03-jdeps-internals/` (guía `jdeps`)
  - `demos/04-tls-defaults/`
  - `demos/05-apis-cambiadas/`
- Laboratorio clásico: [`laboratorio/`](./modulo-5-modularidad-seguridad/laboratorio/)
- **Ejercicios con tests:** [`ejercicios/`](./modulo-5-modularidad-seguridad/ejercicios/)

## Cómo correr demos

```bash
# Demos estables
java ruta/al/Archivo.java

# Demos preview (Structured Concurrency / Scoped Values en Java 21)
java --source 21 --enable-preview ruta/al/Archivo.java
```

Requiere JDK 21+.

## Cómo correr ejercicios

```bash
cd modulo-2-evolucion-lenguaje/ejercicios && mvn test
cd modulo-3-concurrencia-moderna/ejercicios && mvn test
cd modulo-4-jvm-observabilidad/ejercicios && mvn test
cd modulo-5-modularidad-seguridad/ejercicios && mvn test
```
