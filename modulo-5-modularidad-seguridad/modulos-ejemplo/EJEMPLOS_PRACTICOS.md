# Ejemplos Prácticos de Módulos Java 21

## 1. Estructura del Proyecto

El proyecto está organizado como un módulo multi-módulo Maven:

```
modulo08/
├── com.banking.core/           # Módulo núcleo (interfaz pública)
│   ├── pom.xml
│   └── src/main/java/
│       ├── module-info.java
│       └── com/banking/core/
│           ├── account/BankAccount.java
│           └── exception/
├── com.banking.operations/      # Módulo de operaciones (encapsulado)
│   ├── pom.xml
│   └── src/main/java/
│       ├── module-info.java
│       └── com/banking/operations/
│           ├── account/SavingsAccount.java
│           └── factory/AccountFactory.java
├── com.banking.ui/              # Módulo de UI
│   ├── pom.xml
│   └── src/main/java/
│       ├── module-info.java
│       └── com/banking/ui/
│           └── console/BankingConsoleUI.java
├── com.banking.app/             # Módulo aplicación principal
│   ├── pom.xml
│   └── src/main/java/
│       ├── module-info.java
│       ├── com/banking/app/
│       │   ├── BankingApplication.java
│       │   ├── ModuleInspector.java
│       │   └── ModulesConceptsGuide.java
├── pom.xml                      # POM padre
└── README.md
```

## 2. Archivos module-info.java

### com.banking.core/src/main/java/module-info.java

```java
module com.banking.core {
    exports com.banking.core.account;
    exports com.banking.core.exception;
}
```

**Significado:**
- Este módulo exporta dos paquetes públicos
- Otros módulos pueden usar `BankAccount` y las excepciones
- Cualquier otro paquete interno está completamente encapsulado

### com.banking.operations/src/main/java/module-info.java

```java
module com.banking.operations {
    requires com.banking.core;
    
    exports com.banking.operations.factory;
    // NO exportamos com.banking.operations.account
    // La implementación (SavingsAccount) está privada
}
```

**Significado:**
- Este módulo requiere el módulo core
- Solo exporta la factory (punto de entrada público)
- La implementación concreta (SavingsAccount) está completamente encapsulada
- Imposible acceder directamente a SavingsAccount desde otros módulos

### com.banking.ui/src/main/java/module-info.java

```java
module com.banking.ui {
    requires com.banking.core;
    requires com.banking.operations;
    exports com.banking.ui.console;
}
```

**Significado:**
- Este módulo requiere core y operations
- Exporta la UI (BankingConsoleUI)
- Puede usar tanto la interfaz como la factory

### com.banking.app/src/main/java/module-info.java

```java
module com.banking.app {
    requires com.banking.core;
    requires com.banking.operations;
    requires com.banking.ui;
}
```

**Significado:**
- Aplicación principal que requiere todos los módulos
- No exporta nada (es la aplicación terminal)

## 3. Flujo de Dependencias

```
com.banking.app (aplicación)
  ├─ requires com.banking.core
  ├─ requires com.banking.operations
  │   └─ requires com.banking.core
  └─ requires com.banking.ui
      ├─ requires com.banking.core
      └─ requires com.banking.operations
```

## 4. Encapsulación en Acción

### ✅ PERMITIDO - Desde com.banking.app:

```java
// Estos paquetes son exportados, así que podemos usarlos
import com.banking.core.account.BankAccount;           // ✓ Exportado por core
import com.banking.operations.factory.AccountFactory;  // ✓ Exportado por operations
import com.banking.ui.console.BankingConsoleUI;        // ✓ Exportado por ui

BankAccount account = AccountFactory.createSavingsAccount(
    "ACC-001", "Juan", new BigDecimal("1000")
);
```

### ❌ NO PERMITIDO - Desde com.banking.app:

```java
// Este paquete NO es exportado por operations
import com.banking.operations.account.SavingsAccount;  // ✗ NO EXPORTADO
// Error en compilación:
// package com.banking.operations.account is not visible
// (package com.banking.operations.account is declared in module 
// com.banking.operations, which does not export it)
```

## 5. Cómo Ejecutar

### Compilar todo

```bash
cd /Users/josediaz/Projects/JoeDayz/java21-certification-samples/modulo08
mvn clean compile
```

### Ejecutar la aplicación bancaria

