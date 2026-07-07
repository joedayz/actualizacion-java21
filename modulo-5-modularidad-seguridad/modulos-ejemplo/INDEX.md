# 📑 Índice Completo - Modulo 08: Java Modules

## 📂 Estructura del Proyecto

```
modulo08/
├── pom.xml                           ← POM padre (multi-módulo)
├── README.md                         ← Descripción general del proyecto
├── EJEMPLOS_PRACTICOS.md            ← Guía con ejemplos de código
├── GUIA_ENSENANZA.md                ← Cómo enseñar este tema a alumnos
├── ERRORES_COMUNES.md               ← Errores frecuentes y soluciones
├── QUICK_REFERENCE.sh               ← Referencia rápida (ejecutable)
├── run-demo.sh                      ← Script para ejecutar demos
├── INDEX.md                         ← Este archivo
│
├── com.banking.core/                ← Módulo 1: Núcleo
│   ├── pom.xml
│   └── src/main/java/
│       ├── module-info.java         ← Define exports: account, exception
│       └── com/banking/core/
│           ├── account/
│           │   └── BankAccount.java       ← Interfaz pública
│           └── exception/
│               ├── InsufficientFundsException.java
│               └── InvalidAccountOperationException.java
│
├── com.banking.operations/          ← Módulo 2: Operaciones
│   ├── pom.xml
│   └── src/main/java/
│       ├── module-info.java         ← Define requires: core
│       │                              exports: factory (NO account)
│       └── com/banking/operations/
│           ├── account/
│           │   └── SavingsAccount.java    ← Privada (NO exportada)
│           └── factory/
│               └── AccountFactory.java    ← Pública (exportada)
│
├── com.banking.ui/                  ← Módulo 3: UI
│   ├── pom.xml
│   └── src/main/java/
│       ├── module-info.java         ← Define requires: core, operations
│       │                              exports: console
│       └── com/banking/ui/
│           └── console/
│               └── BankingConsoleUI.java  ← Interfaz de usuario
│
└── com.banking.app/                 ← Módulo 4: Aplicación
    ├── pom.xml
    └── src/main/java/
        ├── module-info.java         ← Define requires: core, operations, ui
        └── com/banking/app/
            ├── BankingApplication.java        ← Aplicación principal
            ├── ModuleInspector.java           ← Herramienta de inspección
            └── ModulesConceptsGuide.java      ← Guía de conceptos (docs)
```

## 📄 Archivos de Documentación

### README.md
**Contenido**: Descripción general del proyecto
- Estructura de módulos
- Características demostradas
- Inicio rápido
- Conceptos clave
- Recursos externos
- **Tamaño**: ~500 líneas
- **Para**: Cualquiera que quiera entender el proyecto

### EJEMPLOS_PRACTICOS.md
**Contenido**: Guía detallada con ejemplos de código
- Estructura del proyecto explicada
- Todos los module-info.java con explicaciones
- Flujo de dependencias
- Encapsulación en acción (qué está permitido y qué no)
- Cómo ejecutar
- Conceptos clave
- Ejercicios sugeridos (niveles 1-4)
- Preguntas frecuentes
- **Tamaño**: ~600 líneas
- **Para**: Aprender con ejemplos concretos

### GUIA_ENSENANZA.md
**Contenido**: Cómo enseñar el tema a alumnos
- Objetivos educativos
- Estructura de cada módulo
- Puntos clave de cada módulo
- Plan de 3 sesiones:
  - Sesión 1: Introducción (30 min)
  - Sesión 2: Encapsulación en acción (40 min)
  - Sesión 3: Ejercicios prácticos (60 min)
- 4 ejercicios detallados con código
- Puntos para enfatizar
- Preguntas frecuentes de alumnos
- Extensiones posibles
- Conclusión
- **Tamaño**: ~700 líneas
- **Para**: Profesores y formadores

### ERRORES_COMUNES.md
**Contenido**: 10 errores frecuentes con soluciones
1. "package X is not visible"
2. "module X is not accessible"
3. "Circular dependencies detected"
4. "symbol not found"
5. "POM file is invalid"
6. "module-info.java not found"
7. "Cannot access com.example.X"
8. "Different version conflicts"
9. "exports X to Y but Y does not exist"
10. "IDE shows red squiggles but compiles"

