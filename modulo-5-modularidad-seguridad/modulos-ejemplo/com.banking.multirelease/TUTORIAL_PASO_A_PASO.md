# 📖 Tutorial Paso a Paso: Multi-Release Module Archives en Java 21

## Introducción

Este tutorial te guiará a través de la creación de un **Multi-Release JAR** que soporta múltiples versiones de Java (9, 11, 17, 21) con implementaciones específicas para cada versión.

---

## 📋 Tabla de Contenidos

1. [Conceptos Básicos](#conceptos-básicos)
2. [Requisitos](#requisitos)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Paso 1: Crear la Estructura](#paso-1-crear-la-estructura)
5. [Paso 2: Crear la Interfaz](#paso-2-crear-la-interfaz)
6. [Paso 3: Implementaciones Específicas](#paso-3-implementaciones-específicas)
7. [Paso 4: Factory Pattern](#paso-4-factory-pattern)
8. [Paso 5: Compilar Cada Versión](#paso-5-compilar-cada-versión)
9. [Paso 6: Crear el Manifest](#paso-6-crear-el-manifest)
10. [Paso 7: Empaquetar como JAR](#paso-7-empaquetar-como-jar)
11. [Paso 8: Verificar el JAR](#paso-8-verificar-el-jar)
12. [Paso 9: Ejecutar en Diferentes Versiones](#paso-9-ejecutar-en-diferentes-versiones)

---

## 🎯 Conceptos Básicos

### ¿Qué es un Multi-Release JAR?

Un **Multi-Release JAR** es un archivo JAR que contiene múltiples versiones de clases compiladas para diferentes versiones de Java. El JVM automáticamente carga la versión correcta según la versión de Java en la que se ejecute.

### Estructura Visual

```
multi-release.jar
│
├── Base (Java 9+)
│   ├── com/example/MyService.class
│   ├── com/example/MyServiceImpl.class
│   └── module-info.class
│
└── META-INF/versions/
    ├── 11/
    │   └── com/example/MyServiceImpl.class  (Java 11 optimized)
    │
    ├── 17/
    │   └── com/example/MyServiceImpl.class  (Java 17 optimized)
    │
    └── 21/
        └── com/example/MyServiceImpl.class  (Java 21 optimized)
```

### Cómo Funciona

Cuando ejecutas una aplicación con el JAR:

```
java -cp multi-release.jar com.example.App
     ↓
JVM detecta su versión (ej: Java 21)
     ↓
Busca en META-INF/versions/21/
     ↓
Si no existe, busca en META-INF/versions/17/
     ↓
Si no existe, busca en META-INF/versions/11/
     ↓
Si no existe, carga la versión base
```

---

## ✅ Requisitos

- Java 21 (o la versión que quieras usar como base)
- Maven 3.8+
- Un editor de texto o IDE

---

## 📁 Estructura del Proyecto

### Paso 0: Crear la estructura de directorios

```bash
mkdir -p my-multirelease-project
cd my-multirelease-project

# Crear estructura de directorios
mkdir -p src/main/java/com/example
mkdir -p src/main/java-11/com/example
mkdir -p src/main/java-17/com/example
mkdir -p src/main/java-21/com/example
```

**Resultado:**

```
my-multirelease-project/
├── src/main/
│   ├── java/           ← Base (Java 9+)
│   ├── java-11/        ← Versión Java 11
│   ├── java-17/        ← Versión Java 17
│   └── java-21/        ← Versión Java 21
├── target/             (se crea después)
└── pom.xml             (crearemos este)
```

---

## 📝 Paso 1: Crear la Estructura

### 1.1 Crear pom.xml

**Archivo**: `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>my-multirelease</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>Multi-Release JAR Example</name>
    <description>Ejemplo de Multi-Release JAR para Java 9, 11, 17, 21</description>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <build>
        <plugins>
            <!-- Compilador Maven -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <executions>
                    <!-- Compilación Base (Java 9) -->
                    <execution>
                        <id>base</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <source>9</source>
                            <target>9</target>
                            <compileSourceRoots>
                                <compileSourceRoot>${basedir}/src/main/java</compileSourceRoot>
                            </compileSourceRoots>
                            <outputDirectory>${project.build.outputDirectory}</outputDirectory>
                        </configuration>
                    </execution>

                    <!-- Compilación Java 11 -->
                    <execution>
                        <id>java-11</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <source>11</source>
                            <target>11</target>
                            <compileSourceRoots>
                                <compileSourceRoot>${basedir}/src/main/java-11</compileSourceRoot>
                            </compileSourceRoots>
                            <outputDirectory>${project.build.outputDirectory}/META-INF/versions/11</outputDirectory>
                            <skipMain>true</skipMain>
                        </configuration>
                    </execution>

                    <!-- Compilación Java 17 -->
                    <execution>
                        <id>java-17</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <source>17</source>
                            <target>17</target>
                            <compileSourceRoots>
                                <compileSourceRoot>${basedir}/src/main/java-17</compileSourceRoot>
                            </compileSourceRoots>
                            <outputDirectory>${project.build.outputDirectory}/META-INF/versions/17</outputDirectory>
                            <skipMain>true</skipMain>
                        </configuration>
                    </execution>

                    <!-- Compilación Java 21 -->
                    <execution>
                        <id>java-21</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <source>21</source>
                            <target>21</target>
                            <compileSourceRoots>
                                <compileSourceRoot>${basedir}/src/main/java-21</compileSourceRoot>
                            </compileSourceRoots>
                            <outputDirectory>${project.build.outputDirectory}/META-INF/versions/21</outputDirectory>
                            <skipMain>true</skipMain>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <!-- JAR Plugin con Multi-Release -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.4.1</version>
                <configuration>
                    <archive>
                        <manifestEntries>
                            <!-- 🔴 CRUCIAL: Esto marca el JAR como Multi-Release -->
                            <Multi-Release>true</Multi-Release>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

**Explicación:**
- `maven-compiler-plugin`: Compila cada versión por separado
- `maven-jar-plugin`: Empaqueta como JAR con `Multi-Release: true`
- Cada `<execution>`: Compila una versión de Java

---

## 🔧 Paso 2: Crear la Interfaz

Esta interfaz es **igual para todas las versiones**. Solo cambia la implementación.

**Archivo**: `src/main/java/com/example/DataProcessor.java`

```java
package com.example;

/**
 * Interfaz que define el contrato para procesar datos.
 * Esta interfaz es la MISMA para todas las versiones de Java.
 */
public interface DataProcessor {
    
    /**
     * Procesa una cadena de texto
     */
    String process(String input);
    
    /**
     * Ejecuta un benchmark
     */
    long benchmark();
    
    /**
     * Obtiene información de la implementación
     */
    String getInfo();
}
```

---

## 🚀 Paso 3: Implementaciones Específicas

### 3.1 Implementación Base (Java 9+)

**Archivo**: `src/main/java/com/example/DataProcessorImpl.java`

```java
package com.example;

/**
 * Implementación base para Java 9+
 * Compilada con -source 9 -target 9
 */
public class DataProcessorImpl implements DataProcessor {
    
    @Override
    public String process(String input) {
        // Java 9: Implementación simple
        return input.toUpperCase();
    }
    
    @Override
    public long benchmark() {
        long start = System.nanoTime();
        
        for (int i = 0; i < 1_000_000; i++) {
            process("benchmark");
        }
        
        return (System.nanoTime() - start) / 1_000_000;
    }
    
    @Override
    public String getInfo() {
        return "Java 9+ Implementation";
    }
}
```

### 3.2 Implementación Java 11

**Archivo**: `src/main/java-11/com/example/DataProcessorImpl.java`

```java
package com.example;

/**
 * Implementación mejorada para Java 11+
 * Compilada con -source 11 -target 11
 * 
 * Mejoras:
 * - Métodos de String nuevos (strip, isBlank)
 * - HTTP Client API
 */
public class DataProcessorImpl implements DataProcessor {
    
    @Override
    public String process(String input) {
        // Java 11: Usamos strip() (nuevo en Java 11)
        return input
            .strip()              // ← Nuevo en Java 11
            .toUpperCase();
    }
    
    @Override
    public long benchmark() {
        long start = System.nanoTime();
        
        // Java 11: Usamos Stream API para procesamiento
        java.util.stream.IntStream
            .range(0, 1_000_000)
            .forEach(i -> process("benchmark"));
        
        return (System.nanoTime() - start) / 1_000_000;
    }
    
    @Override
    public String getInfo() {
        return "Java 11 Implementation (con Stream API)";
    }
}
```

### 3.3 Implementación Java 17

**Archivo**: `src/main/java-17/com/example/DataProcessorImpl.java`

```java
package com.example;

/**
 * Implementación con Records para Java 17+
 * Compilada con -source 17 -target 17
 * 
 * Mejoras:
 * - Records (data classes)
 * - Pattern Matching en instanceof
 * - Sealed Classes
 */
public class DataProcessorImpl implements DataProcessor {
    
    // Record: Una forma elegante de definir clases de datos
    private record ProcessingResult(String value, long duration) {}
    
    @Override
    public String process(String input) {
        // Java 17: Pattern matching en instanceof
        if (input instanceof String s) {
            return s
                .strip()
                .toUpperCase();
        }
        return input.toUpperCase();
    }
    
    @Override
    public long benchmark() {
        long start = System.nanoTime();
        
        // Java 17: Parallel streams mejorados
        java.util.stream.IntStream
            .range(0, 1_000_000)
            .parallel()  // ← Mejor en Java 17
            .forEach(i -> process("benchmark"));
        
        return (System.nanoTime() - start) / 1_000_000;
    }
    
    @Override
    public String getInfo() {
        return "Java 17 Implementation (con Records y Pattern Matching)";
    }
}
```

### 3.4 Implementación Java 21

**Archivo**: `src/main/java-21/com/example/DataProcessorImpl.java`

```java
package com.example;

/**
 * Implementación con Virtual Threads para Java 21+
 * Compilada con -source 21 -target 21
 * 
 * Mejoras:
 * - Virtual Threads (Project Loom)
 * - Structured Concurrency
 * - Mejor rendimiento en concurrencia
 */
public class DataProcessorImpl implements DataProcessor {
    
    @Override
    public String process(String input) {
        return input
            .strip()
            .toUpperCase();
    }
    
    @Override
    public long benchmark() {
        long start = System.nanoTime();
        
        try {
            // Java 21: Virtual Threads para mejor concurrencia
            Thread vt = Thread.ofVirtual()
                .name("processor")
                .start(() -> {
                    java.util.stream.IntStream
                        .range(0, 1_000_000)
                        .forEach(i -> process("benchmark"));
                });
            
            vt.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return (System.nanoTime() - start) / 1_000_000;
    }
    
    @Override
    public String getInfo() {
        return "Java 21 Implementation (con Virtual Threads)";
    }
}
```

---

## 🏭 Paso 4: Factory Pattern

**Archivo**: `src/main/java/com/example/ProcessorFactory.java`

```java
package com.example;

/**
 * Factory que devuelve la implementación correcta
 * según la versión de Java en la que se ejecuta.
 */
public class ProcessorFactory {
    
    private static DataProcessor processor;
    
    static {
        try {
            // Detectar versión de Java
            String javaVersion = System.getProperty("java.version");
            int majorVersion = Integer.parseInt(javaVersion.split("\\.")[0]);
            
            System.out.println("Java Version: " + majorVersion);
            System.out.println("Loading optimized implementation...");
            
            // Crear instancia
            processor = new DataProcessorImpl();
            
            System.out.println("✅ Cargada: " + processor.getInfo());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public static DataProcessor getProcessor() {
        return processor;
    }
}
```

---

## 📦 Paso 5: Compilar Cada Versión

### Opción A: Con Maven

```bash
# Compilar todo (base + 11 + 17 + 21)
mvn clean compile

# Ver la estructura compilada
tree target/classes/
```

### Opción B: Manual con javac

```bash
# Base (Java 9)
mkdir -p target/classes
javac -d target/classes \
    -source 9 -target 9 \
    src/main/java/com/example/*.java

# Java 11
mkdir -p target/classes/META-INF/versions/11
javac -d target/classes/META-INF/versions/11 \
    -cp target/classes \
    -source 11 -target 11 \
    src/main/java-11/com/example/DataProcessorImpl.java

# Java 17
mkdir -p target/classes/META-INF/versions/17
javac -d target/classes/META-INF/versions/17 \
    -cp target/classes \
    -source 17 -target 17 \
    src/main/java-17/com/example/DataProcessorImpl.java

# Java 21
mkdir -p target/classes/META-INF/versions/21
javac -d target/classes/META-INF/versions/21 \
    -cp target/classes \
    -source 21 -target 21 \
    src/main/java-21/com/example/DataProcessorImpl.java
```

---

## 📄 Paso 6: Crear el Manifest

**Archivo**: `target/classes/META-INF/MANIFEST.MF`

```
Manifest-Version: 1.0
Automatic-Module-Name: com.example
Multi-Release: true
```

**Explicación:**
- `Multi-Release: true`: 🔴 **CRUCIAL** - Esto marca el JAR como multi-release
- `Automatic-Module-Name`: Nombre del módulo para Java 9+

---

## 🗂️ Paso 7: Empaquetar como JAR

### Con Maven

```bash
mvn package
```

### Manual con jar

```bash
cd target/classes
jar cfm ../my-multirelease.jar \
    META-INF/MANIFEST.MF \
    com/ META-INF/
```

---

## ✅ Paso 8: Verificar el JAR

```bash
# Ver contenido del JAR
jar tf target/my-multirelease.jar

# Verificar que tiene Multi-Release: true
jar xf target/my-multirelease.jar META-INF/MANIFEST.MF
cat META-INF/MANIFEST.MF

# Debe mostrar:
# Multi-Release: true
```

**Salida esperada:**

```
com/example/DataProcessor.class
com/example/DataProcessorImpl.class
com/example/ProcessorFactory.class
META-INF/versions/11/com/example/DataProcessorImpl.class
META-INF/versions/17/com/example/DataProcessorImpl.class
META-INF/versions/21/com/example/DataProcessorImpl.class
META-INF/MANIFEST.MF
```

---

## 🚀 Paso 9: Ejecutar en Diferentes Versiones

### Crear una Clase Main

**Archivo**: `src/main/java/com/example/Demo.java`

```java
package com.example;

public class Demo {
    
    public static void main(String[] args) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("Multi-Release JAR Demo");
        System.out.println("═".repeat(60) + "\n");
        
        DataProcessor processor = ProcessorFactory.getProcessor();
        
        System.out.println("📊 Información:");
        System.out.println("  " + processor.getInfo());
        System.out.println("  Java: " + System.getProperty("java.version"));
        
        System.out.println("\n⚡ Benchmark:");
        long time = processor.benchmark();
        System.out.println("  Tiempo: " + time + " ms");
        
        System.out.println("\n✨ Procesamiento:");
        String result = processor.process("  hello world  ");
        System.out.println("  Input: '  hello world  '");
        System.out.println("  Output: '" + result + "'");
        
        System.out.println("\n" + "═".repeat(60) + "\n");
    }
}
```

### Ejecutar

```bash
# En Java 21 (cargará la versión 21)
java -cp target/my-multirelease.jar com.example.Demo

# En Java 17 (cargará la versión 17)
java17 -cp target/my-multirelease.jar com.example.Demo

# En Java 11 (cargará la versión 11)
java11 -cp target/my-multirelease.jar com.example.Demo

# En Java 9 (cargará la versión base)
java9 -cp target/my-multirelease.jar com.example.Demo
```

---

## 📊 Resumen de Flujo

```
┌─────────────────────────────────────────────────────────────┐
│                    Multi-Release JAR Workflow               │
└─────────────────────────────────────────────────────────────┘

1. Crear estructura
   ├── src/main/java/         (Base Java 9+)
   ├── src/main/java-11/      (Java 11 specifics)
   ├── src/main/java-17/      (Java 17 specifics)
   └── src/main/java-21/      (Java 21 specifics)

2. Escribir código
   ├── Interfaz (same for all)
   ├── Implementación Java 9
   ├── Implementación Java 11
   ├── Implementación Java 17
   └── Implementación Java 21

3. Compilar
   ├── Base con -source 9 -target 9 → target/classes/
   ├── Java 11 con -source 11 -target 11 → META-INF/versions/11/
   ├── Java 17 con -source 17 -target 17 → META-INF/versions/17/
   └── Java 21 con -source 21 -target 21 → META-INF/versions/21/

4. Crear Manifest
   └── Multi-Release: true

5. Empaquetar
   └── jar cfm ... META-INF/MANIFEST.MF com/ META-INF/

6. Ejecutar
   └── java -cp jar com.example.Main
       → JVM detecta versión
       → Carga la mejor implementación
       → ✨ Funciona!
```

---

## 🎯 Ventajas

✅ **Un único JAR** para todas las versiones  
✅ **Optimizaciones específicas** para cada versión  
✅ **Retrocompatibilidad** automática  
✅ **Sin duplicación** de clases  
✅ **Fácil mantenimiento**  

---

## ⚠️ Limitaciones Importantes

❌ **Misma interfaz pública requerida**
- Puedes cambiar la implementación, pero no la interfaz

❌ **module-info.java debe ser igual**
- No puede haber versiones diferentes del descriptor de módulo

❌ **Estructura debe ser consistente**
- Todas las versiones necesitan los mismos paquetes

---

## 📚 Ejemplo Real Completo

En el proyecto `com.banking.multirelease` del repositorio:

- **Base (Java 9)**: Loop simple
- **Java 11**: Stream API
- **Java 17**: Records + Pattern Matching
- **Java 21**: Virtual Threads

**Ejecutar:**

```bash
cd modulo08/com.banking.multirelease
./build-multirelease.sh
java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar \
    com.banking.multirelease.MultiReleaseDemo
```

---

## 🔗 Referencias

- [JEP 238: Multi-Release JAR Files](https://openjdk.org/jeps/238)
- [Maven Compiler Plugin Multi-Release](https://maven.apache.org/plugins/maven-compiler-plugin/)
- [Creating Multi-Release JARs](https://docs.oracle.com/javase/tutorial/)

---

**Ahora ya sabes cómo crear Multi-Release JARs en Java 21! 🎉**

