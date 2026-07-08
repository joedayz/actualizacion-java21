# Demo JPMS — Sistema bancario modular (Java 21)

Proyecto educativo de **Project Jigsaw**: encapsulación fuerte, dependencias explícitas y `module-info.java`.

**Requisitos:** JDK 21+, Maven 3.8+

## Inicio rápido

### IntelliJ

Abre `modulo-5-modularidad-seguridad/modulos-ejemplo/pom.xml` (no el repo completo). SDK **21+**.

### Terminal

```bash
cd modulo-5-modularidad-seguridad/modulos-ejemplo

mvn clean compile          # compilar
./run-demo.sh inspect      # ver exports/requires en runtime
./run-demo.sh app          # app interactiva (menú consola)
./run-demo.sh              # ver todas las opciones
```

## Estructura de módulos

```
com.banking.app          ← aplicación (no exporta nada)
  requires core, operations, ui

com.banking.ui           ← interfaz consola
  exports com.banking.ui.console
  requires core, operations

com.banking.operations   ← implementación encapsulada
  exports com.banking.operations.factory
  requires core
  (SavingsAccount NO se exporta)

com.banking.core         ← contratos públicos
  exports account, exception
```

| Módulo | Exporta | Requiere |
|--------|---------|----------|
| `com.banking.core` | `account`, `exception` | — |
| `com.banking.operations` | `factory` | `core` |
| `com.banking.ui` | `console` | `core`, `operations` |
| `com.banking.app` | *(nada)* | `core`, `operations`, `ui` |

## `module-info.java` de cada módulo

**core**
```java
module com.banking.core {
    exports com.banking.core.account;
    exports com.banking.core.exception;
}
```

**operations**
```java
module com.banking.operations {
    requires com.banking.core;
    exports com.banking.operations.factory;
}
```

**ui**
```java
module com.banking.ui {
    requires com.banking.core;
    requires com.banking.operations;
    exports com.banking.ui.console;
}
```

**app**
```java
module com.banking.app {
    requires com.banking.core;
    requires com.banking.operations;
    requires com.banking.ui;
}
```

## Qué ejecutar en IntelliJ

| Clase | Módulo | Para qué |
|-------|--------|----------|
| `ModuleInspector` | `com.banking.app` | Ver grafo `com.banking.*` en runtime |
| `BankingApplication` | `com.banking.app` | Demo interactiva |
| `BankingConsoleUI` | `com.banking.ui` | Solo la UI |

## Encapsulación en acción

```java
// ✅ Permitido desde com.banking.app
import com.banking.core.account.BankAccount;
import com.banking.operations.factory.AccountFactory;
import com.banking.ui.console.BankingConsoleUI;

// ❌ No compila — paquete no exportado
import com.banking.operations.account.SavingsAccount;
```

Usa la **factory** para crear cuentas; la implementación `SavingsAccount` queda oculta dentro de `operations`.

## Ejercicios sugeridos

1. Lee los 4 `module-info.java` y traza el grafo de dependencias.
2. En `BankingApplication`, intenta importar `SavingsAccount` y lee el error del compilador.
3. Ejecuta `ModuleInspector` y compara con lo que leíste en los `module-info`.
4. *(Avanzado)* Crea `com.banking.reports` que requiera `core` y exponga un paquete de reportes.

## Errores frecuentes

| Error | Causa | Solución |
|-------|-------|----------|
| `package X is not visible` | Importas un paquete no exportado | Usa la API pública (factory/interfaz) o añade `exports` |
| `module X is not accessible` | Falta `requires` en `module-info.java` | Añade `requires com.banking.X` |
| `Circular dependency` | A requiere B y B requiere A | Rediseña capas (core → operations → ui → app) |
| `Cannot find artifact` | Módulos no instalados en `.m2` | `mvn clean install -DskipTests` desde la raíz |
| `module-info.java not found` | Archivo mal ubicado | Debe estar en `src/main/java/module-info.java` |

**Checks antes de compilar:** sin ciclos en `requires`, cada `exports` apunta a un paquete real, `module-info.java` en `src/main/java/`.

```bash
java --describe-module com.banking.core   # tras empaquetar
mvn compile -X | grep module              # debug Maven
```

## Multi-Release JAR (opcional)

Tema avanzado en `com.banking.multirelease/`. Ver su `README.md` o:

```bash
./run-demo.sh multi
```

## Recursos

- [Tutorial módulos (Oracle)](https://docs.oracle.com/javase/tutorial/java/javaOO/modules.html)
- [Project Jigsaw](https://openjdk.org/projects/jigsaw/)
