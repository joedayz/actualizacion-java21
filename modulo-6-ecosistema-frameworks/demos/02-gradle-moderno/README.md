# Demo: Gradle moderno para Java 21

Gradle es alternativa común en equipos que migran desde Maven o usan Kotlin DSL.

## build.gradle.kts de referencia

```kotlin
plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}

tasks.test {
    useJUnitPlatform()
}
```

## Comandos equivalentes a Maven

| Maven | Gradle |
|-------|--------|
| `mvn test` | `./gradlew test` |
| `mvn dependency:tree` | `./gradlew dependencies` |
| `mvn package` | `./gradlew build` |
| `mvn versions:display-dependency-updates` | `./gradlew dependencyUpdates` (plugin) |

## Migración javax → jakarta en Gradle

```kotlin
// Buscar dependencias javax en el classpath
configurations.compileClasspath.get().resolvedConfiguration
    .firstLevelModuleDependencies
    .forEach { println(it.moduleGroup + ":" + it.moduleName) }
```

## Preguntas para la clase

1. ¿Qué ventaja da `JavaLanguageVersion.of(21)` con toolchains?
2. ¿Cómo importarías un BOM en Gradle vs Maven?
3. ¿En qué casos mantendrías Maven y en cuáles migrarías a Gradle?
