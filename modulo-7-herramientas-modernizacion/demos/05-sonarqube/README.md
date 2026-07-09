# Demo: SonarQube

Calidad continua como gobernanza de la migración.

## Setup rápido (Docker, opcional en clase)

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:community
# Abrir http://localhost:9000 — user/pass inicial admin/admin
```

## Análisis Maven (cuando el server esté arriba)

```bash
cd modulo-7-herramientas-modernizacion/app-legacy

mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=modulo7-app-legacy \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=$SONAR_TOKEN
```

## Sin server (demo de pizarra)

Mostrar capturas / UI de:

- Issues por regla Java (deprecated, security hotspots)
- Quality Gate: New Code vs Overall
- Cómo un PR de migración falla el gate por bugs nuevos

## Quality Gate sugerido (migración)

| Condición | Valor |
|-----------|-------|
| Bugs on New Code | 0 |
| Vulnerabilities on New Code | 0 |
| Security Hotspots Reviewed | 100% |
| Coverage on New Code | ≥ 80% |

## Relación con otras herramientas

```
jdeps / jdeprscan  →  inventario técnico
OpenRewrite        →  cambio mecánico
SonarQube          →  no regresar deuda en el PR
```
