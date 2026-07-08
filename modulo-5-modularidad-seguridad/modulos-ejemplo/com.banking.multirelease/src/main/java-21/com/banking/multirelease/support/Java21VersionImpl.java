package com.banking.multirelease.support;

/**
 * Implementación específica para Java 21+
 * Esta versión se compila solo si Java 21+ está disponible
 * y es la que se usa cuando se ejecuta en Java 21
 * 
 * IMPORTANTE: Java 21 introdujo Virtual Threads (Project Loom)
 * y String Templates (preview)
 */
public class Java21VersionImpl implements VersionInfo {
    
    @Override
    public String getMinimumJavaVersion() {
        return "21";
    }
    
    @Override
    public String getCompiledForVersion() {
        return "Java 21 (última versión con Virtual Threads)";
    }
    
    @Override
    public String getAvailableFeatures() {
        // Características nuevas en Java 21
        return "✓ All Java 17 features\n" +
               "✓ Virtual Threads (Project Loom - final)\n" +
               "✓ Structured Concurrency\n" +
               "✓ Record Patterns (pattern matching mejorado)\n" +
               "✓ String Templates (preview)\n" +
               "✓ Generational ZGC\n" +
               "✓ Unnamed Patterns and Variables";
    }
    
    @Override
    public long runBenchmark() {
        long startTime = System.nanoTime();
        
        // Java 21: usamos Virtual Threads para mejor rendimiento
        long sum = 0;
        
        // Simulación con VirtualThreads
        try {
            // Crear un virtual thread para el cálculo
            Thread vt = Thread.ofVirtual()
                .name("calculator")
                .start(() -> {
                    // El código se ejecuta en un virtual thread
                    long localSum = 0;
                    for (int i = 1; i <= 1_000_000; i++) {
                        localSum += i;
                    }
                });
            
            vt.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
    }
    
    @Override
    public String toString() {
        return "Java 21 Implementation (con Virtual Threads)";
    }
}

