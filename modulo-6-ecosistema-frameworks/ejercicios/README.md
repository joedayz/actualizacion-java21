# Módulo 6 — Ejercicios (participantes)

Proyecto Maven con **5 ejercicios** y tests **JUnit 5**. Completa los `TODO` hasta que `mvn test` quede en verde.

## Requisitos

- JDK **21+**
- Maven **3.9+**

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
