import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Demo: APIs removidas/deprecadas relevantes en la migración Java 8 → 21.
 * Ejecutar: java ApisCambiadas.java
 */
public class ApisCambiadas {

    public static void main(String[] args) {
        System.out.println("=== JDK " + System.getProperty("java.version") + " ===");
        System.out.println();

        System.out.println("Removidos del JDK (usar dependencias externas):");
        System.out.println("  - javax.xml.bind (JAXB)     → jakarta.xml.bind-api");
        System.out.println("  - javax.annotation          → jakarta.annotation-api");
        System.out.println("  - Java EE completo          → Jakarta EE / Spring Boot 3");
        System.out.println("  - Nashorn (JS en JVM)       → GraalJS u otro motor");
        System.out.println("  - CORBA                     → sin reemplazo en JDK");
        System.out.println();

        System.out.println("Deprecados / en retirada:");
        System.out.println("  - SecurityManager           → removido en Java 24");
        System.out.println("  - Object.finalize()         → usar Cleaner / try-with-resources");
        System.out.println("  - Thread.stop/suspend       → interrupción cooperativa");
        System.out.println();

        System.out.println("Encapsulación JPMS (Java 17+):");
        System.out.println("  - Reflection a sun.* / jdk.internal.* → InaccessibleObjectException");
        System.out.println("  - Migración: --add-opens puntual o actualizar librerías");
        System.out.println();

        System.out.println("TLS:");
        System.out.println("  - TLS 1.0 / 1.1 deshabilitados por defecto");
        System.out.println("  - Actualizar servidores remotos a TLS 1.2+");


        ScriptEngine engine =
                new ScriptEngineManager().getEngineByName("JavaScript");
        System.out.println();
        System.out.println(engine == null
                ? "Nashorn NO disponible"
                : "Nashorn disponible");

        System.out.println();
        System.out.println(System.getSecurityManager());
    }
}