Cada error incluye:
- Problema (código que falla)
- Solución (código que funciona)
- Explicación
- **Tamaño**: ~350 líneas
- **Para**: Debugging y troubleshooting

### QUICK_REFERENCE.sh
**Contenido**: Referencia rápida visual
- Estructura de módulo
- Sintaxis de module-info.java
- Palabras clave
- Visibilidad y acceso
- Gráfico de dependencias
- Compilar y ejecutar
- Información en runtime
- Errores comunes
- Ejercicios prácticos
- Referencias
- **Tamaño**: ~200 líneas
- **Para**: Búsquedas rápidas de sintaxis

## 🔧 Scripts Ejecutables

### run-demo.sh
**Funcionalidad**: Ejecutar fácilmente el proyecto
```bash
./run-demo.sh app          # Ejecutar aplicación bancaria
./run-demo.sh inspect      # Inspeccionar módulos
./run-demo.sh build        # Solo compilar
./run-demo.sh jar          # Empaquetar
./run-demo.sh clean        # Limpiar
```

### QUICK_REFERENCE.sh
**Funcionalidad**: Mostrar referencia rápida
```bash
./QUICK_REFERENCE.sh       # Mostrar referencia completa
```

## 💻 Archivos de Código

### module-info.java (4 archivos)

#### com.banking.core/module-info.java
```java
module com.banking.core {
    exports com.banking.core.account;
    exports com.banking.core.exception;
}
```
- **Propósito**: Define qué paquetes son públicos
- **Requiere**: Nada (módulo base)
- **Exporta**: Todo (es la interfaz pública)

#### com.banking.operations/module-info.java
```java
module com.banking.operations {
    requires com.banking.core;
    exports com.banking.operations.factory;
    // NO exporta account (encapsulado)
}
```
- **Propósito**: Implementa el core pero solo expone factory
- **Requiere**: core
- **Exporta**: Solo factory (encapsulación fuerte)

#### com.banking.ui/module-info.java
```java
module com.banking.ui {
    requires com.banking.core;
    requires com.banking.operations;
    exports com.banking.ui.console;
}
```
- **Propósito**: Proporciona interfaz de usuario
- **Requiere**: core y operations
- **Exporta**: console

#### com.banking.app/module-info.java
```java
module com.banking.app {
    requires com.banking.core;
    requires com.banking.operations;
    requires com.banking.ui;
}
```
- **Propósito**: Integra todos los módulos
- **Requiere**: Todos
- **Exporta**: Nada (es aplicación terminal)

### Archivos de Código Fuente

#### BankAccount.java
- **Ubicación**: com.banking.core/account/
- **Tipo**: Interfaz
- **Visibilidad**: Pública (exportada)
- **Propósito**: Define el contrato
- **Métodos**: 7 métodos públicos

#### BankingApplication.java
- **Ubicación**: com.banking.app/
- **Tipo**: Clase con main
- **Visibilidad**: Pública
- **Propósito**: Aplicación principal interactiva
- **Función**: Crea cuentas y proporciona menú

#### ModuleInspector.java
- **Ubicación**: com.banking.app/
- **Tipo**: Clase con main
- **Visibilidad**: Pública
- **Propósito**: Herramienta de inspección
- **Función**: Muestra información de módulos en runtime

#### ModulesConceptsGuide.java
- **Ubicación**: com.banking.app/
- **Tipo**: Documentación (no ejecutable)
- **Visibilidad**: Pública
- **Propósito**: Guía completa de conceptos
- **Contenido**: Explicación detallada en comentarios

#### SavingsAccount.java
- **Ubicación**: com.banking.operations/account/
- **Tipo**: Clase (implementa BankAccount)
- **Visibilidad**: Paquete privada (NO exportada)
- **Propósito**: Implementación concreta
- **Función**: Gestiona cuentas de ahorros

#### AccountFactory.java
- **Ubicación**: com.banking.operations/factory/
- **Tipo**: Clase con método estático
- **Visibilidad**: Pública (exportada)
- **Propósito**: Factory pattern
- **Función**: Crea instancias de SavingsAccount

#### BankingConsoleUI.java
- **Ubicación**: com.banking.ui/console/
- **Tipo**: Clase
- **Visibilidad**: Pública (exportada)
- **Propósito**: Interfaz de usuario
- **Función**: Menú interactivo en consola

