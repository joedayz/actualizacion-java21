package pe.joedayz.modulo5.ejercicio01;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Ejercicio 01 — Interpretar un {@code module-info.java} simplificado (texto).
 */
public final class ModuleInfoExercises {

    private static final Pattern MODULE_NAME = Pattern.compile("module\\s+([\\w.]+)\\s*\\{");
    private static final Pattern REQUIRES = Pattern.compile("requires\\s+(?:transitive\\s+)?([\\w.]+)\\s*");
    private static final Pattern EXPORTS = Pattern.compile("exports\\s+([\\w.]+)");


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
        TreeSet<String> mods = new TreeSet<>();
        Matcher m = REQUIRES.matcher(moduleInfo);
        while(m.find()) {
            mods.add(m.group(1));
        }
        return new ArrayList<>(mods);
    }

    /**
     * Extrae paquetes en líneas {@code exports}.
     */
    public static List<String> extraerExports(String moduleInfo) {
        List<String> pkgs = new ArrayList<>();
        Matcher m = EXPORTS.matcher(moduleInfo);
        while(m.find()) {
            pkgs.add(m.group(1));
        }
        return pkgs;
    }

    /**
     * Devuelve el nombre del módulo declarado en {@code module NOMBRE { ... }}.
     */
    public static String extraerNombreModulo(String moduleInfo) {
        Matcher m = MODULE_NAME.matcher(moduleInfo);
        if(!m.find()) {
            throw new IllegalArgumentException("module-info invalido: no se encontró declaración de módulo");
        }
        return m.group(1);
    }
}
