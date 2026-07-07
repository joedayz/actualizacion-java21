# 🎓 Guía de Enseñanza: Módulos Java 21

## Objetivos Educativos

Los estudiantes aprenderán:

1. **Concepto de Módulos en Java**
   - Qué son y por qué son importantes
   - Diferencia entre paquetes y módulos
   - El archivo `module-info.java`

2. **Encapsulación a Nivel de Aplicación**
   - Exportar solo lo necesario
   - Mantener implementaciones privadas
   - Ventajas de la encapsulación fuerte

3. **Gestión de Dependencias Explícitas**
   - Declarar dependencias claras
   - Detectar dependencias circulares
   - Validación en tiempo de compilación

4. **Arquitectura Modular**
   - Diseñar módulos cohesivos
   - Separar interfaz de implementación
   - Crear un sistema escalable

## Estructura del Proyecto de Demostración

### Módulos

```
Nivel 0 (Base):
├─ java.base (módulo estándar de Java)
└─ java.lang (siempre transitivo)

Nivel 1 (Núcleo):
└─ com.banking.core
   (Define interfaces y excepciones)

Nivel 2 (Operaciones):
├─ com.banking.operations
│  (Implementa com.banking.core)
└─ com.banking.ui
   (Utiliza com.banking.core)

Nivel 3 (Aplicación):
└─ com.banking.app
   (Integra todos los módulos)
```

### Puntos Clave de Cada Módulo

#### 🔹 com.banking.core

**Responsabilidad**: Define el contrato

```java
// module-info.java
module com.banking.core {
    exports com.banking.core.account;
    exports com.banking.core.exception;
}
```

**Lo que contiene**:
- `BankAccount` - Interfaz pública
- `InsufficientFundsException` - Excepción pública
- `InvalidAccountOperationException` - Excepción pública

**Lo que exporta**: Todo (es la interfaz pública)

**Lo que requiere**: Nada (módulo independiente)

#### 🔹 com.banking.operations

**Responsabilidad**: Implementa el contrato

```java
// module-info.java
module com.banking.operations {
    requires com.banking.core;
    exports com.banking.operations.factory;
}
```

**Lo que contiene**:
- `SavingsAccount` - Implementación **PRIVADA** de BankAccount
- `AccountFactory` - Factory **PÚBLICA** para crear cuentas

**Lo que exporta**: Solo la factory (punto de entrada controlado)

**Lo que requiere**: core

**Lesson**: La implementación está completamente encapsulada. Los clientes no pueden instanciar directamente SavingsAccount.

#### 🔹 com.banking.ui

**Responsabilidad**: Proporciona interfaz de usuario

```java
// module-info.java
module com.banking.ui {
    requires com.banking.core;
    requires com.banking.operations;
    exports com.banking.ui.console;
}
```

**Lo que contiene**:
- `BankingConsoleUI` - Interfaz de consola

**Lo que exporta**: UI Console

**Lo que requiere**: core y operations

#### 🔹 com.banking.app

**Responsabilidad**: Integra todo

```java
// module-info.java
module com.banking.app {
    requires com.banking.core;
    requires com.banking.operations;
    requires com.banking.ui;
}
```

**Lo que no exporta**: Nada (es una aplicación)

**Lo que requiere**: Todos los módulos

## Cómo Usar en Clase

### Sesión 1: Introducción a Módulos (30 minutos)

1. **Explicar el problema** (5 min)
   - Sin módulos: Cualquier código puede acceder a cualquier clase pública
   - Problema: Imposible mantener arquitectura clara en proyectos grandes
   
2. **Mostrar la solución** (10 min)
   - Módulos permiten encapsulación a nivel de aplicación
   - Solo los paquetes en `exports` son visibles
   - Dependencias son explícitas y validadas
   
3. **Mostrar el código** (10 min)
   - Abrir cada `module-info.java`
   - Explicar cada declaración
   
4. **Demo en vivo** (5 min)
   - Ejecutar compilación
   - Mostrar que compila sin errores

