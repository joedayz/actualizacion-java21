# 🎓 Ejercicios Prácticos: Multi-Release JARs

## Ejercicio 1: Tu Primer Multi-Release JAR

### Objetivo
Crear un Multi-Release JAR simple que detecta la versión de Java y muestra información.

### Pasos

**1. Crear la estructura**
```bash
mkdir -p ejercicio1
cd ejercicio1
mkdir -p src/main/java/com/mycompany
mkdir -p src/main/java-11/com/mycompany
mkdir -p src/main/java-17/com/mycompany
mkdir -p src/main/java-21/com/mycompany
```

**2. Crear la interfaz** (igual en todas las versiones)

`src/main/java/com/mycompany/Calculator.java`:
```java
package com.mycompany;

public interface Calculator {
    int add(int a, int b);
    int multiply(int a, int b);
    String getImplementation();
}
```

**3. Implementación Base (Java 9)**

`src/main/java/com/mycompany/SimpleCalculator.java`:
```java
package com.mycompany;

public class SimpleCalculator implements Calculator {
    
    @Override
    public int add(int a, int b) {
        return a + b;
    }
    
    @Override
    public int multiply(int a, int b) {
        return a * b;
    }
    
    @Override
    public String getImplementation() {
        return "Base Implementation (Java 9+)";
    }
}
```

**4. Implementación Java 11** (más eficiente)

`src/main/java-11/com/mycompany/SimpleCalculator.java`:
```java
package com.mycompany;

public class SimpleCalculator implements Calculator {
    
    @Override
    public int add(int a, int b) {
        // Java 11: Podríamos usar var si fuera necesario
        int result = a + b;
        return result;
    }
    
    @Override
    public int multiply(int a, int b) {
        return Math.multiplyExact(a, b); // ← Mejor validación
    }
    
    @Override
    public String getImplementation() {
        return "Java 11 Implementation (con Math utilities)";
    }
}
```

**5. Compilar**
```bash
# Java 9 (base)
mkdir -p target/classes
javac -d target/classes -source 9 -target 9 \
    src/main/java/com/mycompany/*.java

# Java 11
mkdir -p target/classes/META-INF/versions/11
javac -d target/classes/META-INF/versions/11 \
    -cp target/classes -source 11 -target 11 \
    src/main/java-11/com/mycompany/SimpleCalculator.java
```

**6. Crear MANIFEST**
```bash
mkdir -p target/classes/META-INF
cat > target/classes/META-INF/MANIFEST.MF << 'EOF'
Manifest-Version: 1.0
Multi-Release: true
EOF
```

**7. Crear el JAR**
```bash
cd target/classes
jar cfm ../ejercicio1.jar META-INF/MANIFEST.MF com/ META-INF/
cd ..
```

**8. Verificar**
```bash
# Ver contenido
jar tf ejercicio1.jar | grep -E "class|versions"

# Debería mostrar:
# com/mycompany/Calculator.class
# com/mycompany/SimpleCalculator.class
# META-INF/versions/11/com/mycompany/SimpleCalculator.class
```

**9. Crear una clase Main para probar**

`src/main/java/com/mycompany/Demo.java`:
```java
package com.mycompany;

public class Demo {
    public static void main(String[] args) {
        Calculator calc = new SimpleCalculator();
        
        System.out.println("Implementación: " + calc.getImplementation());
        System.out.println("2 + 3 = " + calc.add(2, 3));
        System.out.println("4 * 5 = " + calc.multiply(4, 5));
    }
}
```

**10. Ejecutar**
```bash
javac -cp target/ejercicio1.jar -d target/classes \
    src/main/java/com/mycompany/Demo.java

java -cp target/classes:target/ejercicio1.jar com.mycompany.Demo
```

✅ **Resultado esperado**: Debe mostrar la versión correcta según tu Java

---

## Ejercicio 2: Optimizar para Cada Versión

### Objetivo
Mejorar el rendimiento de una función en cada versión de Java.

### Código Base

Crear una clase que procesa colecciones de números:

```java
package com.mycompany;

import java.util.List;
import java.util.ArrayList;

public interface CollectionProcessor {
    List<Integer> processNumbers(int[] numbers);
}
```

### Implementación Base (Java 9)

```java
// src/main/java/com/mycompany/SimpleProcessor.java
public class SimpleProcessor implements CollectionProcessor {
    
    @Override
    public List<Integer> processNumbers(int[] numbers) {
        List<Integer> result = new ArrayList<>();
        
        for (int num : numbers) {
            if (num % 2 == 0) {  // Números pares
                result.add(num * 2);
            }
        }
        
        return result;
    }
}
```

### Implementación Java 11

```java
// src/main/java-11/com/mycompany/SimpleProcessor.java
public class SimpleProcessor implements CollectionProcessor {
    
    @Override
    public List<Integer> processNumbers(int[] numbers) {
        // Java 11: Usamos var
        var result = java.util.stream.IntStream
            .of(numbers)
            .filter(num -> num % 2 == 0)
            .map(num -> num * 2)
            .boxed()
            .toList();  // toList() disponible en Java 16+
        
        return new java.util.ArrayList<>(result);
    }
}
```

### Implementación Java 17

```java
// src/main/java-17/com/mycompany/SimpleProcessor.java
public class SimpleProcessor implements CollectionProcessor {
    
    private record NumberResult(java.util.List<Integer> data) {}
    
    @Override
    public List<Integer> processNumbers(int[] numbers) {
        // Java 17: Pattern matching y records
        var result = java.util.stream.IntStream
            .of(numbers)
            .parallel()  // Mejor en Java 17
            .filter(num -> num % 2 == 0)
            .map(num -> num * 2)
            .boxed()
            .toList();
        
        return new java.util.ArrayList<>(result);
    }
}
```

