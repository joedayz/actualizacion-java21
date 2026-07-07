#!/bin/bash

# Quick Reference - Módulos Java 21
# Use este archivo como referencia rápida

cat << 'EOF'

╔════════════════════════════════════════════════════════════════════════════╗
║                   JAVA 21 MODULES - QUICK REFERENCE                       ║
║                                                                            ║
║                    Proyecto: Banking Application                          ║
╚════════════════════════════════════════════════════════════════════════════╝

┌────────────────────────────────────────────────────────────────────────────┐
│ 1. ESTRUCTURA DE MÓDULO                                                    │
└────────────────────────────────────────────────────────────────────────────┘

  com.banking.core/
  ├── pom.xml
  └── src/main/java/
      ├── module-info.java          ← Define el módulo
      └── com/banking/core/
          ├── account/
          │   └── BankAccount.java
          └── exception/
              ├── InsufficientFundsException.java
              └── InvalidAccountOperationException.java

┌────────────────────────────────────────────────────────────────────────────┐
│ 2. module-info.java - Sintaxis Básica                                     │
└────────────────────────────────────────────────────────────────────────────┘

  module com.banking.core {
      // 1. Declarar dependencias
      requires java.base;                    // Módulo del JDK
      requires transitive com.banking.core;  // Transitivo

      // 2. Exportar paquetes
      exports com.banking.core.account;          // Al mundo
      exports com.banking.core.exception to      // Solo a:
          com.banking.operations,
          com.banking.ui;

      // 3. Service Providers (avanzado)
      uses com.example.spi.MyService;
      provides com.example.spi.MyService
          with com.example.impl.MyServiceImpl;
  }

┌────────────────────────────────────────────────────────────────────────────┐
│ 3. PALABRAS CLAVE                                                          │
└────────────────────────────────────────────────────────────────────────────┘

  MODULE          Define el nombre del módulo
  REQUIRES        Declara una dependencia
  REQUIRES STATIC Dependencia solo en compilación
  REQUIRES TRANSITIVE  La dependencia es transitiva
  EXPORTS         Hace un paquete público
  OPENS           Abre un paquete para reflection
  USES            Este módulo usa un servicio
  PROVIDES        Este módulo proporciona un servicio
  WITH            Implementación de un servicio

┌────────────────────────────────────────────────────────────────────────────┐
│ 4. VISIBILIDAD Y ACCESO                                                    │
└────────────────────────────────────────────────────────────────────────────┘

  ✅ ACCESO PERMITIDO
  ─────────────────────────────────────────────────────────────────────────
  // En com.banking.app
  import com.banking.core.account.BankAccount;        // ✓ Exportado
  import com.banking.operations.factory.AccountFactory; // ✓ Exportado
  import com.banking.ui.console.BankingConsoleUI;     // ✓ Exportado

  ❌ ACCESO BLOQUEADO
  ─────────────────────────────────────────────────────────────────────────
  // En com.banking.app
  import com.banking.operations.account.SavingsAccount;
  // ✗ ERROR: package com.banking.operations.account is not visible

  Razón: El paquete NO está exportado en operations/module-info.java

┌────────────────────────────────────────────────────────────────────────────┐
│ 5. GRÁFICO DE DEPENDENCIAS                                                 │
└────────────────────────────────────────────────────────────────────────────┘

                    ┌─────────────────────┐
                    │ com.banking.app     │
                    │  (Aplicación)       │
                    └──────────┬──────────┘
                         requires
          ┌─────────────────┼─────────────────┐
          │                 │                 │
          ▼                 ▼                 ▼
    ┌──────────┐    ┌──────────────┐  ┌────────────┐
    │  core    │    │ operations   │  │    ui      │
    │(Base)    │    │(Impl)        │  │(Console)   │
    └──────────┘    └──────┬───────┘  └────┬───────┘
         ▲                 │               │
         │                 requires        │
         └─────────────────┴───────────────┘

  Regla: NO hay ciclos. Solo flujo hacia arriba.

