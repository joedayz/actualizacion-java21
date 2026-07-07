package com.banking.multirelease;

import com.banking.multirelease.utils.VersionFactory;
import com.banking.multirelease.support.VersionInfo;

/**
 * Aplicación de demostración de Multi-Release Module Archives
 * 
 * Este programa muestra cómo un JAR multi-release selecciona automáticamente
 * la implementación correcta según la versión de Java en la que se ejecuta.
 */
public class MultiReleaseDemo {
    
    public static void main(String[] args) {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("  Multi-Release Module Archives Demo");
        System.out.println("  Demostración de soporte multi-versión en Java");
        System.out.println("═".repeat(70) + "\n");
        
        // Detectar si estamos ejecutando desde un JAR
        String classPath = System.getProperty("java.class.path");
        boolean runningFromJar = classPath.contains(".jar");
        
        if (runningFromJar && classPath.contains("multirelease")) {
            System.out.println("✅ Ejecutando desde Multi-Release JAR");
            System.out.println("   El JVM seleccionará automáticamente la versión correcta\n");
        } else {
            System.out.println("ℹ️  Ejecutando desde clases compiladas");
            System.out.println("   Para ver el Multi-Release en acción, ejecuta:");
            System.out.println("   ./run-demo.sh multi-build && ./run-demo.sh multi\n");
        }
        
        // Obtener información del sistema
        System.out.println("📊 INFORMACIÓN DEL SISTEMA");
        System.out.println("─".repeat(70));
        System.out.println(VersionFactory.getJavaVersionInfo());
        System.out.println();
        
        // Obtener la implementación correcta según la versión
        try {
            VersionInfo versionInfo = VersionFactory.getVersionInfo();
            
            System.out.println("🎯 IMPLEMENTACIÓN CARGADA");
            System.out.println("─".repeat(70));
            System.out.println("Descripción: " + versionInfo);
            System.out.println("Versión mínima: " + versionInfo.getMinimumJavaVersion());
            System.out.println("Compilado para: " + versionInfo.getCompiledForVersion());
            System.out.println();
            
            System.out.println("✨ CARACTERÍSTICAS DISPONIBLES");
            System.out.println("─".repeat(70));
            System.out.println(versionInfo.getAvailableFeatures());
            System.out.println();
            
            System.out.println("⚡ BENCHMARK");
            System.out.println("─".repeat(70));
            long startTime = System.currentTimeMillis();
            long benchmarkResult = versionInfo.runBenchmark();
            long totalTime = System.currentTimeMillis() - startTime;
            
            System.out.println("Tiempo de cálculo: " + benchmarkResult + " ms");
            System.out.println("Tiempo total (incluyendo overhead): " + totalTime + " ms");
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("═".repeat(70));
        System.out.println();
    }
}

