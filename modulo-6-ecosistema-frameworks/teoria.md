# Módulo 6. Ecosistema y Frameworks Modernos (4 horas)

> Objetivo: dominar las herramientas de build modernas (**Maven** y **Gradle**), comprender el salto a **Spring Boot 3** y **Jakarta EE**, ejecutar la migración **javax → jakarta**, adoptar **Hibernate 6**, validar **compatibilidad de librerías y drivers JDBC**, y aplicar **buenas prácticas** para mantener dependencias actualizadas en proyectos Java 21.
>
> Ejercicios Maven + JUnit 5: [`ejercicios/`](./ejercicios/).
> Soluciones del facilitador (local, no en el repo): `soluciones-modulo-6/`.

---

## Sección 1: Maven moderno y Gradle (60 min)

### Maven en 2024–2026

| Tema | Qué cambió / buena práctica |
|------|-----------------------------|
| **Java version** | Usar `<maven.compiler.release>21</maven.compiler.release>` en lugar de `source`/`target` separados |
| **BOM** | Importar BOM de Spring Boot / Jakarta para alinear versiones transitivas |
| **Toolchains** | Compilar con JDK distinto al que corre Maven (`maven-toolchains-plugin`) |
| **Maven 4** | Mejoras en resolución de dependencias y POM simplificado (awareness) |
| **Plugins** | `maven-compiler-plugin` 3.13+, `maven-surefire-plugin` 3.5+ para JUnit 5 |

### POM mínimo Java 21

```xml
<properties>
    <maven.compiler.release>21</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.4.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Comandos útiles en clase

```bash
mvn dependency:tree                    # árbol de dependencias
mvn dependency:analyze               # dependencias no usadas / faltantes
mvn versions:display-dependency-updates  # actualizaciones disponibles
mvn -q -DskipTests package             # build rápido
```

### Gradle moderno (contraste)

```kotlin
// build.gradle.kts
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}
```

**Mensaje para la clase:** Maven y Gradle no son el obstáculo de la migración, pero un POM/Gradle desactualizado **bloquea** el paso a Java 21 y Spring Boot 3.

### Demo
Ver `demos/01-maven-moderno/README.md` y `demos/02-gradle-moderno/README.md`.

### Práctica
```bash
cd ejercicios && mvn test -Dtest=MavenPomExercisesTest
```

---

## Sección 2: Spring Boot 3 y Jakarta EE (60 min)

### La línea divisoria: Spring Boot 2 vs 3

| Aspecto | Spring Boot 2.x | Spring Boot 3.x |
|---------|-----------------|-----------------|
| Java mínimo | 8 (17 recomendado al final) | **17** (21 recomendado) |
| Namespace EE | `javax.*` | **`jakarta.*`** |
| Servlet | javax.servlet 4 | jakarta.servlet 6 |
| JPA | javax.persistence 2.x | jakarta.persistence 3.x |
| Hibernate | 5.x | **6.x** |
| Spring Framework | 5.x | **6.x** |

### Spring Boot 3 + Java 21

- **Spring Boot 3.2+** es la línea estable para Java 21 en producción.
- Virtual Threads: `spring.threads.virtual.enabled=true` (Spring Boot 3.2+).
- Observabilidad: Micrometer, Actuator y tracing alineados con el ecosistema actual.

### Jakarta EE 10 (awareness)

Jakarta EE es el estándar empresarial que reemplazó Java EE. Los nombres de paquete cambiaron (`javax` → `jakarta`), pero el modelo mental (CDI, JPA, JAX-RS, Servlet) se mantiene.

```
Java EE 8 (javax)  →  Jakarta EE 9+ (jakarta)
     ↓                        ↓
Spring Boot 2            Spring Boot 3
```

### Checklist de arranque Spring Boot 3

1. Subir a **Java 17+** (idealmente 21).
2. Actualizar Spring Boot a **3.2+**.
3. Ejecutar migración `javax` → `jakarta` (OpenRewrite o IDE).
4. Revisar propiedades `application.yml` renombradas.
5. Actualizar starters de terceros (Springdoc, Spring Security, etc.).

### Demo
Ver `demos/04-spring-boot-3/SpringBoot3Cambios.java`.

### Práctica
```bash
mvn test -Dtest=SpringBoot3ExercisesTest
```

---

## Sección 3: Migración javax.* → jakarta.* (45 min)

### Qué migra y qué NO

| Migra a jakarta | Permanece en javax (JDK) |
|-----------------|--------------------------|
| `javax.persistence` | `javax.crypto` |
| `javax.servlet` | `javax.net` |
| `javax.validation` | `javax.sql` |
| `javax.annotation` | `javax.swing` |
| `javax.xml.bind` | `javax.management` |
| `javax.inject` | `javax.security` |
| `javax.ws.rs` | `javax.naming` |
| `javax.transaction` | `javax.tools` |

### Tabla de dependencias Maven

| Antes (javax) | Después (jakarta) |
|---------------|-------------------|
| `javax.persistence:javax.persistence-api` | `jakarta.persistence:jakarta.persistence-api` |
| `javax.servlet:javax.servlet-api` | `jakarta.servlet:jakarta.servlet-api` |
| `javax.validation:validation-api` | `jakarta.validation:jakarta.validation-api` |
| `javax.annotation:javax.annotation-api` | `jakarta.annotation:jakarta.annotation-api` |
| `javax.xml.bind:jaxb-api` | `jakarta.xml.bind:jakarta.xml.bind-api` |

### Herramientas de migración automática

```bash
# OpenRewrite (recomendado en CI)
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run \
  -Drewrite.activeRecipes=org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_2
