# Demo: Compatibilidad hacia atrás

Un mismo archivo (`Demo.java`) que usa `var` (Java 10+) y Text Blocks (Java 15+).
Lo compilamos apuntando a distintas versiones con `--release` para ver en vivo qué se rompe.

## Guion para la demo (en este orden)

1. Muestra el código y pregunta: "¿alguien ve algo que no compilaría en Java 8?"

2. Intenta compilar como si fuera Java 8:

```bash
javac --release 8 Demo.java
```

Resultado esperado: **error** — "text blocks are not supported in -source 8" (y warning sobre `var`).

3. Intenta con Java 11 (para mostrar que ni siquiera la primera LTS post-8 alcanza):

```bash
javac --release 11 Demo.java
```

Resultado esperado: **error** — "text blocks are not supported in -source 11" (aquí `var` ya no da warning, porque sí existe desde Java 10).

4. Compila con Java 21 (la meta de la migración):

```bash
javac --release 21 Demo.java
java Demo
```

Resultado esperado: compila y corre, imprimiendo el mensaje y el text block.

## Mensaje pedagógico
- El `--release` no es solo el JDK que tienes instalado (aquí usamos JDK 25 para simular JDK 21), sino el **nivel de lenguaje objetivo**.
- Esto es exactamente lo que las herramientas de análisis (Módulo 7: `jdeps`, `jdeprscan`, OpenRewrite) automatizan a gran escala: detectar todos los puntos de un proyecto real que no compilan en la versión destino.
- Punto de discusión: "en un proyecto de 500,000 líneas, ¿cómo encontrarían todos estos puntos sin hacerlo archivo por archivo?" → conecta con Módulo 7.

## Limpieza
Después de la demo, borra los `.class` generados:

```bash
rm -f Demo.class
```