### Implementación Java 21

```java
// src/main/java-21/com/mycompany/SimpleProcessor.java
public class SimpleProcessor implements CollectionProcessor {
    
    @Override
    public List<Integer> processNumbers(int[] numbers) {
        // Java 21: Virtual Threads para procesamiento
        var result = java.util.stream.IntStream
            .of(numbers)
            .parallel()
            .filter(num -> num % 2 == 0)
            .map(num -> num * 2)
            .boxed()
            .toList();
        
        return new java.util.ArrayList<>(result);
    }
}
```

### Prueba

```java
public class ProcessorDemo {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        CollectionProcessor processor = new SimpleProcessor();
        System.out.println("Números pares * 2: " + processor.processNumbers(numbers));
    }
}
```

---

## Ejercicio 3: Multi-Release con Módulos

### Objetivo
Combinar Multi-Release JAR con Java 21 Modules.

### Crear el module-info.java

`src/main/java/module-info.java`:
```java
module com.mycompany {
    exports com.mycompany;
}
```

**Importante**: Este archivo debe ser idéntico en todas las versiones.

### Compilar como módulo

```bash
# Base
javac -d target/classes \
    --module-version 1.0 \
    -source 9 -target 9 \
    src/main/java/module-info.java \
    src/main/java/com/mycompany/*.java

# Java 11
javac -d target/classes/META-INF/versions/11 \
    -cp target/classes \
    -source 11 -target 11 \
    src/main/java-11/com/mycompany/*.java
```

### Verificar

```bash
jar --describe-module --file target/ejercicio3.jar
```

---

## Ejercicio 4: Crear un Multi-Release Benchmark

### Objetivo
Medir el rendimiento de cada implementación.

### Código

```java
package com.mycompany;

import java.util.*;

public class BenchmarkComparison {
    
    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  Multi-Release JAR Benchmark          ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        runBenchmark();
    }
    
    private static void runBenchmark() {
        CollectionProcessor processor = new SimpleProcessor();
        
        // Generar datos de prueba
        int[] largeArray = new int[1_000_000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Procesando 1,000,000 números...\n");
        
        // Warmup
        for (int i = 0; i < 5; i++) {
            processor.processNumbers(largeArray);
        }
        
        // Benchmark real
        long start = System.nanoTime();
        List<Integer> result = processor.processNumbers(largeArray);
        long end = System.nanoTime();
        
        long timeMs = (end - start) / 1_000_000;
        
        System.out.println("Resultados: " + result.size() + " números pares");
        System.out.println("Tiempo: " + timeMs + " ms");
        System.out.println("Velocidad: " + (1_000_000.0 / timeMs) + " ops/ms\n");
    }
}
```

---

## 🎯 Checklist de Ejercicios

### Ejercicio 1: Tu Primer Multi-Release JAR
- [ ] Creaste la estructura de directorios
- [ ] Compilaste base + Java 11
- [ ] Creaste el MANIFEST.MF con Multi-Release: true
- [ ] Empaquetaste como JAR
- [ ] Ejecutaste y viste la salida correcta

### Ejercicio 2: Optimizar por Versión
- [ ] Implementaste CollectionProcessor para 4 versiones
- [ ] Cada versión usa características modernas
- [ ] Las versiones más nuevas son más eficientes
- [ ] Compilaste todas las versiones
- [ ] Verificaste que se carga la correcta

### Ejercicio 3: Multi-Release con Módulos
- [ ] Combinaste Multi-Release con module-info.java
- [ ] El módulo se declara correctamente
- [ ] Funciona con Java 21 modules

### Ejercicio 4: Benchmark
- [ ] Creaste un programa de benchmark
- [ ] Mediste rendimiento en cada versión
- [ ] Observaste diferencias de velocidad
- [ ] Documentaste los resultados

---

## 📊 Comparación de Características

| Versión | Características | Ventajas |
|---------|-----------------|----------|
| Java 9  | Base           | Retrocompatibilidad |
| Java 11 | Stream API    | Procesamiento eficiente |
| Java 17 | Records       | Menos código, más rendimiento |
| Java 21 | Virtual Threads| Concurrencia mejorada |

---

## 🚀 Próximos Pasos

Después de estos ejercicios, puedes:

1. **Combinar con Hibernate/JPA** - Crear versiones específicas
2. **Usar en APIs REST** - Endpoints optimizados por versión
3. **Testing Multi-Release** - Tests específicos por versión
4. **Distribución** - Publicar en Maven Central

---

## 💡 Tips y Trucos

### Compilación Rápida
```bash
# Usar alias
alias compile-base='javac -d target/classes -source 9 -target 9'
alias compile-21='javac -d target/classes/META-INF/versions/21 -source 21 -target 21'

compile-base src/main/java/com/mycompany/*.java
compile-21 src/main/java-21/com/mycompany/*.java
```

### Debugging
```bash
# Ver qué se carga
java -verbose:class -cp jar com.example.Main | grep mycompany

# Debug en módulos
jar --verbose --extract --file myjar.jar | less
```

### Validación
```bash
# Verificar estructura
jar tf myjar.jar | sort

# Contar clases por versión
echo "Base:"
jar tf myjar.jar | grep -E "^com/" | wc -l
echo "Java 21:"
jar tf myjar.jar | grep "versions/21" | wc -l
```

---

**¡Ahora estás listo para dominar Multi-Release JARs!** 🎉