```

También: plugin IntelliJ [Jakarta Migration (javax → jakarta)](https://plugins.jetbrains.com/plugin/30093-jakarta-migration-javax--jakarta-) (análisis + refactor OpenRewrite en el IDE) y Spring Boot Migrator (SBM).

### Errores típicos en compilación

```
package javax.persistence does not exist
cannot find symbol: class Entity
```

**Causa:** código o dependencia aún en `javax`. **Solución:** actualizar imports y coordenadas Maven.

### Demo
Ver `demos/03-jakarta-migracion/MigracionJakarta.java`.

### Lab hands-on (OpenRewrite + IntelliJ + SBM)

Mini Spring Boot 2 legacy listo para migrar:

```bash
cd migracion-legacy
mvn clean compile
./migrate.sh dry-run    # preview OpenRewrite
./migrate.sh run        # aplicar (usa git para revertir)
```

Guía completa: [`migracion-legacy/README.md`](./migracion-legacy/README.md).

### Práctica
```bash
mvn test -Dtest=JakartaMigracionExercisesTest
```

---

## Sección 4: Hibernate 6 (45 min)

### Cambios principales respecto a Hibernate 5

| Área | Hibernate 5 | Hibernate 6 |
|------|-------------|-------------|
| Namespace | `javax.persistence` | `jakarta.persistence` |
| Dialectos | `MySQL5Dialect`, `PostgreSQL95Dialect` | `MySQLDialect`, `PostgreSQLDialect` |
| Tipos | Muchos tipos legacy | Simplificación del modelo de tipos |
| UUID | Generadores legacy | `UuidGenerator` / estándar JPA |
| Spatial | En core | Módulo `hibernate-spatial` aparte |
| Query | HQL/JPQL con cambios menores | Mejor tipado, menos APIs internas |

### Propiedades que ya no aplican

```properties
# Hibernate 5 (deprecado / eliminado en 6)
hibernate.id.new_generator_mappings=true
hibernate.jdbc.lob.non_contextual_creation=true

# Hibernate 6 — preferir
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### Migración de entidades (ejemplo)

```java
// Antes
import javax.persistence.Entity;
import javax.persistence.Id;

// Después
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
```

### Con Spring Boot 3

Spring Boot 3 trae Hibernate 6 por defecto. Si tu app arranca con errores de dialecto o `ClassNotFoundException` de `javax.persistence`, aún hay código o librerías sin migrar.

### Demo
Ver `demos/05-hibernate-6/Hibernate6Cambios.java`.

### Práctica
```bash
mvn test -Dtest=Hibernate6ExercisesTest
```

---

## Sección 5: Compatibilidad de librerías y drivers JDBC (30 min)

### Matriz rápida Java 21

| Componente | Versión mínima recomendada |
|------------|---------------------------|
| Spring Boot | 3.2+ |
| Hibernate | 6.4+ (vía Spring Boot BOM) |
| PostgreSQL JDBC | 42.7+ |
| MySQL Connector/J | 8.2+ (`com.mysql:mysql-connector-j`) |
| Oracle JDBC | 23.x+ (ojdbc11) |
| HikariCP | 5.1+ (incluido en Spring Boot 3) |
| Jackson | 2.15+ (2.17+ con SB 3.3+) |
| Lombok | 1.18.30+ |

### Drivers JDBC: cambios frecuentes

```properties
# MySQL legacy (NO usar)
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# MySQL moderno
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

```xml
<!-- Coordenada antigua -->
<artifactId>mysql-connector-java</artifactId>
<!-- Coordenada actual -->
<artifactId>mysql-connector-j</artifactId>
```

### Cómo validar compatibilidad

1. `mvn dependency:tree` — buscar `javax.*` transitivos.
2. Revisar release notes de cada librería crítica.
3. Probar arranque + tests de integración con JDK 21 en CI.
4. Staging con la misma versión de driver que producción.

### Demo
Ver `demos/06-jdbc-compatibilidad/JdbcCompatibilidad.java`.

### Práctica
```bash
mvn test -Dtest=JdbcCompatibilidadExercisesTest
```

---

## Sección 6: Buenas prácticas para actualización de dependencias + Laboratorio (40 min)

### Estrategia incremental (no “big bang”)

```
1. Inventario     → dependency:tree + SBOM
2. JDK            → subir a Java 21 en CI (sin cambiar framework)
3. Build          → Maven/Gradle plugins al día
4. Spring Boot 3  → migración jakarta + Hibernate 6
5. Drivers/libs   → actualizar conectores y clientes HTTP
6. Observar       → monitorear en staging antes de prod
```

### Herramientas

| Herramienta | Uso |
|-------------|-----|
| `versions-maven-plugin` | Ver actualizaciones disponibles |
| OWASP Dependency-Check | CVEs conocidos |
| Renovate / Dependabot | PRs automáticos de actualización |
| OpenRewrite | Migraciones masivas (javax→jakarta, SB2→3) |
| `jdeps` | APIs internas del JDK |

### Política de actualización sugerida

- **Parches de seguridad:** aplicar en días.
- **Minor de framework:** planificar por sprint con tests de regresión.
- **Major (SB 2→3, Hibernate 5→6):** proyecto dedicado con checklist.

### Laboratorio
Migrar el descriptor de dependencias legacy (`LegacyEcosystemPom.xml`) a un stack compatible con Java 21 + Spring Boot 3.

```bash
mvn test -Dtest=MigracionEcosistemaTest
```

Referencia sin Maven: carpeta `laboratorio/`.

### Cierre del módulo
"Java 21 no se adopta solo cambiando el JDK: el **ecosistema** (Maven, Spring Boot 3, Jakarta, Hibernate 6, drivers JDBC) debe moverse en bloque. La migración exitosa es un pipeline de build verde en JDK 21, sin `javax.persistence` en el classpath y con dependencias sin CVEs críticos abiertos."
