# Laboratorio: migración de código legacy de plataforma

## Objetivo
Refactorizar `LegacyPlatformService.java`, que mezcla patrones incompatibles con Java 21:
- Uso de paquetes internos del JDK (`jdk.internal.*`)
- Referencias a APIs removidas del JDK
- Configuración TLS insegura

## Pasos
1. Lee `LegacyPlatformService.java` y localiza los anti-patrones.
2. Implementa las correcciones en el ejercicio 05 (`MigracionEmpresarial`).
3. Compara con `SolucionMigrada.java`.

## Versión con tests (recomendada en clase)
```bash
cd ../ejercicios && mvn test -Dtest=MigracionEmpresarialTest
```

## IntelliJ IDEA

Abre `modulo-5-modularidad-seguridad/pom.xml` como proyecto Maven. Las clases del laboratorio están en el submódulo `modulo-5-laboratorio`; usa Run en `LegacyPlatformService` o `SolucionMigrada`.
