package pe.joedayz.modulo7.ejercicio03;

import java.util.Locale;

/**
 * Ejercicio 03 — Modelo mental de OpenRewrite (recetas y modos).
 */
public final class OpenRewriteExercises {

    private OpenRewriteExercises() {}

    /**
     * Clasifica el goal Maven: {@code dryRun}, {@code run} o {@code DESCONOCIDO}.
     */
    public static String clasificarGoal(String goal) {
        if (goal == null || goal.isBlank()) {
            return "DESCONOCIDO";
        }
        String g = goal.trim().toLowerCase(Locale.ROOT);
        if (g.equals("dryrun") || g.equals("dry-run") || g.endsWith(":dryrun")) {
            return "dryRun";
        }
        if (g.equals("run") || g.endsWith(":run")) {
            return "run";
        }
        return "DESCONOCIDO";
    }

    /**
     * {@code true} si el nombre de receta parece de migración JDK / Java.
     */
    public static boolean esRecetaMigracionJava(String recipe) {
        if (recipe == null || recipe.isBlank()) {
            return false;
        }
        String r = recipe.toLowerCase(Locale.ROOT);
        return r.contains("migrate") && (r.contains("java") || r.contains("jdk"))
                || r.contains("upgradetojava");
    }

    /**
     * Orden recomendado de familias de recetas (1 = primero).
     *
     * <p>{@code JDK} → {@code JAKARTA} → {@code SPRING} → otros {@code 99}.
     */
    public static int ordenFamilia(String familia) {
        if (familia == null) {
            return 99;
        }
        return switch (familia.trim().toUpperCase(Locale.ROOT)) {
            case "JDK", "JAVA" -> 1;
            case "JAKARTA" -> 2;
            case "SPRING", "SPRING_BOOT", "SPRINGBOOT" -> 3;
            default -> 99;
        };
    }
}