### Sesión 2: Encapsulación en Acción (40 minutos)

1. **Experimento 1: Acceso permitido** (10 min)
   ```bash
   # Compilar y ejecutar
   ./run-demo.sh app
   ```
   Mostrar que puedo crear cuentas usando la factory
   
2. **Experimento 2: Intentar acceso prohibido** (15 min)
   
   Editar `BankingApplication.java` y agregar:
   ```java
   // Esto FALLARÁ
   import com.banking.operations.account.SavingsAccount;
   SavingsAccount account = new SavingsAccount("123", "John", BigDecimal.TEN);
   ```
   
   Mostrar el error:
   ```
   package com.banking.operations.account is not visible
   (package com.banking.operations.account is declared in module 
   com.banking.operations, which does not export it)
   ```
   
   **Lección**: La encapsulación es FUERTE. No puedo acceder incluso siendo en el mismo proyecto.

3. **Explicar por qué es bueno** (10 min)
   - Imposible depender de detalles de implementación
   - Los desarrolladores del módulo pueden cambiar la implementación sin romper el código cliente
   - Arquitectura explícita

4. **Mostrar el graph de dependencias** (5 min)
   ```bash
   ./run-demo.sh inspect
   ```

### Sesión 3: Ejercicios Prácticos (60 minutos)

#### Ejercicio 1: Crear un nuevo módulo (20 min)

Pedir a los estudiantes que creen `com.banking.reports`:

```
com.banking.reports/
├── pom.xml
└── src/main/java/
    ├── module-info.java
    └── com/banking/reports/
        ├── ReportGenerator.java
        └── models/
            └── ReportData.java
```

**module-info.java** debe ser:
```java
module com.banking.reports {
    requires com.banking.core;
    exports com.banking.reports;
    // No exportamos models (es interno)
}
```

**Lesson**: Practicar decisiones de qué exportar y qué encapsular

#### Ejercicio 2: Agregar dependencia circular (10 min)

Hacer que `com.banking.core` requiera `com.banking.reports`:

```java
// com.banking.core/module-info.java
module com.banking.core {
    requires com.banking.reports;  // ← Esto causa error!
    exports com.banking.core.account;
}
```

Intentar compilar:
```bash
mvn compile
```

**Error esperado**:
```
module com.banking.reports reads package com.banking.core 
from source file com/banking/reports/ReportGenerator.java
and source files are compiled incrementally
```

**Lesson**: El sistema de módulos detecta automáticamente dependencias circulares

#### Ejercicio 3: Usar Reflection sobre módulos (15 min)

Crear `ModuleAnalyzer.java`:

```java
public class ModuleAnalyzer {
    public static void main(String[] args) {
        Module module = BankAccount.class.getModule();
        
        System.out.println("Módulo: " + module.getName());
        System.out.println("Exporta:");
        
        module.getDescriptor().exports().forEach(exp -> {
            System.out.println("  - " + exp.source());
        });
        
        System.out.println("Requiere:");
        module.getDescriptor().requires().forEach(req -> {
            System.out.println("  - " + req.name());
        });
    }
}
```

**Lesson**: Podemos inspeccionar módulos en runtime

#### Ejercicio 4: Crear un Service Provider (15 min)

Crear un sistema de plugins usando servicios:

1. Definir un servicio en `com.banking.core`:
   ```java
   public interface BankAccountValidator {
       boolean validate(BankAccount account);
   }
   ```

2. En `module-info.java`:
   ```java
   module com.banking.core {
       // ...
       exports com.banking.core.spi;
       uses com.banking.core.spi.BankAccountValidator;
   }
   ```

3. Implementar en `com.banking.operations`:
   ```java
   public class DefaultValidator implements BankAccountValidator {
       @Override
       public boolean validate(BankAccount account) {
           return account.getBalance().signum() >= 0;
       }
   }
   ```

