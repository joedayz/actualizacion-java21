package com.banking.multirelease.support;

/**
 * Implementación específica para Java 11+
 * Esta versión se compila solo si Java 11+ está disponible
 * y es la que se usa cuando se ejecuta en Java 11
 */
public class Java11VersionImpl implements VersionInfo {
    
    @Override
    public String getMinimumJavaVersion() {
        return "11";
    }
    
    @Override
    public String getCompiledForVersion() {
        return "Java 11 (mejorado)";
    }
    
    @Override
    public String getAvailableFeatures() {
        // Características nuevas en Java 11
        return "✓ All Java 9 features\n" +
               "✓ HTTP Client API\n" +
               "✓ Local Variable Syntax for Lambda Parameters\n" +
               "✓ String methods: isBlank(), lines(), strip()\n" +
               "✓ Nest-based Access Control\n" +
               "✓ Dynamic Class-File Constants";
    }
    
    @Override
    public long runBenchmark() {
        long startTime = System.nanoTime();
        
        // Java 11: usamos Stream con takeWhile (nuevo en Java 9)
        long sum = java.util.stream.LongStream.rangeClosed(1, 1_000_000)
            .sum();
        
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000; // millisegundos
    }
    
    @Override
    public String toString() {
        return "Java 11 Implementation (con HTTP Client)";
    }
}

