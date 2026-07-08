/**
 * Demo: mapeo javax → jakarta en la migración a Spring Boot 3.
 * Ejecutar: java MigracionJakarta.java
 */
public class MigracionJakarta {

    public static void main(String[] args) {
        System.out.println("=== Migración javax → jakarta ===\n");

        String[][] migraciones = {
            {"javax.persistence.Entity", "jakarta.persistence.Entity"},
            {"javax.servlet.http.HttpServlet", "jakarta.servlet.http.HttpServlet"},
            {"javax.validation.constraints.NotNull", "jakarta.validation.constraints.NotNull"},
            {"javax.annotation.PostConstruct", "jakarta.annotation.PostConstruct"},
            {"javax.xml.bind.JAXBContext", "jakarta.xml.bind.JAXBContext"},
            {"javax.inject.Inject", "jakarta.inject.Inject"},
            {"javax.ws.rs.GET", "jakarta.ws.rs.GET"},
            {"javax.transaction.Transactional", "jakarta.transaction.Transactional"},
        };

        System.out.println("Paquetes que MIGRAN:");
        for (String[] par : migraciones) {
            System.out.printf("  %-45s → %s%n", par[0], par[1]);
        }

        System.out.println("\nPaquetes javax que NO migran (permanecen en el JDK):");
        System.out.println("  javax.crypto.*, javax.net.*, javax.sql.*, javax.naming.*");

        System.out.println("\nDependencias Maven típicas:");
        System.out.println("  javax.persistence-api  →  jakarta.persistence-api");
        System.out.println("  javax.servlet-api      →  jakarta.servlet-api");
        System.out.println("  validation-api         →  jakarta.validation-api");

        System.out.println("\nHerramientas: OpenRewrite, IntelliJ Migrate to Jakarta, Spring Boot Migrator");
    }
}
