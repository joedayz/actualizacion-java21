import java.util.Comparator;

/**
 * Demo: módulos cargados en la JVM actual (JPMS en runtime).
 * Ejecutar: java ModulosEnRuntime.java
 */
public class ModulosEnRuntime {

    public static void main(String[] args) {
        System.out.println("=== Módulo actual ===");
        Module mod = ModulosEnRuntime.class.getModule();
        System.out.println("Nombre: " + (mod.isNamed() ? mod.getName() : "(unnamed)"));
        System.out.println("Descriptor: " + mod.getDescriptor());

        System.out.println();
        System.out.println("=== Módulos en ModuleLayer.boot() (primeros 20) ===");
        ModuleLayer.boot().modules().stream()
                .filter(Module::isNamed)
                .map(Module::getName)
                .sorted(Comparator.naturalOrder())
                .limit(20)
                .forEach(name -> System.out.println("  " + name));

        long total = ModuleLayer.boot().modules().stream().filter(Module::isNamed).count();
        System.out.println("... total módulos nombrados: " + total);

        System.out.println();
        System.out.println("=== ¿java.sql exportado por java.sql? ===");
        Module sql = ModuleLayer.boot().findModule("java.sql").orElseThrow();
        System.out.println("java.sql exporta java.sql: " + sql.isExported(java.sql.Connection.class.getPackageName()));

        System.out.println();
        System.out.println("=== Tip ===");
        System.out.println("Tu app en classpath vive en el 'unnamed module'.");
        System.out.println("El JDK está modularizado aunque tu JAR no tenga module-info.java.");
    }
}
