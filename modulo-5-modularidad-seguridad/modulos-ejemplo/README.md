# Modulo 08 - Java Modules (Project Jigsaw)

Proyecto educativo que demuestra el uso de módulos en Java 21, conocido como Project Jigsaw.

Este es un sistema bancario modular que implementa arquitectura de módulos con encapsulación fuerte, dependencias explícitas y separación clara de responsabilidades.

## 📚 Documentación Incluida

1. **README.md** (este archivo) - Información general del proyecto
2. **EJEMPLOS_PRACTICOS.md** - Guía detallada con ejemplos código
3. **GUIA_ENSENANZA.md** - Guía completa para enseñar el tema
4. **ERRORES_COMUNES.md** - Errores frecuentes y sus soluciones
5. **run-demo.sh** - Script para ejecutar fácilmente el proyecto

## 🏗️ Estructura de Módulos

```
Nivel 3 (Aplicación):
└─ com.banking.app
   ├─ BankingApplication.java     ← Aplicación principal interactiva
   ├─ ModuleInspector.java        ← Herramienta de inspección
   └─ ModulesConceptsGuide.java   ← Guía de conceptos

Nivel 2 (Servicios):
├─ com.banking.ui
│  └─ BankingConsoleUI.java       ← Interfaz de usuario
│
└─ com.banking.operations
   ├─ SavingsAccount.java         ← Implementación (PRIVADA)
   └─ AccountFactory.java         ← Punto de entrada (PÚBLICO)

Nivel 1 (Núcleo):
└─ com.banking.core
   ├─ BankAccount.java            ← Interfaz pública
   ├─ InsufficientFundsException.java
   └─ InvalidAccountOperationException.java
```

### Características por Módulo

| Módulo | Exporta | Requiere | Propósito |
|--------|---------|----------|-----------|
| **core** | account, exception | (ninguno) | Define el contrato |
| **operations** | factory | core | Implementa de forma encapsulada |
| **ui** | console | core, operations | Proporciona interfaz |
| **app** | (nada) | core, operations, ui | Integra todo |

## ✨ Características Demostradas

- ✅ **Encapsulación de módulos**: Paquetes internos no exportados
- ✅ **Dependencias explícitas**: Validadas en tiempo de compilación
- ✅ **Module Graph**: Visualización de dependencias
- ✅ **Reflection sobre módulos**: Análisis en runtime
- ✅ **Validación automática**: Detección de ciclos y errores
- ✅ **Separación de responsabilidades**: Cada módulo tiene un propósito

## 🚀 Inicio Rápido

### Requisitos
- Java 21+
- Maven 3.8+

### Compilar

```bash
cd modulo08
mvn clean compile
```

### Ejecutar

```bash
# Opción 1: Usando el script
./run-demo.sh app              # Ejecutar aplicación
./run-demo.sh inspect          # Inspeccionar módulos
./run-demo.sh build            # Solo compilar
./run-demo.sh jar              # Empaquetar

# Opción 2: Usando Maven directamente
mvn -pl com.banking.app exec:java \
    -Dexec.mainClass="com.banking.app.BankingApplication"

mvn -pl com.banking.app exec:java \
    -Dexec.mainClass="com.banking.app.ModuleInspector"
```

## 📖 Contenido Didáctico

### module-info.java

Cada módulo contiene un archivo `module-info.java` que define:

```java
module com.example.mymodule {
    // Declarar dependencias de otros módulos
    requires com.example.other;
    requires transitive java.base;
    
    // Exportar paquetes públicos
    exports com.example.public.api;
    exports com.example.other to com.example.specific;
    
    // Para Service Providers
    uses com.example.spi.MyService;
    provides com.example.spi.MyService with com.example.impl.MyServiceImpl;
}
```

### Ejemplo: BankAccount