┌────────────────────────────────────────────────────────────────────────────┐
│ 6. COMPILAR Y EJECUTAR                                                     │
└────────────────────────────────────────────────────────────────────────────┘

  Compilar:
  ─────────
  mvn clean compile

  Empaquetar:
  ───────────
  mvn clean package -DskipTests

  Ejecutar aplicación:
  ────────────────────
  mvn -pl com.banking.app exec:java \
      -Dexec.mainClass="com.banking.app.BankingApplication"

  Inspeccionar módulos:
  ─────────────────────
  mvn -pl com.banking.app exec:java \
      -Dexec.mainClass="com.banking.app.ModuleInspector"

  Limpiar:
  ────────
  mvn clean

┌────────────────────────────────────────────────────────────────────────────┐
│ 7. INFORMACIÓN DE MÓDULOS EN RUNTIME                                       │
└────────────────────────────────────────────────────────────────────────────┘

  // Obtener módulo de una clase
  Module module = BankAccount.class.getModule();

  // Nombre del módulo
  String name = module.getName();

  // Descriptor del módulo
  ModuleDescriptor descriptor = module.getDescriptor();

  // Paquetes exportados
  descriptor.exports().forEach(exp ->
      System.out.println(exp.source())
  );

  // Módulos requeridos
  descriptor.requires().forEach(req ->
      System.out.println(req.name())
  );

  // Servicios proporcionados
  descriptor.provides().forEach(prov ->
      System.out.println(prov.service())
  );

┌────────────────────────────────────────────────────────────────────────────┐
│ 8. ERRORES COMUNES                                                         │
└────────────────────────────────────────────────────────────────────────────┘

  ERROR                           SOLUCIÓN
  ─────────────────────────────────────────────────────────────────────────
  package X is not visible        → Agregar exports en module-info.java
  module X is not accessible      → Agregar requires en module-info.java
  Circular dependency detected    → Rediseñar para evitar ciclos
  Cannot access class X           → Exportar el paquete
  symbol not found                → Importar la clase correcta
  POM file is invalid             → Verificar que pom.xml no está vacío

┌────────────────────────────────────────────────────────────────────────────┐
│ 9. EJERCICIOS PRÁCTICOS                                                    │
└────────────────────────────────────────────────────────────────────────────┘

  1. LEER module-info.java
     Abre cada archivo y entiende:
     - Qué módulos requiere
     - Qué paquetes exporta
     - Por qué es así

  2. INTENTAR ACCESO PROHIBIDO
     Intenta importar SavingsAccount desde BankingApplication
     Ve el error y entiende por qué ocurre

  3. CREAR NUEVO MÓDULO
     Crea com.banking.reports que:
     - Requiere com.banking.core
     - Exporta com.banking.reports
     - Proporciona reportes

  4. AGREGAR DEPENDENCIA CIRCULAR
     Haz que core requiera reports
     Ve cómo Maven detecta el error

  5. INSPECCIONAR EN RUNTIME
     Ejecuta ModuleInspector
     Ve todos los módulos disponibles

┌────────────────────────────────────────────────────────────────────────────┐
│ 10. REFERENCIAS RÁPIDAS                                                    │
└────────────────────────────────────────────────────────────────────────────┘

  Documentación:
  • https://docs.oracle.com/javase/tutorial/java/javaOO/modules.html
  • https://openjdk.org/projects/jigsaw/

  Archivos en este proyecto:
  • README.md - Descripción general
  • EJEMPLOS_PRACTICOS.md - Ejemplos de código
  • GUIA_ENSENANZA.md - Cómo enseñar este tema
  • ERRORES_COMUNES.md - Soluciones a errores
  • run-demo.sh - Scripts de ejecución
  • ModulesConceptsGuide.java - Guía de conceptos
  • ModuleInspector.java - Herramienta de inspección

╔════════════════════════════════════════════════════════════════════════════╗
║                                                                            ║
║  Recuerda: Los módulos son sobre ENCAPSULACIÓN, no solo ORGANIZACIÓN      ║
║                                                                            ║
║  • Solo exporta lo necesario                                              ║
║  • Mantén las implementaciones privadas                                   ║
║  • Las dependencias deben ser acíclicas                                   ║
║  • Usa factory patterns para crear instancias                             ║
║                                                                            ║
╚════════════════════════════════════════════════════════════════════════════╝

EOF

