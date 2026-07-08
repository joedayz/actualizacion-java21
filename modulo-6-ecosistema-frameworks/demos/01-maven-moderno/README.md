# Demo: Maven moderno para Java 21

## Requisitos

- JDK 21+
- Maven 3.9+

## 1. Verificar toolchain

```bash
mvn -version
java -version
```

## 2. Explorar el POM de ejercicios del curso

```bash
cd modulo-6-ecosistema-frameworks/ejercicios
cat pom.xml
```

Observa:
- `<maven.compiler.release>21</maven.compiler.release>`
- Plugins de compiler y surefire actualizados

## 3. Árbol de dependencias

```bash
mvn dependency:tree
```

## 4. Buscar actualizaciones (requiere plugin en proyectos reales)

```bash
# Ejemplo en un proyecto con versions-maven-plugin configurado:
mvn versions:display-dependency-updates
mvn versions:display-plugin-updates
```

## 5. BOM de Spring Boot (ejemplo conceptual)

En aplicaciones reales, importar el BOM evita conflictos de versiones:

```xml
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

## Preguntas para la clase

1. ¿Por qué `release` es mejor que `source` + `target`?
2. ¿Qué dependencias transitivas traen `javax.persistence` en un proyecto legacy?
3. ¿Cómo detectarías una dependencia duplicada en el árbol?
