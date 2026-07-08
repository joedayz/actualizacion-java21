/**
 * Servicio legacy con patrones del ecosistema Spring Boot 2 / Java EE.
 * Punto de partida del laboratorio (sin Maven).
 */
public class LegacyEcosystemService {

    public static void main(String[] args) {
        System.out.println("Import JPA: " + importJpaLegacy());
        System.out.println("Driver JDBC: " + driverJdbc());
        System.out.println("Dialecto Hibernate: " + dialectoHibernate());
        System.out.println("Spring Boot: " + versionSpringBoot());
    }

    static String importJpaLegacy() {
        return "javax.persistence.Entity";
    }

    static String driverJdbc() {
        return "com.mysql.jdbc.Driver";
    }

    static String dialectoHibernate() {
        return "org.hibernate.dialect.MySQL5Dialect";
    }

    static String versionSpringBoot() {
        return "2.7.18";
    }
}
