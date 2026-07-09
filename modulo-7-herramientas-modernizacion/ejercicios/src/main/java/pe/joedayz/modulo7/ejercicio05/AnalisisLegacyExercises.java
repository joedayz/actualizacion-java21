package pe.joedayz.modulo7.ejercicio05;

import java.util.List;
import java.util.Locale;

/**
 * Ejercicio 05 — Laboratorio: clasificar hallazgos del análisis legacy.
 */
public final class AnalisisLegacyExercises {

    private AnalisisLegacyExercises() {}

    /**
     * Clasifica un hallazgo de herramienta: {@code CRITICO}, {@code DEPRECADO}, {@code OK}.
     */
    public static String clasificarHallazgo(String linea) {
        if (linea == null || linea.isBlank()) {
            return "OK";
        }
        String s = linea.toLowerCase(Locale.ROOT);
        if (s.contains("sun.misc") || s.contains("jdk.unsupported") || s.contains("jdk.internal")
                || s.contains("jdk internal")) {
            return "CRITICO";
        }
        if (s.contains("deprecated") || s.contains("for-removal") || s.contains("for removal")) {
            return "DEPRECADO";
        }
        return "OK";
    }

    /**
     * Orden del pipeline de modernización (nombres canónicos).
     */
    public static List<String> ordenPipeline() {
        return List.of("jdeps", "jdeprscan", "OpenRewrite", "SonarQube");
    }

    /**
     * Plataforma de escala organizacional sobre OpenRewrite.
     */
    public static String plataformaEscala() {
        return "Moderne";
    }
}
