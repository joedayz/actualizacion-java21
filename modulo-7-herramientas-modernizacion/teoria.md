# Módulo 7. Herramientas de Modernización y Migración (4 horas)

> Objetivo: dominar el **kit de análisis y refactoring** para migrar aplicaciones Java 8 → Java 21 a escala: **jdeps**, **jdeprscan**, **OpenRewrite**, **Moderne**, **SonarQube**, **Revapi** y las **herramientas de análisis de IntelliJ IDEA**. El laboratorio cierra con un **análisis automatizado de una aplicación heredada**.
>
> Ejercicios Maven + JUnit 5: [`ejercicios/`](./ejercicios/).
> Soluciones del facilitador (local, no en el repo): `soluciones-modulo-7/`.

---

## Sección 1: jdeps — análisis de dependencias (40 min)

### Qué es y cuándo usarlo

`jdeps` (JDK Dependency Analysis Tool) inspecciona bytecode (`.class` / JAR) y reporta:

- Dependencias entre paquetes y módulos del JDK
- Uso de **APIs internas** (`sun.*`, `jdk.internal.*`)
- Resumen multi-release (útil con Java 9+)

Es el primer filtro en una migración: **¿qué usa realmente mi app del JDK y de terceros?**

### Comandos esenciales

```bash
# Resumen de dependencias de un JAR
jdeps target/mi-app.jar

# Solo APIs internas del JDK (hallazgos críticos)
jdeps --jdk-internals --multi-release 21 target/mi-app.jar

# Con classpath de librerías
jdeps --jdk-internals --class-path 'lib/*' target/mi-app.jar

# Vista por paquete (más detalle)
jdeps -verbose:package target/mi-app.jar
```

### Interpretar la salida

| Hallazgo | Severidad | Acción típica |
|----------|-----------|---------------|
| Solo `java.base`, `java.sql`, … | Baja | OK para Java 21 |
| `jdk.unsupported` / `sun.misc.Unsafe` | Alta | Sustituir por API pública o actualizar librería |
| `com.sun.*` no exportado | Alta | Buscar alternativa o `--add-exports` temporal |
| Dependencia a módulo removido | Crítica | Reescribir o traer dependencia externa (p. ej. JAXB) |

### Relación con el Módulo 5

En el Módulo 5 usaste `jdeps --jdk-internals` sobre demos con `Unsafe`. Aquí lo aplicas como **paso de inventario** en un pipeline de modernización, junto con el resto de herramientas.

### Demo
Ver `demos/01-jdeps/README.md`.

### Práctica
```bash
cd ejercicios && mvn test -Dtest=JdepsExercisesTest
```

---

## Sección 2: jdeprscan — APIs deprecadas (30 min)

### Qué detecta

`jdeprscan` escanea class files en busca de uso de elementos marcados `@Deprecated` (y, con opciones, deprecaciones del JDK).

```bash
# Escaneo básico
jdeprscan target/mi-app.jar

# Incluir deprecaciones del JDK (recomendado en migraciones)
jdeprscan --for-removal target/mi-app.jar

# Con classpath
jdeprscan --class-path 'lib/*' target/classes
```

### Por qué importa en Java 8 → 21

Muchas APIs usadas en Java 8 están **deprecadas para remoción** (`forRemoval=true`) o ya fueron eliminadas. `jdeprscan` no reescribe código: **prioriza el backlog** de deuda técnica antes de subir el JDK en CI.

### Flujo sugerido

```
1. Compilar con JDK 8/11 (baseline)
2. jdeprscan --for-removal
3. Ticket por cada API for-removal
4. Sustituir / encapsular
5. Subir JDK en CI y repetir
```

### Demo
Ver `demos/02-jdeprscan/README.md`.

### Práctica
```bash
mvn test -Dtest=JdeprscanExercisesTest
```

---

## Sección 3: OpenRewrite — refactoring automatizado (50 min)

### Qué es

