package pe.joedayz.modulo6.ejercicio05;

/**
 * Ejercicio 05 — Laboratorio: evaluar dependencias legacy del ecosistema.
 */
public final class MigracionEcosistema {

    private MigracionEcosistema() {}

    /**
     * Clasifica una coordenada Maven para migración a Java 21 + Spring Boot 3.
     *
     * @return {@code "OK"}, {@code "MIGRAR"} o {@code "REVISAR"}
     */
    public static String clasificarDependencia(String coordenada) {
        // TODO
        throw new UnsupportedOperationException("TODO: clasificarDependencia");
    }

    /**
     * {@code true} si la clase de driver JDBC es legacy (ej. {@code com.mysql.jdbc.Driver}).
     */
    public static boolean esDriverJdbcLegacy(String driverClass) {
        // TODO
        throw new UnsupportedOperationException("TODO: esDriverJdbcLegacy");
    }

    /**
     * Versión objetivo de Spring Boot para el laboratorio (stack moderno).
     */
    public static String versionSpringBootObjetivo() {
        // TODO
        throw new UnsupportedOperationException("TODO: versionSpringBootObjetivo");
    }
}
