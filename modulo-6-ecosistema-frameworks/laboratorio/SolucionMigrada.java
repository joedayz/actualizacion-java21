/**
 * Referencia del laboratorio — stack alineado con Java 21 + Spring Boot 3.
 */
public class SolucionMigrada {

    public static void main(String[] args) {
        System.out.println("Import JPA: " + importJpaModerno());
        System.out.println("Driver JDBC: " + driverJdbcModerno());
        System.out.println("Dialecto Hibernate: " + dialectoHibernate6());
        System.out.println("Spring Boot: " + versionSpringBootObjetivo());
        System.out.println("Clasificación javax.persistence-api: "
                + clasificar("javax.persistence:javax.persistence-api"));
    }

    static String importJpaModerno() {
        return "jakarta.persistence.Entity";
    }

    static String driverJdbcModerno() {
        return "com.mysql.cj.jdbc.Driver";
    }

    static String dialectoHibernate6() {
        return "org.hibernate.dialect.MySQLDialect";
    }

    static String versionSpringBootObjetivo() {
        return "3.4.1";
    }

    static String clasificar(String coordenada) {
        if (coordenada.startsWith("javax.") || coordenada.contains("mysql-connector-java")) {
            return "MIGRAR";
        }
        if (coordenada.contains("hibernate-core:5.")) {
            return "REVISAR";
        }
        return "OK";
    }
}
