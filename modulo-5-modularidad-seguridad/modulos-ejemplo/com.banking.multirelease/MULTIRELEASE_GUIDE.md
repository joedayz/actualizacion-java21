# Multi-Release Module Archives - Guía Paso a Paso

## 📚 ¿Qué son los Multi-Release JARs?

Los **Multi-Release JARs** (o Multi-Release Module Archives) son archivos JAR que contienen versiones específicas de clases compiladas para diferentes versiones de Java. El JVM automáticamente carga la versión más apropiada según la versión de Java en la que se ejecute.

### Estructura

```
my-library.jar
├── META-INF/
│   ├── MANIFEST.MF              ← Multi-Release: true
│   └── versions/
│       ├── 11/
│       │   └── com/example/MyClass.class   (Java 11 version)
│       ├── 17/
│       │   └── com/example/MyClass.class   (Java 17 version)
│       └── 21/
│           └── com/example/MyClass.class   (Java 21 version)
├── com/example/
│   └── MyClass.class            (Base - Java 9+)
└── module-info.class
```

---

## 🎯 Caso de Uso

Imagina que tienes una librería que necesita soportar:
- **Java 9**: Usuarios con versiones antiguas
- **Java 11**: La mayoría de empresas usan esta versión LTS
- **Java 17**: Usuarios con Java 17 LTS
- **Java 21**: Usuarios con la última versión

Cada versión tiene características nuevas que pueden mejorar el rendimiento o la funcionalidad.

---

## 📋 Pasos para Crear un Multi-Release JAR

### Paso 1: Estructura de Directorios

```bash
my-project/
├── src/main/java/                  ← Base (Java 9+)
│   ├── module-info.java
│   └── com/example/
│       └── MyClass.java
├── src/main/java-11/               ← Código específico Java 11
│   └── com/example/
│       └── MyClass.java
├── src/main/java-17/               ← Código específico Java 17
│   └── com/example/
│       └── MyClass.java
├── src/main/java-21/               ← Código específico Java 21
│   └── com/example/
│       └── MyClass.java
└── pom.xml
```

**Nota**: Solo creas archivos en los directorios donde diferencia existe. Si Java 11 usa el mismo código que Java 9, no creas `java-11/`.

---

### Paso 2: Crear la Interfaz (igual para todas las versiones)

**Ubicación**: `src/main/java/com/example/MyService.java`

```java
public interface MyService {
    String process(String input);
    long benchmark();
}
```

---

### Paso 3: Implementación Base (Java 9+)

**Ubicación**: `src/main/java/com/example/MyServiceImpl.java`

```java
public class MyServiceImpl implements MyService {
    @Override
    public String process(String input) {
        // Implementación simple
        return input.toUpperCase();
    }
    
    @Override
    public long benchmark() {
        // Benchmark básico
        long start = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            process("test");
        }
        return (System.nanoTime() - start) / 1_000_000;
    }
}
```

---

### Paso 4: Implementación Java 11

**Ubicación**: `src/main/java-11/com/example/MyServiceImpl.java`

```java
public class MyServiceImpl implements MyService {
    @Override
    public String process(String input) {
        // Java 11: usamos nuevos métodos de String
        return input
            .strip()                    // ← Nuevo en Java 11
            .toUpperCase();
    }
    
    @Override
    public long benchmark() {
        // Java 11: usamos Stream API
        long start = System.nanoTime();
        java.util.stream.IntStream
            .range(0, 1_000_000)
            .forEach(i -> process("test"));
        return (System.nanoTime() - start) / 1_000_000;
    }
}
```

---

### Paso 5: Implementación Java 17

**Ubicación**: `src/main/java-17/com/example/MyServiceImpl.java`

```java
public class MyServiceImpl implements MyService {
    // Records disponible en Java 17
    private record Result(String value, long time) {}
    
    @Override
    public String process(String input) {
        // Java 17: Pattern matching en switch
        return switch (input.length()) {
            case 0 -> "";
            case 1, 2, 3 -> input.toUpperCase();
            default -> input.strip().toUpperCase();
        };
    }
    
    @Override
    public long benchmark() {
        // Java 17: Parallel streams con mejor rendimiento
        long start = System.nanoTime();
        java.util.stream.IntStream
            .range(0, 1_000_000)
            .parallel()  // ← Mejor en Java 17
            .forEach(i -> process("test"));
        return (System.nanoTime() - start) / 1_000_000;
    }
}
```

---

### Paso 6: Implementación Java 21

**Ubicación**: `src/main/java-21/com/example/MyServiceImpl.java`

```java
public class MyServiceImpl implements MyService {
    @Override
    public String process(String input) {
        // Java 21: Virtual Threads para mejor concurrencia
        return input.strip().toUpperCase();
    }
    
    @Override
    public long benchmark() {
        // Java 21: Usar Virtual Threads
        long start = System.nanoTime();
        
        Thread vt = Thread.ofVirtual()
            .start(() -> {
                java.util.stream.IntStream
                    .range(0, 1_000_000)
                    .forEach(i -> process("test"));
            });
        
        try {
            vt.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return (System.nanoTime() - start) / 1_000_000;
    }
}
```

