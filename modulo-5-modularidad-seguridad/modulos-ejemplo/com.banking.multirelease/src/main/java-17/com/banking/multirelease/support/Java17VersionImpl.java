package com.banking.multirelease.support;

/**
 * Implementación específica para Java 17+
 * Esta versión se compila solo si Java 17+ está disponible
 * y es la que se usa cuando se ejecuta en Java 17
 * 
 * IMPORTANTE: Java 17 introdujo Records como feature final
 */
public class Java17VersionImpl implements VersionInfo {
    
    // En Java 17 podemos usar records (preview en 14-15, final en 16+)
    private record FeatureInfo(String name, String description) {}
    
    @Override
    public String getMinimumJavaVersion() {
        return "17";
    }
    
    @Override
    public String getCompiledForVersion() {
        return "Java 17 LTS (con Records)";
    }
    
    @Override
    public String getAvailableFeatures() {
        // Características nuevas en Java 17
        return "✓ All Java 11 features\n" +
               "✓ Records (data classes)\n" +
               "✓ Sealed Classes\n" +
               "✓ Pattern Matching (enhanced)\n" +
               "✓ Foreign Function & Memory API\n" +
               "✓ Vector API";
    }
    
    @Override
    public long runBenchmark() {
        long startTime = System.nanoTime();
        
        // Java 17: usamos Pattern Matching en instanceof
        for (int i = 1; i <= 1_000_000; i++) {
            Object obj = i;
            // Pattern matching en instanceof (disponible en Java 17)
            if (obj instanceof Integer n) {
                // n es automáticamente Integer sin necesidad de casting
                long value = n.longValue();
            }
        }
        
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
    }
    
    @Override
    public String toString() {
        return "Java 17 LTS Implementation (con Records y Sealed Classes)";
    }
}

