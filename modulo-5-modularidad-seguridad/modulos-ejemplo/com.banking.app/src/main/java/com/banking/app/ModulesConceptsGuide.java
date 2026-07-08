package com.banking.app;

/**
 * Ejemplo que demuestra cómo usar módulos en Java 21.
 * Este archivo es una guía de conceptos clave sobre el Sistema de Módulos.
 */
public final class ModulesConceptsGuide {

// Este archivo NO se ejecuta, es solo documentación de conceptos

    /*
     * ═══════════════════════════════════════════════════════════════════════════════
     *                    CONCEPTOS CLAVE DE MÓDULOS EN JAVA 21
     * ═══════════════════════════════════════════════════════════════════════════════
     *
     * 1. ¿QUÉ ES UN MÓDULO?
     * ──────────────────────
     * Un módulo es una unidad de código que contiene:
     *   - Un conjunto de paquetes
     *   - Recursos
     *   - Un descriptor (module-info.java)
     *
     * El módulo define qué código es público (exportado) y qué dependencias necesita.
     *
     *
     * 2. ESTRUCTURA DE UN MÓDULO
     * ────────────────────────────
     *
     * com.banking.core/
     * ├── src/main/java/
     * │   ├── module-info.java              ← Descriptor del módulo
     * │   └── com/banking/core/
     * │       ├── account/
     * │       │   └── BankAccount.java     ← Exportado
     * │       └── exception/
     * │           └── InsufficientFundsException.java  ← Exportado
     * └── pom.xml
     *
     *
     * 3. MODULE-INFO.JAVA
     * ────────────────────
     * Define la configuración del módulo:
     *
     *   module com.banking.core {
     *       // Paquetes que exporta (hace públicos)
     *       exports com.banking.core.account;
     *       exports com.banking.core.exception;
     *   }
     *
     *
     * 4. TIPOS DE DECLARACIONES EN module-info.java
     * ──────────────────────────────────────────────
     *
     * a) MODULE
     * ─────────
     *   module com.banking.core { ... }
     *   Define el nombre del módulo (debe coincidir con el artifactId en Maven)
     *
     *
     * b) REQUIRES
     * ────────────
     *   requires com.banking.core;
     *   requires transitive java.base;
     *
     *   - Declara dependencias en otros módulos
     *   - Sin 'transitive': solo ese módulo puede usar la dependencia
     *   - Con 'transitive': otros módulos que dependan de este también tienen acceso
     *
     *
     * c) EXPORTS
     * ──────────
     *   exports com.banking.core.account;
     *   exports com.banking.core.exception to com.banking.ui;
     *
     *   - Especifica qué paquetes son públicos
     *   - Sin 'to': el paquete es público para todos
     *   - Con 'to': el paquete solo es visible para los módulos especificados
     *
     *
     * d) USES / PROVIDES
     * ───────────────────
     *   uses com.example.spi.MyService;
     *   provides com.example.spi.MyService with com.example.impl.MyServiceImpl;
     *
     *   - Para implementar el patrón Service Provider Interface (SPI)
     *   - 'uses': este módulo usa un servicio
     *   - 'provides': este módulo implementa un servicio
     *
     *
     * 5. BENEFICIOS DE LOS MÓDULOS
     * ──────────────────────────────
     *
     * ✓ ENCAPSULACIÓN FUERTE
     *   - Solo se exponen los paquetes necesarios
     *   - El resto es completamente privado
     *   - Imposible acceder a clases internas sin reflección especial
     *
     * ✓ DEPENDENCIAS EXPLÍCITAS
     *   - El archivo module-info.java documenta todas las dependencias
     *   - El compilador y runtime las validan
     *   - Se detectan errores circulares automáticamente
     *
     * ✓ SEGURIDAD
     *   - Control fino sobre qué código es visible desde donde
     *   - Imposible depender accidentalmente de implementaciones internas
     *   - Facilita la evolución de APIs sin romper el código cliente
     *
     * ✓ OPTIMIZACIÓN
     *   - El sistema puede hacer análisis estático más completo
     *   - Posibles optimizaciones en tiempo de compilación y runtime
     *   - Mejor cacheo de clases
     *
     * ✓ CLARIDAD
     *   - La arquitectura es más clara y explícita
     *   - Las responsabilidades de cada módulo son obvias
     *   - Facilita el desarrollo en equipo
     *
     *
     * 6. EJEMPLO EN ESTE PROYECTO
     * ─────────────────────────────
     *
     * Estructura de módulos:
     *
     *   ┌──────────────────────────────────────────────────────────────┐
     *   │                    com.banking.app                           │
     *   │                  (Aplicación Principal)                      │
     *   └──────────────┬────────────────┬────────────────┬─────────────┘
     *                  │                │                │
     *                  ▼                ▼                ▼
     *   ┌──────────────────┐  ┌──────────────────┐  ┌──────────────┐
     *   │ com.banking.core │  │com.banking.ui    │  │com.banking.  │
     *   │                  │  │                  │  │operations    │
     *   │- BankAccount (I) │  │- BankingConsole  │  │- Factory     │
     *   │- Exceptions      │  │  UI              │  │(encapsulada) │
     *   └──────────────────┘  └────────┬─────────┘  └──────┬───────┘
     *        (Exporta)                 │                    │
     *                                  ▼                    ▼
     *                          (Requiere core)     (Requiere core)
     *
     *
     * CARACTERÍSTICAS:
     *
     * 🔹 com.banking.core
     *    - Define la interfaz BankAccount
     *    - Define las excepciones
     *    - Es exportado completamente (público)
     *
     * 🔹 com.banking.operations
     *    - Implementa SavingsAccount (que implementa BankAccount)
     *    - Proporciona AccountFactory para crear cuentas
     *    - NO exporta la implementación (solo la factory)
     *    - Encapsula SavingsAccount completamente
     *
     * 🔹 com.banking.ui
     *    - Proporciona BankingConsoleUI
     *    - Depende de core y operations
     *
     * 🔹 com.banking.app
     *    - Punto de entrada de la aplicación
     *    - Depende de todos los módulos anteriores
     *    - Proporciona ModuleInspector para analizar módulos en runtime
     *
     *
     * 7. ENCAPSULACIÓN EN ACCIÓN
     * ───────────────────────────
     *
     * Desde com.banking.app, podemos:
     *   ✓ Usar com.banking.core.account.BankAccount (es exportado)
     *   ✓ Usar com.banking.operations.factory.AccountFactory (es exportado)
     *   ✓ Usar com.banking.ui.console.BankingConsoleUI (es exportado)
     *
     * Desde com.banking.app, NO podemos:
     *   ✗ Usar com.banking.operations.account.SavingsAccount
     *     (el paquete account NO es exportado)
     *
     *
     * 8. REFLEJO DE MÓDULOS EN RUNTIME
     * ──────────────────────────────────
     *
     * Podemos obtener información de módulos en tiempo de ejecución:
     *
     *   Module module = MyClass.class.getModule();
     *   ModuleDescriptor descriptor = module.getDescriptor();
     *
     *   descriptor.name();           // Nombre del módulo
     *   descriptor.exports();        // Paquetes exportados
     *   descriptor.requires();       // Dependencias
     *   descriptor.provides();       // Servicios proporcionados
     *   descriptor.uses();           // Servicios utilizados
     *
     *
     * 9. CONFIGURACIÓN EN MAVEN
     * ───────────────────────────
     *
     * En pom.xml, el module-info.java se compila automáticamente.
     *
     * Maven maneja las dependencias entre módulos si están en el mismo
     * proyecto multi-módulo o si están en repositorios.
     *
     *
     * 10. COMPARACIÓN CON VERSIONES ANTERIORES
     * ──────────────────────────────────────────
     *
     * ANTES (Java 8):
     *   - No había encapsulación a nivel de módulo
     *   - Todos los paquetes públicos eran accesibles globalmente
     *   - Era difícil identificar dependencias reales
     *   - No se validaban las dependencias en tiempo de compilación
     *
     * AHORA (Java 21 con módulos):
     *   - Encapsulación fuerte a nivel de módulo
     *   - Solo se exponen los paquetes necesarios
     *   - Las dependencias son explícitas y validadas
     *   - El compilador rechaza dependencias circulares
     *   - Mejor seguridad y más clara la arquitectura
     *
     *
     * ═══════════════════════════════════════════════════════════════════════════════
     *
     * Para más información:
     * - https://docs.oracle.com/javase/tutorial/java/javaOO/modules.html
     * - https://openjdk.org/projects/jigsaw/
     *
}
