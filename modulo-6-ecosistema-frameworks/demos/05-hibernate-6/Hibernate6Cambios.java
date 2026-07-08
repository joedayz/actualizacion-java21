/**
 * Demo: cambios principales de Hibernate 5 → 6 (con Spring Boot 3).
 * Ejecutar: java Hibernate6Cambios.java
 */
public class Hibernate6Cambios {

    public static void main(String[] args) {
        System.out.println("=== Hibernate 6 ===\n");

        System.out.println("Namespace:");
        System.out.println("  javax.persistence.*  →  jakarta.persistence.*");
        System.out.println();

        System.out.println("Dialectos (ejemplos):");
        String[][] dialectos = {
            {"org.hibernate.dialect.MySQL5Dialect", "org.hibernate.dialect.MySQLDialect"},
            {"org.hibernate.dialect.MySQL8Dialect", "org.hibernate.dialect.MySQLDialect"},
            {"org.hibernate.dialect.PostgreSQL95Dialect", "org.hibernate.dialect.PostgreSQLDialect"},
            {"org.hibernate.dialect.Oracle12cDialect", "org.hibernate.dialect.OracleDialect"},
        };
        for (String[] d : dialectos) {
            System.out.printf("  %-45s → %s%n", d[0], d[1]);
        }
        System.out.println();

        System.out.println("Propiedades eliminadas / innecesarias en Hibernate 6:");
        System.out.println("  hibernate.id.new_generator_mappings");
        System.out.println("  hibernate.jdbc.lob.non_contextual_creation");
        System.out.println();

        System.out.println("Errores típicos post-migración:");
        System.out.println("  ClassNotFoundException: javax.persistence.Entity");
        System.out.println("    → código o librería sin migrar a jakarta");
        System.out.println("  Unknown entity / wrong dialect");
        System.out.println("    → actualizar dialecto y revisar auto-ddl");
        System.out.println();

        System.out.println("Con Spring Boot 3: Hibernate 6 viene por defecto.");
        System.out.println("No mezclar dependencias hibernate-core 5.x con Spring Boot 3.");
    }
}
