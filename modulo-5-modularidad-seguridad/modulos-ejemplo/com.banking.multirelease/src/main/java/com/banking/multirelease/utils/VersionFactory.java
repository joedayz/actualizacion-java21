package com.banking.multirelease.utils;

import com.banking.multirelease.support.VersionInfo;

/**
 * Clase factory que devuelve la implementación correcta de VersionInfo
 * según la versión de Java en la que se ejecuta.
 * 
 * IMPORTANTE: Esta clase siempre está disponible en todas las versiones.
 * Lo que cambia es la implementación de VersionInfo que retorna.
 */
public class VersionFactory {
    
    private static VersionInfo versionInfo;
    
    static {
        // Intenta cargar la implementación específica de la versión
        // Si no existe, usa la implementación base (Java9VersionImpl)
        String javaVersion = System.getProperty("java.version");
        String javaVersionNumber = javaVersion.split("\\.")[0];
        
        try {
            // En Java 21: cargará com.banking.multirelease.support.Java21VersionImpl
            // En Java 17: cargará com.banking.multirelease.support.Java17VersionImpl
            // En Java 11: cargará com.banking.multirelease.support.Java11VersionImpl
            // En Java 9:  cargará com.banking.multirelease.support.Java9VersionImpl
            
            String className = "com.banking.multirelease.support.Java" + javaVersionNumber + "VersionImpl";
            versionInfo = (VersionInfo) Class.forName(className).getDeclaredConstructor().newInstance();
            
            System.out.println("✅ Cargada implementación: " + className);
        } catch (ClassNotFoundException e) {
            System.out.println("⚠️  Implementación específica no encontrada, usando base (Java 9)");
            // Fallback a implementación base
            try {
                versionInfo = new com.banking.multirelease.support.Java9VersionImpl();
                System.out.println("✅ Usando implementación base: Java9VersionImpl");
            } catch (Exception fallbackError) {
                System.err.println("❌ Error crítico al cargar implementación base: " + fallbackError.getMessage());
            }
        } catch (Exception e) {
            System.err.println("❌ Error al cargar implementación: " + e.getMessage());
            // Intentar fallback
            try {
                versionInfo = new com.banking.multirelease.support.Java9VersionImpl();
                System.out.println("✅ Usando implementación base: Java9VersionImpl");
            } catch (Exception fallbackError) {
                System.err.println("❌ Error crítico: " + fallbackError.getMessage());
            }
        }
    }
    
    /**
     * Obtiene la información de versión de la implementación correcta
     */
    public static VersionInfo getVersionInfo() {
        if (versionInfo == null) {
            throw new RuntimeException("No se pudo cargar la implementación de VersionInfo");
        }
        return versionInfo;
    }
    
    /**
     * Obtiene información de la versión de Java actual
     */
    public static String getJavaVersionInfo() {
        return String.format(
            "Java Version: %s | Java Home: %s",
            System.getProperty("java.version"),
            System.getProperty("java.home")
        );
    }
}

