package pe.joedayz.modulo7.ejercicio01;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ejercicio 01 — Interpretar salida de {@code jdeps}.
 */
public final class JdepsExercises {

    private static final Pattern INTERNAL = Pattern.compile(
            "(sun\\.[\\w.]+|jdk\\.internal\\.[\\w.]+|com\\.sun\\.[\\w.]+)");

    private JdepsExercises() {}

    /**
     * {@code true} si la salida menciona APIs internas del JDK
     * ({@code sun.*}, {@code jdk.internal.*}, {@code com.sun.*}) o el módulo
     * {@code jdk.unsupported}.
     */
    public static boolean tieneApisInternas(String salidaJdeps) {
        if (salidaJdeps == null || salidaJdeps.isBlank()) {
            return false;
        }
        String s = salidaJdeps.toLowerCase(Locale.ROOT);
        return s.contains("jdk.unsupported")
                || s.contains("jdk internal api")
                || INTERNAL.matcher(salidaJdeps).find();
    }

    /**
     * Extrae nombres de APIs internas mencionadas en la salida.
     *
     * @return lista ordenada, sin duplicados
     */
    public static List<String> extraerApisInternas(String salidaJdeps) {
        var found = new java.util.TreeSet<String>();
        Matcher m = INTERNAL.matcher(salidaJdeps == null ? "" : salidaJdeps);
        while (m.find()) {
            found.add(m.group(1));
        }
        return new ArrayList<>(found);
    }

    /**
     * Severidad orientativa de un hallazgo jdeps.
     *
     * <ul>
     *   <li>{@code CRITICO} — API interna / jdk.unsupported</li>
     *   <li>{@code OK} — solo módulos públicos del JDK ({@code java.*})</li>
     *   <li>{@code REVISAR} — cualquier otro caso</li>
     * </ul>
     */
    public static String severidad(String linea) {
        if (linea == null || linea.isBlank()) {
            return "REVISAR";
        }
        if (tieneApisInternas(linea)) {
            return "CRITICO";
        }
        String s = linea.toLowerCase(Locale.ROOT);
        if (s.contains("java.base") || s.contains("java.sql") || s.contains("java.logging")
                || s.contains("java.xml") || s.contains("java.desktop")) {
            return "OK";
        }
        return "REVISAR";
    }
}
