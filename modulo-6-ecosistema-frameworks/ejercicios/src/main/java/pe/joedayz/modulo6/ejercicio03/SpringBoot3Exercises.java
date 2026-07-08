package pe.joedayz.modulo6.ejercicio03;

/**
 * Ejercicio 03 — Spring Boot 3 y compatibilidad con Java 21.
 */
public final class SpringBoot3Exercises {

    private SpringBoot3Exercises() {}

    /**
     * {@code true} si la versión de Spring Boot soporta Java 21 de forma estable (3.2.0+).
     */
    public static boolean soportaJava21(String springBootVersion) {
        return compareVersions(springBootVersion, "3.2.0") >= 0;
    }



    /**
     * {@code true} si la versión usa el namespace jakarta (Spring Boot 3.0.0+).
     */
    public static boolean usaJakarta(String springBootVersion) {
        return compareVersions(springBootVersion, "3.0.0") >= 0;
    }

    /**
     * Versión mínima de Java requerida: {@code "8"} para Spring Boot 2.x, {@code "17"} para 3.x.
     */
    public static String versionMinimaJava(String springBootVersion) {
        return compareVersions(springBootVersion, "3.0.0") >= 0 ? "17" : "8";
    }

    private static int compareVersions(String left, String right) {
        String[] leftParts = left.split("\\.");
        String[] rightParts = right.split("\\.");
        int length = Math.max(leftParts.length, rightParts.length);
        for (int i = 0; i < length; i++) {
            int leftPart = i < leftParts.length ? Integer.parseInt(leftParts[i]) : 0;
            int rightPart = i < rightParts.length ? Integer.parseInt(rightParts[i]) : 0;
            if (leftPart != rightPart) {
                return Integer.compare(leftPart, rightPart);
            }
        }
        return 0;

    }
}
