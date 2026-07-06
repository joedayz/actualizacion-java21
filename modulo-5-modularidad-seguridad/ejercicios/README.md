# Módulo 5 — Ejercicios (participantes)

Proyecto Maven con **5 ejercicios** y tests **JUnit 5**. Completa los `TODO` hasta que `mvn test` quede en verde.

## Requisitos

- JDK **21+**
- Maven **3.9+**

## Cómo trabajar

```bash
cd modulo-5-modularidad-seguridad/ejercicios

mvn test

# Por ejercicio
mvn test -Dtest=ModuleInfoExercisesTest
mvn test -Dtest=EncapsulacionExercisesTest
mvn test -Dtest=ReflectionMigracionTest
mvn test -Dtest=TlsCriptografiaTest
mvn test -Dtest=MigracionEmpresarialTest
```

## Ejercicios

| # | Paquete | Tema | Tests |
|---|---------|------|-------|
| 01 | `ejercicio01` | Parser de `module-info` simplificado | `ModuleInfoExercisesTest` |
| 02 | `ejercicio02` | Encapsulación y `--add-opens` | `EncapsulacionExercisesTest` |
| 03 | `ejercicio03` | Migración de reflection / APIs internas | `ReflectionMigracionTest` |
| 04 | `ejercicio04` | TLS y protocolos seguros | `TlsCriptografiaTest` |
| 05 | `ejercicio05` | Laboratorio: migración empresarial | `MigracionEmpresarialTest` |

## Demo jdeps (opcional)

```bash
mvn -q package -DskipTests
jdeps --jdk-internals --multi-release 21 target/modulo-5-ejercicios-1.0.0-SNAPSHOT.jar
```
