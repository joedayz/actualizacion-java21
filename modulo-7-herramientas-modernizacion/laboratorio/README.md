# Laboratorio: análisis automatizado de aplicaciones heredadas

## Objetivo

Analizar `LegacyAnalisisService.java` (hallazgos simulados de jdeps/jdeprscan) y completar
el ejercicio 05 (`AnalisisLegacyExercises`) con la lógica de clasificación y el orden del pipeline.

## Pasos

1. Lee `LegacyAnalisisService.java` e identifica tipos de hallazgo.
2. Compara con `SolucionAnalisis.java`.
3. Implementa los `TODO` en `AnalisisLegacyExercises.java` (si aún están).
4. Ejecuta el análisis real en `../app-legacy` con `./analyze.sh`.

## Versión con tests (recomendada en clase)

```bash
cd ../ejercicios && mvn test -Dtest=AnalisisLegacyExercisesTest
```

## IntelliJ IDEA

Abre `modulo-7-herramientas-modernizacion/pom.xml` como proyecto Maven. Las clases del
laboratorio están en el submódulo `modulo-7-laboratorio`.

## Checklist de análisis (discusión en grupo)

- [ ] `jdeps --jdk-internals` sin hallazgos críticos (o tickets abiertos)
- [ ] `jdeprscan --for-removal` priorizado
- [ ] OpenRewrite dry-run revisado
- [ ] Quality Gate SonarQube en verde en el PR
- [ ] Revapi (si hay API pública) sin breaking no documentados