[OpenRewrite](https://docs.openrewrite.org/) es un ecosistema de **recetas** (recipes) que transforman código fuente y descriptores de build de forma determinista. Ideal para:

- `javax.*` → `jakarta.*`
- Spring Boot 2 → 3
- Actualizar imports, propiedades y POMs a gran escala

Ya lo practicaste en el Módulo 6 (`migracion-legacy`). Aquí profundizamos el **modelo mental** y el encaje en CI.

### Modelo mental

```
Código + POM  →  Lossless Semantic Tree (LST)  →  Recipe  →  Diff / Patch
```

- **Dry-run:** preview sin tocar archivos (`rewrite:dryRun`)
- **Run:** aplica cambios (`rewrite:run`)
- **Recetas activas:** se declaran en el POM o en `rewrite.yml`

### Plugin Maven (patrón de clase)

```xml
<plugin>
    <groupId>org.openrewrite.maven</groupId>
    <artifactId>rewrite-maven-plugin</artifactId>
    <version>5.40.0</version>
    <configuration>
        <activeRecipes>
            <recipe>org.openrewrite.java.migrate.UpgradeToJava21</recipe>
        </activeRecipes>
    </configuration>
</plugin>
```

```bash
mvn -U org.openrewrite.maven:rewrite-maven-plugin:dryRun
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run
```

### Buenas prácticas

1. Siempre en rama / con git limpio.
2. Dry-run → revisar diff → run → `mvn test`.
3. Una familia de recetas a la vez (JDK, luego Jakarta, luego Spring).
4. No sustituye tests: **automatiza el cambio mecánico**, no la validación de negocio.

### Demo
Ver `demos/03-openrewrite/README.md` y el lab `app-legacy/` (OpenRewrite configurado).

### Práctica
```bash
mvn test -Dtest=OpenRewriteExercisesTest
```

---

## Sección 4: Moderne — escala organizacional (25 min)

### Qué es Moderne

[Moderne](https://www.moderne.io/) es la plataforma comercial construida sobre OpenRewrite. Añade:

- Inventario multi-repositorio
- Ejecución de recetas a escala (cientos/miles de repos)
- Dashboards de progreso de migración
- LST almacenado / actualizado de forma continua

### OpenRewrite vs Moderne

| Aspecto | OpenRewrite | Moderne |
|---------|-------------|---------|
| Alcance | Un repo / CI local | Organización completa |
| Costo | Open source | Plataforma (SaaS / self-hosted) |
| Uso típico | Equipo / pipeline | Programa de modernización enterprise |
| Recetas | Las mismas | Las mismas + orquestación |

**Mensaje para la clase:** OpenRewrite es el motor; Moderne es la **fábrica** cuando el problema es “200 microservicios en Java 8”.

### Demo
Ver `demos/04-moderne/README.md` (guía conceptual + flujo demo).

---

## Sección 5: SonarQube — calidad continua (30 min)

### Rol en la modernización

SonarQube no migra código: **mide y bloquea** deuda que empeora al subir de JDK:

- Code smells y bugs
- Vulnerabilidades (OWASP)
- Cobertura y duplicación
- Reglas específicas de Java (nullability, resource leaks, etc.)

### Quality Gate sugerido para migración

| Métrica | Umbral orientativo |
|---------|-------------------|
| Bugs nuevos | 0 |
| Vulnerabilidades nuevas | 0 |
| Coverage en código nuevo | ≥ 80% |
| Code smells críticos nuevos | 0 |

### Integración Maven

```bash
# Requiere SonarQube Server o SonarCloud
mvn verify sonar:sonar \
  -Dsonar.projectKey=mi-app \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=$SONAR_TOKEN
```

En clase: mostrar reglas Java relevantes y cómo un Quality Gate falla un PR con APIs deprecadas o issues de seguridad.

### Demo
Ver `demos/05-sonarqube/README.md`.

### Práctica
```bash
mvn test -Dtest=SonarQubeExercisesTest
```

---

## Sección 6: Revapi — compatibilidad de API (25 min)

### Qué es

[Revapi](https://revapi.org/) analiza cambios binarios/semánticos entre dos versiones de una biblioteca (JAR anterior vs JAR nuevo). Útil cuando:

- Publicas un SDK interno
- Migrar a Java 21 rompe clientes
- Quieres un reporte de **breaking changes** antes del release

### Ejemplo Maven

```xml
<plugin>
    <groupId>org.revapi</groupId>
    <artifactId>revapi-maven-plugin</artifactId>
    <version>0.15.0</version>
    <configuration>
        <oldArtifacts>
            <artifact>com.empresa:api:1.0.0</artifact>
        </oldArtifacts>
        <newArtifacts>
            <artifact>com.empresa:api:2.0.0-SNAPSHOT</artifact>
        </newArtifacts>
    </configuration>
</plugin>
```

```bash
mvn revapi:check
```

### Clasificación típica

| Cambio | Impacto |
|--------|---------|
| Método eliminado / firma cambiada | Breaking |
| Nuevo método en interfaz | Breaking (implementadores) |
| Anotación añadida no-breaking | Compatible |
| Deprecación | Compatible (con deuda) |

### Demo
Ver `demos/06-revapi/README.md`.

### Práctica
```bash
mvn test -Dtest=RevapiExercisesTest
```

---

## Sección 7: Análisis en IntelliJ IDEA + Laboratorio (40 min)

### Herramientas del IDE (Community / Ultimate)

| Acción | Atajo / menú | Uso en migración |
|--------|--------------|------------------|
| Inspect Code | `Analyze → Inspect Code` | Barrido de deprecations, nullability, unused |
| Dependency Analyzer | Maven tool window | Árbol de deps / conflictos |
| Migrate Packages… | Refactor (Ultimate) / plugin Jakarta | javax → jakarta |
| Structural Search | `Edit → Find → Search Structurally` | Patrones legacy custom |
| Problems tool window | Automático | Feedback continuo al editar |

### Flujo IDE recomendado en clase

1. Abrir `app-legacy/` como proyecto Maven.
2. `Analyze → Inspect Code` sobre el módulo.
3. Filtrar por **Deprecated API usage** y **Declaration redundancy**.
4. Contrastar hallazgos con `jdeps` / `jdeprscan` (misma app, distinta lente).
5. Opcional: plugin Jakarta Migration (ya visto en Módulo 6).

### Demo
Ver `demos/07-intellij-analisis/README.md`.

### Laboratorio: análisis automatizado de app heredada

La carpeta [`app-legacy/`](./app-legacy/) simula un servicio Java 8 con:

- Uso de API deprecada (`Date`, `Vector`, etc. según demos)
- Dependencia conceptual a APIs internas (strings / patrones)
- POM antiguo

Pasos:

```bash
cd app-legacy
mvn -q package -DskipTests
./analyze.sh          # jdeps + jdeprscan (macOS/Linux)
# o .\analyze.ps1 en Windows
```

Completa el ejercicio 05 (`AnalisisLegacyExercises`) con la lógica de clasificación de hallazgos.

```bash
cd ../ejercicios && mvn test -Dtest=AnalisisLegacyExercisesTest
```

Referencia sin scripts: carpeta `laboratorio/`.

### Cierre del módulo

"Modernizar no es solo cambiar el JDK: es un **pipeline de evidencia**. `jdeps` y `jdeprscan` inventarian; OpenRewrite/Moderne transforman; SonarQube y Revapi **gobiernan** calidad y compatibilidad; IntelliJ acelera el trabajo diario. El éxito se mide en build verde en Java 21 con Quality Gate y sin APIs internas ni deprecadas críticas."
