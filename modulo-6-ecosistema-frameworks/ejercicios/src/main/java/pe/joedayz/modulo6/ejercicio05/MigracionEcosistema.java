package pe.joedayz.modulo6.ejercicio05;

/**
 * Ejercicio 05 — Laboratorio: evaluar dependencias legacy del ecosistema.
 */
public final class MigracionEcosistema {

    private MigracionEcosistema() {}

    public static String clasificarDependencia(String coordenada) {
        if (coordenada.startsWith("javax.") || coordenada.contains("mysql-connector-java")) {
            return "MIGRAR";
        }
        if (coordenada.contains("hibernate-core:5.")) {
            return "REVISAR";
        }
        return "OK";
    }

    public static boolean esDriverJdbcLegacy(String driverClass) {
        return "com.mysql.jdbc.Driver".equals(driverClass);
    }

    public static String versionSpringBootObjetivo() {
        return "3.4.1";
    }
}