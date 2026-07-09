# Módulo 7 — Ejercicios (participantes)

Proyecto Maven con **5 ejercicios** y tests **JUnit 5**. Completa los `TODO` hasta que `mvn test` quede en verde (si el facilitador dejó stubs; en esta entrega las soluciones de referencia ya pasan).

## Requisitos

- JDK **21+**
- Maven **3.9+**

## IntelliJ IDEA

Abre el proyecto Maven **raíz del módulo** (no la carpeta del repo completo):

```
modulo-7-herramientas-modernizacion/pom.xml
```

IntelliJ importará 4 submódulos:

| Módulo | Contenido | Ejecutar |
|--------|-----------|----------|
| `modulo-7-ejercicios` | Ejercicios con tests JUnit | Click derecho en `*Test` → Run |
| `modulo-7-demos` | `UsaUnsafeLegacy`, `UsaApiDeprecada` | Run + `jdeps`/`jdeprscan` |
| `modulo-7-laboratorio` | `LegacyAnalisisService`, `SolucionAnalisis` | Run en la clase `main` |
| `modulo-7-app-legacy` | Mini app + `analyze.sh` / OpenRewrite | Ver `app-legacy/README.md` |

Tras abrir el `pom.xml`, espera a que Maven termine de importar y verifica que el SDK del proyecto sea **21+**.

Las carpetas `demos/03-openrewrite` … `07-intellij-analisis` son guías en Markdown.

## Cómo trabajar

```bash
cd modulo-7-herramientas-modernizacion/ejercicios

mvn test

# Por ejercicio
mvn test -Dtest=JdepsExercisesTest
mvn test -Dtest=JdeprscanExercisesTest
mvn test -Dtest=OpenRewriteExercisesTest
mvn test -Dtest=SonarQubeExercisesTest
mvn test -Dtest=AnalisisLegacyExercisesTest
```

## Ejercicios

| # | Paquete | Tema | Tests |
|---|---------|------|-------|
| 01 | `ejercicio01` | Interpretar salida de jdeps | `JdepsExercisesTest` |
| 02 | `ejercicio02` | Interpretar salida de jdeprscan | `JdeprscanExercisesTest` |
| 03 | `ejercicio03` | OpenRewrite: goals y recetas | `OpenRewriteExercisesTest` |
| 04 | `ejercicio04` | SonarQube Quality Gate + Revapi | `SonarQubeExercisesTest` |
| 05 | `ejercicio05` | Laboratorio: pipeline de análisis | `AnalisisLegacyExercisesTest` |

## Lab hands-on

```bash
cd ../app-legacy
./analyze.sh all          # macOS/Linux
# .\analyze.ps1 all       # Windows
```
