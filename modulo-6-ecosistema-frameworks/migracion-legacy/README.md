# Lab: migración javax → jakarta (3 herramientas)

Mini **Spring Boot 2.7** con código legacy real:

- `javax.persistence` en `Cliente.java`
- `javax.annotation.PostConstruct` en `ClienteService.java`
- `javax.validation` en `ClienteController.java`
- `mysql-connector-java` y dialecto Hibernate 5 en `application.properties`

Sirve para practicar las **3 herramientas** de la Sección 3 (además del Ejercicio 02 manual).

## Scripts incluidos

| SO | Script | Uso |
|----|--------|-----|
| **Windows** | `migrate.ps1` | `.\migrate.ps1 dry-run` |
| macOS / Linux | `migrate.sh` | `./migrate.sh dry-run` |

Comandos del script: `dry-run` (preview), `run` (aplicar), `verify` (revisar javax/jakarta).

---

## Antes de empezar

### Windows (PowerShell)

```powershell
cd modulo-6-ecosistema-frameworks\migracion-legacy
git status
mvn -q clean compile
Select-String -Path src\main\java\**\*.java -Pattern "javax\."
```

### macOS / Linux (bash)

```bash
cd modulo-6-ecosistema-frameworks/migracion-legacy
git status
mvn -q clean compile
grep -R "javax\." src/main/java
```

Debes ver imports `javax.persistence`, `javax.validation`, `javax.annotation`.

---

## Herramienta 1 — OpenRewrite (terminal / CI)

El `pom.xml` ya trae el plugin configurado.

### Vista previa (no modifica archivos)

**Windows / macOS / Linux (Maven es igual):**

```powershell
mvn -U org.openrewrite.maven:rewrite-maven-plugin:dryRun
```

Revisa el diff en consola: imports `javax.*` → `jakarta.*`, parent Spring Boot 3.x, driver JDBC, etc.

También se genera un patch en `target/rewrite/rewrite.patch`.

### Aplicar cambios

```powershell
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run
mvn clean compile
```

**Verificar imports jakarta:**

```powershell
# Windows
Select-String -Path src\main\java\**\*.java -Pattern "jakarta\."

# macOS / Linux
grep -R "jakarta\." src/main/java
```

Para revertir: `git checkout -- .`

### Atajo con script (recomendado)

**Windows:**

```powershell
.\migrate.ps1 dry-run
.\migrate.ps1 run
.\migrate.ps1 verify
```

**macOS / Linux:**

```bash
./migrate.sh dry-run
./migrate.sh run
./migrate.sh verify
```

---

## Herramienta 2 — Plugin [Jakarta Migration (javax → jakarta)](https://plugins.jetbrains.com/plugin/30093-jakarta-migration-javax--jakarta-)

Herramienta vigente en IntelliJ (Community y Ultimate). Analiza el proyecto, muestra dependencias y aplica refactorings con OpenRewrite desde el IDE.

### Instalación

1. **Settings** (`Ctrl+Alt+S` / `Cmd+,`) → **Plugins** → **Marketplace**.
2. Busca **Jakarta Migration** e instala [desde el Marketplace](https://plugins.jetbrains.com/plugin/30093-jakarta-migration-javax--jakarta-).
3. Reinicia IntelliJ.

### Uso en este lab

1. Abre `modulo-6-ecosistema-frameworks/pom.xml` en IntelliJ.
2. Navega al submódulo **`migracion-legacy`**.
3. **Tools** → **Jakarta Migration** (atajo: `Ctrl+Shift+J` / `Cmd+Shift+J`).
4. En la ventana del plugin, **Analyze Project** y revisa el informe (`javax.*`, blockers, dependencias).
5. Pestaña **Refactor**: aplica las recetas OpenRewrite y revisa el preview antes de confirmar.
6. Compara el resultado con el diff de `./migrate.sh run` (Herramienta 1).

**Tip:** haz esto en una rama o copia del proyecto; es destructivo como `rewrite:run`.

> **Nota:** IntelliJ Ultimate incluye también **Refactor → Migrate Packages and Classes → to Jakarta EE**; para este curso usamos el plugin anterior, que es la opción actual recomendada y funciona en Community.

---

## Herramienta 3 — Spring Boot Migrator (SBM)

Herramienta aparte (no Maven). Útil cuando quieres un informe guiado SB2 → SB3.

En clase, lo más práctico es **mostrar la UI/CLI de SBM** apuntando a esta carpeta `migracion-legacy` y contrastar el plan de migración con OpenRewrite.

Documentación: https://github.com/spring-projects-experimental/spring-boot-migrator

---

## Flujo sugerido en clase (45 min)

| Min | Actividad |
|-----|-----------|
| 5 | Demo `MigracionJakarta` (módulo demos) — mapa conceptual |
| 15 | Ejercicio 02 manual (`JakartaMigracionExercisesTest`) |
| 10 | Este lab: `.\migrate.ps1 dry-run` (o `./migrate.sh dry-run`) y revisar diff |
| 10 | `.\migrate.ps1 run` **o** plugin [Jakarta Migration](https://plugins.jetbrains.com/plugin/30093-jakarta-migration-javax--jakarta-) |
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

### Windows (PowerShell)

```powershell
.\migrate.ps1 verify
mvn dependency:tree | Select-String -Pattern "javax|jakarta"
mvn spring-boot:run
Invoke-WebRequest http://localhost:8080/api/health
```

### macOS / Linux

```bash
./migrate.sh verify
mvn dependency:tree | grep -E "javax\.|jakarta\."
mvn spring-boot:run
curl http://localhost:8080/api/health
```

Tras migrar bien, no deberían quedar dependencias EE en `javax.*` (salvo JDK como `javax.sql` si aparece transitivo del JDK).
