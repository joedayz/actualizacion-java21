# Lab: migración javax → jakarta (3 herramientas)

Mini **Spring Boot 2.7** con código legacy real:

- `javax.persistence` en `Cliente.java`
- `javax.annotation.PostConstruct` en `ClienteService.java`
- `javax.validation` en `ClienteController.java`
- `mysql-connector-java` y dialecto Hibernate 5 en `application.properties`

Sirve para practicar las **3 herramientas** de la Sección 3 (además del Ejercicio 02 manual).

## Antes de empezar

```bash
cd modulo-6-ecosistema-frameworks/migracion-legacy
git status   # idealmente working tree limpio
mvn -q clean compile
grep -R "javax\." src/main/java
```

Debes ver imports `javax.persistence`, `javax.validation`, `javax.annotation`.

---

## Herramienta 1 — OpenRewrite (terminal / CI)

El `pom.xml` ya trae el plugin configurado.

### Vista previa (no modifica archivos)

```bash
mvn -U org.openrewrite.maven:rewrite-maven-plugin:dryRun
```

Revisa el diff en consola: imports `javax.*` → `jakarta.*`, parent Spring Boot 3.x, driver JDBC, etc.

### Aplicar cambios

```bash
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run
mvn clean compile
grep -R "jakarta\." src/main/java
```

Para revertir: `git checkout -- .`

### Atajo con script

```bash
./migrate.sh dry-run
./migrate.sh run
```

---

## Herramienta 2 — IntelliJ *Migrate to Jakarta EE*

1. Abre `modulo-6-ecosistema-frameworks/pom.xml` en IntelliJ.
2. Navega al submódulo **`migracion-legacy`**.
3. Click derecho en `src/main/java` → **Refactor** → **Migrate Packages and Classes** → **to Jakarta EE**.
4. Revisa el preview y aplica.
5. Compara con el resultado de OpenRewrite.

**Tip:** haz esto en una rama o copia del proyecto; es destructivo como `rewrite:run`.

---

## Herramienta 3 — Spring Boot Migrator (SBM)

Herramienta aparte (no Maven). Útil cuando quieres un informe guiado SB2 → SB3.

```bash
# Instalación (requiere red)
curl -G https://start.springboot.io/starter.zip \
  -d dependencies=spring-boot-migrator \
  -d type=maven-project \
  -d baseDir=sbm-demo \
  -d groupId=pe.joedayz \
  -d artifactId=sbm-demo \
  -d name=sbm-demo \
  -d packageName=pe.joedayz.sbm \
  -d javaVersion=17 \
  -o sbm-demo.zip
```

En clase, lo más práctico es **mostrar la UI/CLI de SBM** apuntando a esta carpeta `migracion-legacy` y contrastar el plan de migración con OpenRewrite.

Documentación: https://github.com/spring-projects-experimental/spring-boot-migrator

---

## Flujo sugerido en clase (45 min)

| Min | Actividad |
|-----|-----------|
| 5 | Demo `MigracionJakarta` (módulo demos) — mapa conceptual |
| 15 | Ejercicio 02 manual (`JakartaMigracionExercisesTest`) |
| 10 | Este lab: `mvn rewrite:dryRun` y revisar diff |
| 10 | `mvn rewrite:run` **o** IntelliJ Migrate to Jakarta |
| 5 | Mostrar SBM como alternativa / informe |

---

## Relación con el Ejercicio 02

| Ejercicio 02 (manual) | Este lab (automático) |
|-----------------------|------------------------|
| `requiereMigracionJakarta()` | OpenRewrite decide qué archivos tocar |
| `migrarTipo()` | Reemplaza imports en todo el proyecto |
| `coordenadaMavenJakarta()` | Actualiza `pom.xml` (parent, drivers, APIs) |

Misma lógica; distinta escala.

---

## Verificación post-migración

```bash
mvn dependency:tree | grep -E "javax\.|jakarta\."
mvn spring-boot:run
curl http://localhost:8080/api/health
```

Tras migrar bien, no deberían quedar dependencias EE en `javax.*` (salvo JDK como `javax.sql` si aparece transitivo del JDK).
