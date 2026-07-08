/**
 * Demo: cambios clave al pasar de Spring Boot 2 a Spring Boot 3.
 * Ejecutar: java SpringBoot3Cambios.java
 */
public class SpringBoot3Cambios {

    public static void main(String[] args) {
        System.out.println("=== Spring Boot 3 + Java 21 ===\n");

        System.out.println("Requisitos:");
        System.out.println("  - Java 17 mínimo (21 recomendado)");
        System.out.println("  - Spring Boot 3.2+ para Java 21 estable");
        System.out.println("  - Namespace jakarta.* en todo el stack");
        System.out.println();

        System.out.println("Cambios de stack:");
        System.out.println("  Spring Framework 5.x  →  6.x");
        System.out.println("  Hibernate 5.x         →  6.x");
        System.out.println("  javax.*               →  jakarta.*");
        System.out.println("  Tomcat 9 / Servlet 4  →  Tomcat 10+ / Servlet 6");
        System.out.println();

        System.out.println("Propiedades nuevas / útiles (application.yml):");
        System.out.println("  spring.threads.virtual.enabled: true   # virtual threads");
        System.out.println("  spring.jpa.properties.hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect");
        System.out.println();

        System.out.println("Starters que suelen requerir actualización:");
        System.out.println("  springdoc-openapi  →  versión 2.x (compatible SB3)");
        System.out.println("  spring-cloud       →  release train 2023.x+");
        System.out.println("  spring-security    →  6.x (incluido en SB3)");
        System.out.println();

        System.out.println("Checklist:");
        System.out.println("  1. Subir JDK a 21 en CI");
        System.out.println("  2. Actualizar parent a spring-boot-starter-parent 3.x");
        System.out.println("  3. Migrar imports javax → jakarta");
        System.out.println("  4. Revisar propiedades renombradas en application.yml");
        System.out.println("  5. Ejecutar suite de tests + smoke en staging");
    }
}
