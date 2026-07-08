# Laboratorio: migración del ecosistema legacy a Java 21

## Objetivo

Analizar `LegacyEcosystemPom.xml` y `LegacyEcosystemService.java`, que representan un proyecto Spring Boot 2 con dependencias javax, Hibernate 5 y drivers JDBC antiguos. Completar el ejercicio 05 (`MigracionEcosistema`) con la lógica de clasificación y migración.

## Pasos

1. Lee `LegacyEcosystemPom.xml` e identifica dependencias a migrar.
2. Lee `LegacyEcosystemService.java` y localiza imports javax y el driver JDBC legacy.
3. Implementa los `TODO` en `MigracionEcosistema.java`.
4. Compara con `SolucionMigrada.java`.

## Versión con tests (recomendada en clase)

```bash
cd ../ejercicios && mvn test -Dtest=MigracionEcosistemaTest
```

## Checklist de migración (discusión en grupo)

- [ ] Parent Spring Boot 2.7 → 3.4.x
- [ ] `javax.persistence` → `jakarta.persistence`
- [ ] `mysql-connector-java` → `mysql-connector-j`
- [ ] Dialecto Hibernate 5 → Hibernate 6
- [ ] `maven.compiler.release` = 21
- [ ] CI ejecutando tests con JDK 21
