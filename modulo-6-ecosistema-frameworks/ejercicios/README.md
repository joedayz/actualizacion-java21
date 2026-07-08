# Módulo 6 — Ejercicios (participantes)

Proyecto Maven con **5 ejercicios** y tests **JUnit 5**. Completa los `TODO` hasta que `mvn test` quede en verde.

## Requisitos

- JDK **21+**
- Maven **3.9+**

## IntelliJ IDEA

Abre el proyecto Maven **raíz del módulo** (no la carpeta del repo completo):

```
modulo-6-ecosistema-frameworks/pom.xml
```

IntelliJ importará 4 submódulos:

| Módulo | Contenido | Ejecutar |
|--------|-----------|----------|
| `modulo-6-ejercicios` | Ejercicios con tests JUnit | Click derecho en `*Test` → Run |
| `modulo-6-demos` | `MigracionJakarta`, `SpringBoot3Cambios`, `Hibernate6Cambios`, `JdbcCompatibilidad` | Run en la clase `main` |
| `modulo-6-laboratorio` | `LegacyEcosystemService`, `SolucionMigrada` | Run en la clase `main` |
| `migracion-legacy` | Spring Boot 2 + javax (lab OpenRewrite / IntelliJ / SBM) | Ver `migracion-legacy/README.md` |

Tras abrir el `pom.xml`, espera a que Maven termine de importar y verifica que el SDK del proyecto sea **21+** (`File → Project Structure → Project SDK`).

Las carpetas `demos/01-maven-moderno`, `02-gradle-moderno` y `07-dependencias` son guías en Markdown (comandos Maven/Gradle), no clases Java.

## Cómo trabajar

```bash
cd modulo-6-ecosistema-frameworks/ejercicios

mvn test

# Por ejercicio
mvn test -Dtest=MavenPomExercisesTest
mvn test -Dtest=JakartaMigracionExercisesTest
mvn test -Dtest=SpringBoot3ExercisesTest
mvn test -Dtest=Hibernate6ExercisesTest
mvn test -Dtest=MigracionEcosistemaTest
```

## Ejercicios

| # | Paquete | Tema | Tests |
|---|---------|------|-------|
| 01 | `ejercicio01` | Análisis de POM Maven moderno | `MavenPomExercisesTest` |
| 02 | `ejercicio02` | Migración javax → jakarta | `JakartaMigracionExercisesTest` |
| 03 | `ejercicio03` | Spring Boot 3 y compatibilidad Java 21 | `SpringBoot3ExercisesTest` |
| 04 | `ejercicio04` | Hibernate 6: dialectos y propiedades | `Hibernate6ExercisesTest` |
| 05 | `ejercicio05` | Laboratorio: migración de ecosistema | `MigracionEcosistemaTest` |

## Demo dependency:tree (opcional)

```bash
mvn -q dependency:tree
```
