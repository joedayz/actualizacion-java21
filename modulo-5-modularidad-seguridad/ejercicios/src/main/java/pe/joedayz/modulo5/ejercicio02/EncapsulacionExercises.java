package pe.joedayz.modulo5.ejercicio02;

/**
 * Ejercicio 02 — Encapsulación JPMS y flags de migración.
 */
public final class EncapsulacionExercises {

    private EncapsulacionExercises() {}

    /**
     * Indica si un nombre calificado pertenece a un paquete interno del JDK
     * ({@code jdk.internal.*}, {@code sun.*}, {@code com.sun.*}).
     */
    public static boolean esPaqueteInternoJdk(String qualifiedName) {
        if(qualifiedName == null || qualifiedName.isBlank()) {
            return false;
        }
        return qualifiedName.startsWith("jdk.internal.")
                || qualifiedName.startsWith("sun.")
                || qualifiedName.startsWith("com.sun.");
    }

    /**
     * Construye el flag JVM {@code --add-opens} para abrir {@code pkg} del módulo {@code modulo}
     * hacia {@code destino} (ej. {@code ALL-UNNAMED}).
     *
     * <p>Formato: {@code --add-opens modulo/pkg=destino}
     */
    public static String construirAddOpens(String modulo, String pkg, String destino) {
       return "--add-opens " + modulo + "/" + pkg + "=" + destino;
    }

    /**
     * Dado un módulo y paquete, indica si puede leerse sin {@code opens}
     * (solo con {@code exports} al módulo lector).
     */
    public static boolean puedeLeerseConExports(String moduloExportador, String paquete,
            String moduloLector, String moduleInfoExportador) {

        String exportTo = "exports " + paquete + " to " + moduloLector + ";";
        String exportPublic = "exports " + paquete;
        return  moduleInfoExportador.contains(exportTo)
        || (moduleInfoExportador.contains(exportPublic)
                && !moduleInfoExportador.contains(exportPublic + " to "));
    }
}
