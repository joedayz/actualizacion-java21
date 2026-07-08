/**
 * Demo: compatibilidad de drivers JDBC con Java 21.
 * Ejecutar: java JdbcCompatibilidad.java
 */
public class JdbcCompatibilidad {

    public static void main(String[] args) {
        System.out.println("=== Drivers JDBC y Java 21 ===\n");

        System.out.println("Coordenadas Maven actualizadas:");
        System.out.println("  mysql-connector-java     →  mysql-connector-j (com.mysql:mysql-connector-j)");
        System.out.println("  postgresql (42.2.x)      →  postgresql 42.7+");
        System.out.println("  ojdbc8                   →  ojdbc11 (23.x+)");
        System.out.println();

        System.out.println("Driver classes:");
        System.out.println("  com.mysql.jdbc.Driver         →  com.mysql.cj.jdbc.Driver  (legacy)");
        System.out.println("  org.postgresql.Driver         →  OK (mantener)");
        System.out.println();

        System.out.println("Matriz mínima recomendada:");
        String[][] matriz = {
            {"PostgreSQL", "42.7+", "org.postgresql.Driver"},
            {"MySQL", "8.2+", "com.mysql.cj.jdbc.Driver"},
            {"Oracle", "23.x+", "oracle.jdbc.OracleDriver"},
            {"H2", "2.2+", "org.h2.Driver"},
        };
        for (String[] row : matriz) {
            System.out.printf("  %-12s %-8s %s%n", row[0], row[1], row[2]);
        }
        System.out.println();

        System.out.println("Validación en proyecto real:");
        System.out.println("  1. mvn dependency:tree | grep -E 'mysql|postgresql|ojdbc'");
        System.out.println("  2. Probar conexión en CI con Testcontainers + JDK 21");
        System.out.println("  3. Verificar que no queden drivers compilados para Java 8 antiguos");
    }
}
