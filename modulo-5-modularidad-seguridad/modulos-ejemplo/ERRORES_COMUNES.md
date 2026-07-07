# Errores Comunes y Cómo Resolverlos

## Error 1: "package X is not visible"

### ❌ Problema

```java
// En com.banking.app
import com.banking.operations.account.SavingsAccount;
// Error: package com.banking.operations.account is not visible
```

### ✅ Solución

Verificar qué paquetes exporta `com.banking.operations`:

```java
// com.banking.operations/module-info.java
module com.banking.operations {
    requires com.banking.core;
    exports com.banking.operations.factory;  // ← Solo factory
    // account NO se exporta
}
```

Si necesitas el paquete, debes:

**Opción 1**: Usar la factory (forma correcta)
```java
import com.banking.operations.factory.AccountFactory;
BankAccount account = AccountFactory.createSavingsAccount(...);
```

**Opción 2**: Agregar exports (si lo necesitas realmente)
```java
// com.banking.operations/module-info.java
module com.banking.operations {
    requires com.banking.core;
    exports com.banking.operations.factory;
    exports com.banking.operations.account;  // ← Agregar
}
```

---

## Error 2: "module X is not accessible"

### ❌ Problema

```java
// En com.banking.app
import com.banking.operations.factory.AccountFactory;
// Error: module com.banking.operations is not accessible
```

### ✅ Solución

Verificar que `com.banking.app` requiere `com.banking.operations`:

```java
// com.banking.app/module-info.java
module com.banking.app {
    requires com.banking.core;
    requires com.banking.operations;  // ← Debe estar aquí
    requires com.banking.ui;
}
```

---

## Error 3: "Circular dependencies detected"

### ❌ Problema

```java
// com.banking.core/module-info.java
module com.banking.core {
    requires com.banking.operations;  // ← Errror!
}

// com.banking.operations/module-info.java
module com.banking.operations {
    requires com.banking.core;  // ← Esto crea ciclo
}
```

Compilar:
```bash
mvn compile
# Error: Circular dependency detected
```

### ✅ Solución

Rediseñar módulos para evitar ciclos. En este ejemplo:

**Estructura correcta:**
```
com.banking.core (no depende de nada)
    ↑
    │ requires
    │
com.banking.operations (depende de core)
    ↑
    │ requires
    │
com.banking.app (depende de operations y core)
```

**Regla de oro**: No puede haber ciclos. Si A requiere B, entonces B no puede requerir A.

---

## Error 4: "symbol not found"

### ❌ Problema

```java
// En com.banking.operations
public class SavingsAccount implements BankAccount {
    private String accountNumber;  // Olvidé importar
}

// Error: symbol not found: class BankAccount
```

### ✅ Solución

Agregar el import:
```java
import com.banking.core.account.BankAccount;
```

Este es el error típico de Java, no específico de módulos. Pero recuerda:
- El paquete `com.banking.core.account` debe estar exportado en core
- core debe estar en el classpath

---

## Error 5: "POM file is invalid"

### ❌ Problema

```
[FATAL] Non-readable POM /path/to/pom.xml: input contained no data
```

### ✅ Solución

El archivo `pom.xml` está vacío o corrupto.

**Opción 1**: Revisar que el archivo tiene contenido
```bash
cat pom.xml | head -20
```

**Opción 2**: Recrear el archivo
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" ...>
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.example</groupId>
        <artifactId>modulo08-parent</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <artifactId>com.example.mymodule</artifactId>
    <name>My Module</name>
