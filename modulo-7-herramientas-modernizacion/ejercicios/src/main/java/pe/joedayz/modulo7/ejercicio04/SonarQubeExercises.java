package pe.joedayz.modulo7.ejercicio04;

import java.util.Locale;

/**
 * Ejercicio 04 — SonarQube Quality Gate y Revapi (breaking changes).
 */
public final class SonarQubeExercises {

    private SonarQubeExercises() {}

    /**
     * Evalúa si el Quality Gate pasa según bugs/vulnerabilidades nuevas.
     *
     * <p>Pasa solo si ambos contadores son 0.
     */
    public static boolean qualityGatePasa(int bugsNuevos, int vulnerabilidadesNuevas) {
        return bugsNuevos == 0 && vulnerabilidadesNuevas == 0;
    }

    /**
     * Clasifica un cambio de API para Revapi: {@code BREAKING}, {@code COMPATIBLE}, {@code DEPRECACION}.
     */
    public static String clasificarCambioApi(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            return "COMPATIBLE";
        }
        String d = descripcion.toLowerCase(Locale.ROOT);
        if (d.contains("deprecat")) {
            return "DEPRECACION";
        }
        if (d.contains("removed") || d.contains("eliminad") || d.contains("deleted")
                || d.contains("signature changed") || d.contains("firma cambi")
                || d.contains("added method to interface") || d.contains("método añadido a interfaz")) {
            return "BREAKING";
        }
        return "COMPATIBLE";
    }

    /**
     * Herramienta adecuada: {@code SONAR} (calidad/código) o {@code REVAPI} (compatibilidad binaria).
     */
    public static String herramientaPara(String objetivo) {
        if (objetivo == null) {
            return "SONAR";
        }
        String o = objetivo.toLowerCase(Locale.ROOT);
        if (o.contains("breaking") || o.contains("api") || o.contains("binari")
                || o.contains("compatibilidad") || o.contains("semver")) {
            return "REVAPI";
        }
        return "SONAR";
    }
}
