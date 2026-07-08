package com.banking.app;

import com.banking.core.account.BankAccount;

import java.lang.module.ModuleDescriptor;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Inspecciona en runtime los módulos {@code com.banking.*} cargados en la JVM.
 */
public final class ModuleInspector {



    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("              INSPECTOR DE MÓDULOS JAVA 21");
        System.out.println("=".repeat(70) + "\n");

        // Obtener el sistema de módulos
        System.out.println("📚 MÓDULOS DEL SISTEMA:");
        System.out.println("─".repeat(70));

        ModuleLayer.boot().modules().stream()
                .sorted((m1, m2) -> m1.getName().compareTo(m2.getName()))
                .forEach(module -> inspectModule(module));

        System.out.println("\n" + "─".repeat(70) + "\n");
    }

    private static void inspectModule(Module module) {
        var descriptor = module.getDescriptor();

        System.out.println("\n🔷 Módulo: " + module.getName());

        if (descriptor == null) {
            System.out.println("   └─ Sistema de módulos no disponible");
            return;
        }

        // Mostrar paquetes exportados
        if (!descriptor.exports().isEmpty()) {
            System.out.println("   📤 Paquetes Exportados:");
            descriptor.exports().forEach(exp -> {
                String targets = exp.targets().isEmpty() ?
                        "" : " (hacia: " + String.join(", ", exp.targets()) + ")";
                System.out.println("      └─ " + exp.source() + targets);
            });
        }

        // Mostrar dependencias
        if (!descriptor.requires().isEmpty()) {
            System.out.println("   🔗 Dependencias:");
            descriptor.requires().forEach(req -> {
                System.out.println("      └─ " + req.name());
            });
        }

        // Mostrar servicios
        if (!descriptor.provides().isEmpty()) {
            System.out.println("   🔌 Servicios Proporcionados:");
            descriptor.provides().forEach(prov -> {
                System.out.println("      ├─ " + prov.service());
                prov.providers().forEach(p ->
                        System.out.println("      │  └─ " + p)
                );
            });
        }

        if (!descriptor.uses().isEmpty()) {
            System.out.println("   📦 Servicios Utilizados:");
            descriptor.uses().forEach(use ->
                    System.out.println("      └─ " + use)
            );
        }
    }
}