```java
// Interfaz pública (exportada por core)
public interface BankAccount {
    String getAccountNumber();
    BigDecimal getBalance();
    void deposit(BigDecimal amount);
    void withdraw(BigDecimal amount);
}

// Implementación privada (NO exportada por operations)
class SavingsAccount implements BankAccount {
    // Detalles de implementación encapsulados
}

// Factory pública (exportada por operations)
public class AccountFactory {
    public static BankAccount createSavingsAccount(...) {
        return new SavingsAccount(...);
    }
}
```

**Lección**: Los clientes usan la interfaz y la factory, nunca acceden a la implementación.

## 🎓 Conceptos Clave

### Encapsulación Fuerte

```java
// ✅ PERMITIDO
import com.banking.core.account.BankAccount;
import com.banking.operations.factory.AccountFactory;

// ❌ NO PERMITIDO
import com.banking.operations.account.SavingsAccount;
// Error: package not visible (no está exportado)
```

### Dependencias Explícitas

- `com.banking.app` requiere: core, operations, ui
- `com.banking.ui` requiere: core, operations
- `com.banking.operations` requiere: core
- `com.banking.core` requiere: (nada)

Sin ciclos. Jerarquía clara.

### Validación Automática

El compilador detecta:
- Paquetes no exportados no accesibles
- Módulos requeridos no disponibles
- Dependencias circulares
- Símbolos no encontrados

## 📝 Ejercicios para Estudiantes

### Nivel 1: Entender la Estructura
1. Leer cada `module-info.java`
2. Entender las exportaciones
3. Tracer las dependencias

### Nivel 2: Experimentar
1. Intentar importar `SavingsAccount` (fallará)
2. Cambiar exports en module-info
3. Observar cómo cambian los errores

### Nivel 3: Extender
1. Crear nuevo módulo `com.banking.reports`
2. Implementar generador de reportes
3. Integrar en la aplicación

### Nivel 4: Avanzado
1. Implementar Service Providers
2. Crear validadores modulares
3. Agregar persistencia modular

## 🔍 Inspección de Módulos

Usar `ModuleInspector` para ver:
- Todos los módulos del sistema
- Paquetes exportados
- Dependencias
- Servicios (providers/consumers)

```java
Module module = BankAccount.class.getModule();
ModuleDescriptor descriptor = module.getDescriptor();

descriptor.exports()   // Paquetes públicos
descriptor.requires()  // Dependencias
descriptor.provides()  // Servicios
descriptor.uses()      // Servicios usados
```

## ⚠️ Errores Comunes

Ver **ERRORES_COMUNES.md** para soluciones a:
- "package X is not visible"
- "module X is not accessible"
- "Circular dependencies detected"
- "Cannot access class X" (reflection)
- Y 6 errores más con ejemplos

## 📊 Comparación: Antes vs Ahora

| Aspecto | Java 8 | Java 21+ Módulos |
|---------|--------|------------------|
| Encapsulación | Por convención | Forzada por sistema |
| Dependencias | Implícitas | Explícitas |
| Validación | En runtime | En compilación |
| Arquitectura | Difícil de ver | Clara y explícita |
| Ciclos | Permitidos (problema) | Detectados (error) |

## 🌐 Recursos Externos

- [Project Jigsaw Official](https://openjdk.org/projects/jigsaw/)
- [Module System Tutorial](https://docs.oracle.com/javase/tutorial/java/javaOO/modules.html)
- [State of the Module System](https://cr.openjdk.org/~mr/jigsaw/state-of-modules/)
- [Java 9 Modules by Example](https://www.baeldung.com/java-9-modularity)

## 📞 Soporte

Para preguntas sobre este proyecto:
1. Revisar **GUIA_ENSENANZA.md** para conceptos
2. Revisar **ERRORES_COMUNES.md** para debugging
3. Ver **EJEMPLOS_PRACTICOS.md** para código detallado
4. Ejecutar `./run-demo.sh` con diferentes opciones

---

**Nota**: Este proyecto está diseñado para propósitos educativos. Es un ejemplo simplificado pero completo de arquitectura modular en Java 21.

**Versión**: 1.0  
**Basado en**: Java 21, Maven 3.8+  
**Última actualización**: 2026-03-24