```bash
# Opción 1: Usando el script
./run-demo.sh app

# Opción 2: Usando Maven directamente
mvn -pl com.banking.app exec:java \
    -Dexec.mainClass="com.banking.app.BankingApplication"
```

### Inspeccionar módulos

```bash
# Opción 1: Usando el script
./run-demo.sh inspect

# Opción 2: Usando Maven directamente
mvn -pl com.banking.app exec:java \
    -Dexec.mainClass="com.banking.app.ModuleInspector"
```

### Empaquetar como JARs

```bash
mvn clean package -DskipTests
```

## 6. Conceptos Clave a Entender

### 🔹 Módulo (Module)

Un módulo es:
- Una unidad compilable y ejecutable
- Tiene un archivo `module-info.java`
- Define qué código es público (exporta) y qué es privado
- Define sus dependencias de otros módulos

### 🔹 Encapsulación (Encapsulation)

En Java 21 con módulos:
- Los paquetes NO son públicos por defecto
- Solo los paquetes en `exports` son accesibles desde otros módulos
- Imposible hacer reflection para acceder a clases no exportadas
- Incluso usando `setAccessible(true)`, falla si no está exportado

### 🔹 Dependencias (Requires)

Un módulo puede:
- Requerir otro módulo: `requires com.example.foo;`
- Requerir un módulo de forma transitiva: `requires transitive com.example.foo;`

Si A requiere transitivo B, entonces:
- C que requiere A, automáticamente tiene acceso a B
- Útil para módulos de agregación

### 🔹 Service Providers (SPI)

Los módulos también soportan:
- `uses`: especifica que este módulo usa un servicio
- `provides ... with ...`: especifica que implementa un servicio

No utilizado en este ejemplo, pero es importante en proyectos grandes.

## 7. Ventajas de esta Arquitectura

### Para los Alumnos

✓ **Claridad**: Ven exactamente qué depende de qué
✓ **Buenas prácticas**: Aprenden a crear APIs bien diseñadas
✓ **Encapsulación**: Entienden la diferencia entre interfaz e implementación
✓ **Modularidad**: Pueden agregar nuevos módulos fácilmente

### Para Proyectos Grandes

✓ **Escalabilidad**: Fácil agregar más módulos sin afectar existentes
✓ **Mantenibilidad**: Cambios internos no afectan a otros módulos
✓ **Seguridad**: Mejor control sobre qué código es visible
✓ **Optimización**: El JVM puede optimizar mejor con límites claros

## 8. Ejercicios Sugeridos para Alumnos

### Ejercicio 1: Agregar un nuevo módulo

Crea `com.banking.reports` que:
- Requiera `com.banking.core`
- Exporte `com.banking.reports.export`
- Implemente un generador de reportes

### Ejercicio 2: Modificar la encapsulación

Intenta acceder a `SavingsAccount` directamente:
- Verifica que el compilador lo rechaza
- Lee el mensaje de error
- Entiende por qué es una buena cosa

### Ejercicio 3: Agregar Service Provider

Implementa un servicio que:
- Define una interfaz en `com.banking.core`
- Implementa múltiples proveedores en diferentes módulos
- Los carga dinámicamente usando `ServiceLoader`

## 9. Referencias

- [Project Jigsaw](https://openjdk.org/projects/jigsaw/)
- [Module System Quick-Start](https://openjdk.org/projects/jigsaw/quick-start)
- [Java Modules API Documentation](https://docs.oracle.com/javase/tutorial/java/javaOO/modules.html)
- [State of the Module System](https://cr.openjdk.org/~mr/jigsaw/state-of-modules/)

## 10. Preguntas Frecuentes

**P: ¿Por qué necesitamos módulos si ya tenemos paquetes?**

R: Los paquetes ofrecen encapsulación dentro de un JAR, pero no entre JARs. Los módulos ofrecen encapsulación a nivel de aplicación completa.

**P: ¿Mi proyecto heredado funcionará con módulos?**

R: Sí. Java soporta el "classpath" tradicional. Los módulos son opcionales. Pero si usas módulos, deben funcionar correctamente.

**P: ¿Cómo evito dependencias circulares?**

R: El compilador las detecta automáticamente. Si A requiere B y B requiere A, obtendrás un error.

**P: ¿Puedo usar reflection para acceder a clases no exportadas?**

R: No directamente en Java 21. El módulo system lo previene, incluso con `setAccessible(true)`.