---

### Paso 7: Configurar Maven (pom.xml)

**Parte clave**: Compilar cada versión por separado

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <executions>
        <!-- Base (Java 9+) -->
        <execution>
            <id>base</id>
            <configuration>
                <source>9</source>
                <target>9</target>
                <compileSourceRoots>
                    <compileSourceRoot>${basedir}/src/main/java</compileSourceRoot>
                </compileSourceRoots>
                <outputDirectory>${project.build.outputDirectory}</outputDirectory>
            </configuration>
            <goals><goal>compile</goal></goals>
        </execution>
        
        <!-- Java 11 -->
        <execution>
            <id>java-11</id>
            <configuration>
                <source>11</source>
                <target>11</target>
                <compileSourceRoots>
                    <compileSourceRoot>${basedir}/src/main/java-11</compileSourceRoot>
                </compileSourceRoots>
                <!-- IMPORTANTE: Output en META-INF/versions/11 -->
                <outputDirectory>${project.build.outputDirectory}/META-INF/versions/11</outputDirectory>
            </configuration>
            <goals><goal>compile</goal></goals>
        </execution>
        
        <!-- Repetir para 17, 21, etc. -->
    </executions>
</plugin>

<!-- JAR con Multi-Release -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <configuration>
        <archive>
            <manifestEntries>
                <Multi-Release>true</Multi-Release>  <!-- ← CRUCIAL -->
            </manifestEntries>
        </archive>
    </configuration>
</plugin>
```

---

### Paso 8: Compilar y Empaquetar

```bash
cd com.banking.multirelease
mvn clean package
```

---

### Paso 9: Verificar el JAR

```bash
# Ver contenido del JAR
jar tf target/com.banking.multirelease-1.0-SNAPSHOT.jar

# Debe mostrar:
# META-INF/MANIFEST.MF
# META-INF/versions/11/com/example/MyServiceImpl.class
# META-INF/versions/17/com/example/MyServiceImpl.class
# META-INF/versions/21/com/example/MyServiceImpl.class
# com/example/MyServiceImpl.class
# com/example/MyService.class

# Ver MANIFEST
jar xf target/com.banking.multirelease-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF
cat META-INF/MANIFEST.MF

# Debe contener:
# Multi-Release: true
```

---

### Paso 10: Usar el JAR

```bash
# En Java 9, 10
java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar com.example.Main
# Carga: com/example/MyServiceImpl.class (base)

# En Java 11, 12-16
java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar com.example.Main
# Carga: META-INF/versions/11/com/example/MyServiceImpl.class

# En Java 17, 18-20
java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar com.example.Main
# Carga: META-INF/versions/17/com/example/MyServiceImpl.class

# En Java 21+
java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar com.example.Main
# Carga: META-INF/versions/21/com/example/MyServiceImpl.class
```

---

## 🎯 Ventajas

✅ **Un solo JAR** para todas las versiones de Java  
✅ **Optimizaciones específicas** para cada versión  
✅ **Retrocompatibilidad** automática  
✅ **Performance mejorado** en versiones nuevas  
✅ **Sin duplicación** de archivos (solo lo diferente)  

---

## ⚠️ Limitaciones y Consideraciones

### 1. Misma interfaz pública
Las clases en diferentes versiones **deben tener la misma interfaz pública**. Solo la implementación cambia.

```java
// ✅ PERMITIDO: Mismo método, implementación diferente
// En java-9:
public String process(String input) {
    return input.toUpperCase();
}

// En java-21:
public String process(String input) {
    return input.strip().toUpperCase();
}

// ❌ NO PERMITIDO: Método diferente
// En java-21 agregar un método que no existe en java-9
public String processOptimized(String input) { ... }
```

### 2. Versión base es fallback
Si no existe una versión para la Java actual, se usa la base más cercana inferior.

```
Java 12 con Multi-Release: [9, 11, 17, 21]
└─ Carga la versión 11 (mayor menor que 12)
```

### 3. module-info.java
Debe ser idéntico en todas las versiones (por ahora).

---

## 📊 Ejemplo Real

En el proyecto actual `com.banking.multirelease`:

- **Java 9**: Implementación base con loops simples
- **Java 11**: Usa Stream API
- **Java 17**: Usa Pattern Matching y Records
- **Java 21**: Usa Virtual Threads

Cada versión es más optimizada que la anterior.

---

## 🚀 Cómo Usar el Ejemplo

```bash
# 1. Ir al directorio
cd modulo08/com.banking.multirelease

# 2. Compilar
mvn clean package

# 3. Ejecutar con Java 21
java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar \
    com.banking.multirelease.MultiReleaseDemo

# Output: Mostrará la implementación Java 21
```

---

## 📚 Referencias

- [Multi-Release JARs - OpenJDK](https://openjdk.org/jeps/238)
- [Creating Multi-Release JARs - Oracle Docs](https://docs.oracle.com/javase/tutorial/)
- [Maven Compiler Plugin - Multi-Release](https://maven.apache.org/plugins/maven-compiler-plugin/)