</project>
```

---

## Error 6: "module-info.java not found during compilation"

### ❌ Problema

Estructura incorrecta:
```
com.banking.core/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── com/banking/core/
│   │       │   └── account/
│   │       │       └── BankAccount.java
│   │       └── module-info.java  ← Aquí está bien
│   └── test/
└── pom.xml
```

### ✅ Solución

Asegurar que `module-info.java` está en:
```
src/main/java/module-info.java
```

**NO** en:
```
src/main/resources/module-info.java  ← ¡Mal!
src/module-info.java                  ← ¡Mal!
```

---

## Error 7: "Cannot access com.example.X"

### ❌ Problema

```java
// En com.banking.app, intentando usar reflection
Class<?> cls = Class.forName("com.banking.operations.account.SavingsAccount");
// Runtime error: Cannot access class com.banking.operations.account.SavingsAccount
```

### ✅ Solución

Esto es **intencional**. El módulo system no permite:

```java
// Esto también falla:
Class<?> cls = Class.forName("com.banking.operations.account.SavingsAccount");
cls.getConstructors();  // Errror!
```

**Por qué es bueno:**
- Imposible usar reflection para acceder a código privado
- Fuerza el uso de APIs públicas

**Si realmente necesitas:**
- Exportar el paquete
- O usar `java --add-opens` (solo para testing/debugging)

---

## Error 8: "Different version conflicts"

### ❌ Problema

```
[ERROR] Failed to execute goal on project com.banking.app: 
Could not resolve dependencies for project org.example:com.banking.app:jar:1.0-SNAPSHOT: 
dependency: org.example:com.banking.core:jar:1.0-SNAPSHOT (compile)
    Could not find artifact org.example:com.banking.core:jar:1.0-SNAPSHOT
```

### ✅ Solución

Ejecutar `mvn install` en todos los módulos:

```bash
# Desde el directorio raíz (modulo08/)
mvn clean install -DskipTests

# O desde cada módulo
cd com.banking.core && mvn install
cd ../com.banking.operations && mvn install
cd ../com.banking.ui && mvn install
cd ../com.banking.app && mvn install
```

Esto instala los JARs en el repositorio local de Maven (~/.m2/repository).

---

## Error 9: "exports X to Y but Y does not exist"

### ❌ Problema

```java
// com.banking.core/module-info.java
module com.banking.core {
    exports com.banking.core.account to com.banking.nonexistent;
    // ↑ Este módulo no existe
}
```

### ✅ Solución

Verificar que el módulo destino existe:

```java
// Correcto:
module com.banking.core {
    exports com.banking.core.account to com.banking.ui;
    // ↑ com.banking.ui debe existir
}
```

O simplemente exportar sin restricción:

```java
module com.banking.core {
    exports com.banking.core.account;
    // Accesible a todos
}
```

---

## Error 10: "IDE shows red squiggles but compiles"

### ❌ Problema

El IDE (IntelliJ, Eclipse) muestra errores pero `mvn compile` funciona.

### ✅ Solución

**Opción 1**: Refrescar el IDE
- IntelliJ: File → Invalidate Caches → Restart
- Eclipse: Project → Clean

**Opción 2**: Reconstruir el índice
- IntelliJ: File → Synchronize
- Eclipse: Project → Rebuild All

**Opción 3**: Asegurar que el IDE conoce el JDK 21
- Settings → Project Structure → Project SDK → Seleccionar Java 21

---

## Resumen de Checks

Antes de ejecutar `mvn compile`, verifica:

- [ ] ✅ `module-info.java` está en `src/main/java/`
- [ ] ✅ `pom.xml` contiene el artifact ID correcto
- [ ] ✅ Todos los `requires` en module-info.java corresponden a módulos que existen
- [ ] ✅ Todos los `exports` en module-info.java corresponden a paquetes que existen
- [ ] ✅ No hay dependencias circulares
- [ ] ✅ Las importaciones están en los paquetes exportados
- [ ] ✅ El módulo padre tiene los módulos como `<module>` en su pom.xml

---

## Debugging Tips

### Ver qué módulos están disponibles

```bash
java --list-modules
```

### Ver información detallada de un módulo

```bash
java --describe-module java.base
java --describe-module com.banking.core
```

### Compilar con logs detallados

```bash
mvn compile -X | grep module
```

### Ver estructura de módulo

```bash
jar --describe-module --file com.banking.core-1.0.jar
```

---

## Recursos de Ayuda

Si sigues teniendo problemas:

1. **Lee el mensaje de error completo** - A veces Maven trunca el mensaje
2. **Usa `-X` en Maven** - `mvn -X compile` muestra más detalles
3. **Consulta la consola del IDE** - Puede tener más información
4. **Verifica las versiones** - `mvn --version`, `java --version`
5. **Reconstruye todo** - `mvn clean compile`

