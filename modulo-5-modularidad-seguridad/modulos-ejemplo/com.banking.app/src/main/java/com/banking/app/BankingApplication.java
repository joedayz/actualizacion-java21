package com.banking.app;

import com.banking.core.account.BankAccount;
import com.banking.operations.factory.AccountFactory;
import com.banking.reports.api.AccountReportGenerator;
import com.banking.ui.console.BankingConsoleUI;

import java.math.BigDecimal;

/**
 * Aplicación principal del sistema bancario.
 * Demuestra el uso de módulos en Java 21.
 *
 * Este programa muestra cómo:
 * 1. Los módulos encapsulan la funcionalidad
 * 2. Las dependencias son explícitas (module-info.java)
 * 3. Solo se accede a los paquetes exportados
 * 4. La arquitectura es clara y modular
 */
public final class BankingApplication {

    private BankingApplication() {}

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("      Java 21 - Demostración de Módulos (Project Jigsaw)");
        System.out.println("=".repeat(60) + "\n");

        // Mostrar información de módulos
        showModuleInformation();

        // Crear una cuenta bancaria usando la factory del módulo operations
        BankAccount account = AccountFactory.createSavingsAccount(
                "ACC-2024-001",
                "Juan Pérez",
                new BigDecimal("1000.00")
        );

        // Usar la UI del módulo ui
        BankingConsoleUI ui = new BankingConsoleUI(account, () -> AccountReportGenerator.generarReporte(account));
        ui.start();
    }

    /**
     * Muestra información sobre los módulos cargados.
     */
    private static void showModuleInformation() {
        System.out.println("📦 MÓDULOS CARGADOS:");
        System.out.println("─".repeat(60));

        // Obtener el módulo actual
        Module appModule = BankingApplication.class.getModule();

        // Verificar si el módulo tiene descriptor (está en module path)
        if (appModule.getDescriptor() == null) {
            System.out.println("\n⚠️  Ejecutando desde classpath (no module path)");
            System.out.println("   Módulo sin nombre detectado.");
            System.out.println("   Para usar el module system completo, ejecuta con module path.");
            System.out.println("\n" + "─".repeat(60) + "\n");
            return;
        }

        // Mostrar información del módulo actual
        System.out.println("\n🔹 Módulo: " + appModule.getName());
        System.out.println("   Descriptores: " + appModule.getDescriptor());

        // Mostrar las dependencias (required modules)
        System.out.println("\n🔗 DEPENDENCIAS (required modules):");
        appModule.getDescriptor().requires().forEach(req -> {
            System.out.println("   ├─ " + req.name());
        });

        // Mostrar información general del módulo
        System.out.println("\n📋 Información del módulo:");
        System.out.println("   ├─ Nombre: " + appModule.getName());
        System.out.println("   ├─ Clases cargadas: " +
                (appModule.getClassLoader() != null ? "Sí" : "No"));
        System.out.println("   └─ Módulo named: " + appModule.isNamed());

        System.out.println("\n" + "─".repeat(60) + "\n");
    }
}
