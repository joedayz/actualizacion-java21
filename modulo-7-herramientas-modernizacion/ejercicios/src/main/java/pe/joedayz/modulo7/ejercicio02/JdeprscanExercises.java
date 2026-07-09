package pe.joedayz.modulo7.ejercicio02;

import java.util.Locale;

/**
 * Ejercicio 02 — Interpretar salida de {@code jdeprscan}.
 */
public final class JdeprscanExercises {

    private JdeprscanExercises() {}

    /**
     * {@code true} si la línea indica un elemento deprecado.
     */
    public static boolean esDeprecado(String linea) {
        if (linea == null || linea.isBlank()) {
            return false;
        }
        String s = linea.toLowerCase(Locale.ROOT);
        return s.contains("deprecated") || s.contains("deprecation");
    }

    /**
     * {@code true} si la deprecación es para remoción ({@code forRemoval} / {@code for-removal}).
     */
    public static boolean esForRemoval(String linea) {
        if (linea == null || linea.isBlank()) {
            return false;
        }
        String s = linea.toLowerCase(Locale.ROOT);
        return s.contains("for removal") || s.contains("for-removal") || s.contains("forremoval");
    }

    /**
     * Prioridad del hallazgo: {@code ALTA} (for-removal), {@code MEDIA} (deprecated),
     * {@code BAJA} (resto).
     */
    public static String prioridad(String linea) {
        if (esForRemoval(linea)) {
            return "ALTA";
        }
        if (esDeprecado(linea)) {
            return "MEDIA";
        }
        return "BAJA";
    }
}