#### Excepciones
- **InsufficientFundsException.java**: Fondos insuficientes
- **InvalidAccountOperationException.java**: Operación inválida
- **Ubicación**: com.banking.core/exception/
- **Visibilidad**: Pública (exportadas)

## 🎯 Flujos de Aprendizaje

### Para Principiantes
1. Leer: README.md
2. Ver: Estructura del proyecto
3. Entender: module-info.java
4. Ejecutar: `./run-demo.sh app`

### Para Estudiantes
1. Leer: EJEMPLOS_PRACTICOS.md
2. Experimentar: Ejercicios nivel 1-2
3. Debuggear: Intentar acceso prohibido
4. Inspeccionar: `./run-demo.sh inspect`

### Para Profesores
1. Leer: GUIA_ENSENANZA.md
2. Preparar: 3 sesiones
3. Demonstrar: Código vivo
4. Asignar: Ejercicios para alumnos

### Para Debugging
1. Consultar: ERRORES_COMUNES.md
2. Buscar: El error específico
3. Seguir: Las soluciones
4. Ejecutar: `./run-demo.sh build`

## 📊 Estadísticas del Proyecto

| Métrica | Valor |
|---------|-------|
| Módulos | 4 |
| Archivos Java | 11 |
| Archivos module-info.java | 4 |
| Líneas de código | ~1,200 |
| Líneas de documentación | ~2,500 |
| Archivos de ejemplo | 8 |
| Ejercicios | 4 (nivel 1-4) |
| Errores documentados | 10 |

## 🔗 Relaciones entre Archivos

### Dependencias de Código
```
BankingApplication.java
    └─ require: BankAccount (interface)
    └─ require: AccountFactory
    └─ require: BankingConsoleUI

SavingsAccount.java
    └─ implement: BankAccount
    └─ throw: InsufficientFundsException
    └─ throw: InvalidAccountOperationException

AccountFactory.java
    └─ create: SavingsAccount (private)
    └─ return: BankAccount (interface)

BankingConsoleUI.java
    └─ use: BankAccount
    └─ use: AccountFactory
    └─ catch: Excepciones
```

### Dependencias de Documentación
```
README.md
    └─ reference: EJEMPLOS_PRACTICOS.md
    └─ reference: GUIA_ENSENANZA.md
    └─ reference: ERRORES_COMUNES.md

GUIA_ENSENANZA.md
    └─ reference: EJEMPLOS_PRACTICOS.md
    └─ reference: Código fuente

ERRORES_COMUNES.md
    └─ reference: QUICK_REFERENCE.sh
    └─ reference: Archivos de código
```

## 📈 Cómo Usar Este Índice

1. **¿Quiero entender el proyecto?**
   → Empieza con README.md

2. **¿Quiero ver ejemplos de código?**
   → Lee EJEMPLOS_PRACTICOS.md

3. **¿Quiero enseñar esto a alumnos?**
   → Usa GUIA_ENSENANZA.md

4. **¿Tengo un error?**
   → Busca en ERRORES_COMUNES.md

5. **¿Necesito sintaxis rápida?**
   → Ejecuta QUICK_REFERENCE.sh

6. **¿Dónde está ese archivo?**
   → Consulta la Estructura del Proyecto

## 🎓 Objetivos de Aprendizaje Alcanzados

Al completar este módulo, los estudiantes pueden:

✅ Entender qué son los módulos en Java 21
✅ Crear un nuevo módulo con module-info.java
✅ Exportar e importar paquetes entre módulos
✅ Implementar encapsulación fuerte
✅ Declarar dependencias explícitas
✅ Evitar dependencias circulares
✅ Usar factory patterns en módulos
✅ Inspeccionar módulos en runtime
✅ Debuggear errores de módulos
✅ Diseñar arquitecturas modulares

## 🚀 Próximos Pasos

1. **Ejecutar el proyecto**
   ```bash
   ./run-demo.sh app
   ```

2. **Explorar el código**
   - Abrir cada module-info.java
   - Entender las exportaciones

3. **Hacer los ejercicios**
   - Seguir GUIA_ENSENANZA.md
   - Intentar crear nuevo módulo

4. **Experimentar**
   - Modificar exports
   - Crear dependencias
   - Ver los errores

---

**Última actualización**: 2026-03-24  
**Versión**: 1.0  
**Estado**: ✅ Completo y listo para enseñanza

