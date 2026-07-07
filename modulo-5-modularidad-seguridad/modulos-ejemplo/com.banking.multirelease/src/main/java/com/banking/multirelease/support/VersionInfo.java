package com.banking.multirelease.support;

/**
 * Interfaz base para soporte de versiones de Java.
 * Esta interfaz es la misma para todas las versiones.
 */
public interface VersionInfo {
    
    /**
     * Obtiene la versión mínima de Java soportada
     */
    String getMinimumJavaVersion();
    
    /**
     * Obtiene la versión de Java compilada
     */
    String getCompiledForVersion();
    
    /**
     * Obtiene información sobre características disponibles
     */
    String getAvailableFeatures();
    
    /**
     * Ejecuta un benchmark de la implementación
     */
    long runBenchmark();
}

