# Demo: buenas prácticas para actualizar dependencias

## 1. Inventario con Maven

```bash
cd modulo-6-ecosistema-frameworks/ejercicios
mvn dependency:tree
mvn dependency:analyze
```

## 2. Buscar javax transitivos

```bash
mvn dependency:tree | grep javax
```

Si aparece `javax.persistence`, `javax.servlet`, etc., hay dependencias sin migrar.

## 3. Plugin de versiones (proyectos reales)

Agregar a `pom.xml`:

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>versions-maven-plugin</artifactId>
    <version>2.17.1</version>
</plugin>
```

```bash
mvn versions:display-dependency-updates
mvn versions:display-plugin-updates
```

## 4. Seguridad — OWASP Dependency-Check

```bash
mvn org.owasp:dependency-check-maven:check
```

Revisar CVEs críticos antes de cada release.

## 5. Automatización en GitHub/GitLab

- **Dependabot** / **Renovate**: PRs automáticos semanales.
- Política: parches de seguridad inmediatos; minors planificados por sprint.

## 6. Migración masiva con OpenRewrite

```bash
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run \
  -Drewrite.activeRecipes=org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_2
```

## Flujo sugerido para equipos

```
dependency:tree → identificar javax / versiones viejas
       ↓
actualizar BOM (Spring Boot 3.4.x)
       ↓
OpenRewrite / IDE para jakarta
       ↓
mvn test en JDK 21
       ↓
staging + smoke tests
```

## Preguntas para la clase

1. ¿Con qué frecuencia actualizarían dependencias en producción?
2. ¿Cómo priorizar un CVE vs una actualización major de framework?
3. ¿Qué pondrían en el pipeline de CI para evitar regresiones?