4. En `module-info.java` de operations:
   ```java
   module com.banking.operations {
       // ...
       requires com.banking.core;
       provides com.banking.core.spi.BankAccountValidator 
       with com.banking.operations.validate.DefaultValidator;
   }
   ```

**Lesson**: Los módulos soportan el patrón Service Provider Interface

## Puntos para Enfatizar

### ✅ VENTAJAS (Por qué los módulos son buenos)

1. **Encapsulación Real**
   - No es solo convención, es ejecución
   - Imposible acceder a código privado

2. **Dependencias Explícitas**
   - Ver exactamente qué módulo depende de qué
   - Detección automática de problemas

3. **Arquitectura Clara**
   - La estructura del proyecto es obvia
   - Nuevos miembros del equipo la entienden rápido

4. **Escalabilidad**
   - Fácil agregar nuevos módulos
   - Cambios en un módulo no afectan otros

5. **Seguridad**
   - Control fino sobre qué es público
   - Evita acceso accidental a APIs internas

### ❌ DESAFÍOS (Cosas que los estudiantes pueden encontrar difíciles)

1. **Curva de Aprendizaje**
   - La sintaxis de `module-info.java` es nueva
   - Diferentes mentalidad que paquetes

2. **Debugging**
   - Los errores de módulos pueden ser confusos
   - Errors de "package not visible" requieren entender el sistema

3. **Herramientas IDE**
   - IDEs más viejos pueden no soportar bien módulos
   - A veces el IDE necesita refresh

4. **Compatibilidad Hacia Atrás**
   - Proyectos viejos no usan módulos
   - Mezclar código con y sin módulos es complejo

## Respuestas a Preguntas Frecuentes

**P: ¿Por qué necesitamos `exports` si ya tenemos `public`?**

R: `public` significa visible dentro del classpath. `exports` significa visible desde otros módulos. En un mundo sin módulos, todo `public` es accesible globalmente. Con módulos, podemos ser más restrictivos.

**P: ¿Qué pasa si olvido un `requires`?**

R: El compilador te dará un error indicando que no puede encontrar la clase. Esto es bueno: te obliga a declarar dependencias.

**P: ¿Puedo tener múltiples módulos en un proyecto Maven?**

R: Sí, eso es un proyecto multi-módulo Maven. Cada módulo es un directorio con su propio `pom.xml`.

**P: ¿Los módulos afectan el rendimiento?**

R: Mínimamente. El costo es pequeño comparado a los beneficios de arquitectura.

**P: ¿Debo usar módulos en nuevo proyecto?**

R: Sí, si usas Java 9+. Módulos son la forma estándar de organizar código en Java moderno.

## Recursos para Estudiantes

### Documentación Oficial
- https://docs.oracle.com/javase/tutorial/java/javaOO/modules.html
- https://openjdk.org/projects/jigsaw/

### Artículos Recomendados
- "The State of the Module System" (OpenJDK blog)
- "Understanding Java Modules" (Baeldung)

### Ejemplos
- Este proyecto: com.banking.*
- Módulos en JDK: java.base, java.lang, java.util, etc.

## Extensiones Posibles

1. **Agregar Persistencia**
   - Nuevo módulo `com.banking.persistence`
   - Implementar acceso a base de datos

2. **Agregar Web API**
   - Nuevo módulo `com.banking.api`
   - Exponer endpoints REST

3. **Agregar Tests Modulares**
   - Módulo `com.banking.core.test`
   - Tests especiales para cada módulo

4. **Agregar Documentación**
   - Generar Javadoc por módulo
   - Crear diagramas de dependencias

## Conclusión

El sistema de módulos es una herramienta poderosa para construir aplicaciones Java escalables y mantenibles. Este proyecto demuestra:

- ✅ Cómo diseñar módulos cohesivos
- ✅ Cómo encapsular implementaciones
- ✅ Cómo gestionar dependencias explícitamente
- ✅ Cómo construir arquitecturas limpias

Los estudiantes que dominen los módulos estarán mejor preparados para trabajar en proyectos grandes del mundo real.

