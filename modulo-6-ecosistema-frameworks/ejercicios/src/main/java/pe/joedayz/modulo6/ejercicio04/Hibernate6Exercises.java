package pe.joedayz.modulo6.ejercicio04;

import java.util.Map;
import java.util.Set;

/**
 * Ejercicio 04 — Migración a Hibernate 6.
 */
public final class Hibernate6Exercises {

    private Hibernate6Exercises() {}

    private static final Map<String, String> DIALECTOS = Map.of(
            "org.hibernate.dialect.MySQL5Dialect", "org.hibernate.dialect.MySQLDialect",
            "org.hibernate.dialect.MySQL8Dialect", "org.hibernate.dialect.MySQLDialect",
            "org.hibernate.dialect.PostgreSQL95Dialect", "org.hibernate.dialect.PostgreSQLDialect",
            "org.hibernate.dialect.Oracle12cDialect", "org.hibernate.dialect.OracleDialect");

    private static final Set<String> DEPRECADAS = Set.of(
            "hibernate.id.new_generator_mappings",
            "hibernate.jdbc.lob.non_contextual_creation");

    public static String migrarDialecto(String dialectoH5) {
        return DIALECTOS.getOrDefault(dialectoH5, dialectoH5);
    }

    public static boolean esPropiedadDeprecada(String propiedad) {
        return DEPRECADAS.contains(propiedad);
    }

    public static String namespaceJpa() {
        return "jakarta.persistence";
    }
}