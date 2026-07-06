package pe.joedayz.modulo5.ejercicio01;

import java.util.List;

/**
 * Ejercicio 01 — Interpretar un {@code module-info.java} simplificado (texto).
 */
public final class ModuleInfoExercises {

    private ModuleInfoExercises() {}

    /**
     * Extrae los nombres de módulos en líneas {@code requires} (sin {@code transitive}).
     *
     * <p>Ejemplo de entrada:
     * <pre>
     * module com.app {
     *     requires java.sql;
     *     requires transitive java.logging;
     * }
     * </pre>
     *
     * @return lista ordenada de módulos requeridos, ej. {@code ["java.logging", "java.sql"]}
     */
    public static List<String> extraerRequires(String moduleInfo) {
        // TODO: parsear líneas "requires ..."
        throw new UnsupportedOperationException("TODO: extraerRequires");
    }

    /**
     * Extrae paquetes en líneas {@code exports}.
     */
    public static List<String> extraerExports(String moduleInfo) {
        // TODO: parsear líneas "exports ..."
        throw new UnsupportedOperationException("TODO: extraerExports");
    }

    /**
     * Devuelve el nombre del módulo declarado en {@code module NOMBRE { ... }}.
     */
    public static String extraerNombreModulo(String moduleInfo) {
        // TODO
        throw new UnsupportedOperationException("TODO: extraerNombreModulo");
    }
}
