import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo: información de JVM y garbage collectors activos.
 * Ejecutar: java GcOverview.java
 *
 * Tip: comparar salida con distintos GC:
 *   java -XX:+UseG1GC GcOverview.java
 *   java -XX:+UseZGC GcOverview.java
 */
public class GcOverview {

    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();

        System.out.println("=== JVM ===");
        System.out.println("java.version = " + System.getProperty("java.version"));
        System.out.println("java.vm.name = " + System.getProperty("java.vm.name"));
        System.out.println("availableProcessors = " + runtime.availableProcessors());

        System.out.println();
        System.out.println("=== Memoria (antes de allocations) ===");
        imprimirMemoria(runtime);

        System.out.println();
        System.out.println("=== Garbage Collectors registrados ===");
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            System.out.printf("  %s | colecciones=%d | tiempo=%d ms%n",
                    gc.getName(), gc.getCollectionCount(), gc.getCollectionTime());
        }

        System.out.println();
        System.out.println("=== Simulando presión de allocations ===");
        List<byte[]> basura = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            basura.add(new byte[512 * 1024]); // 512 KB por iteración
            if (i % 10 == 9) {
                System.out.println("  iteración " + (i + 1) + " — heap usado ~"
                        + (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024) + " MB");
            }
        }
        basura.clear();
        System.gc(); // solo sugerencia; en clase comentar que no se usa en producción

        System.out.println();
        System.out.println("=== Memoria (después de GC sugerido) ===");
        imprimirMemoria(runtime);
    }

    static void imprimirMemoria(Runtime runtime) {
        long max = runtime.maxMemory();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long used = total - free;
        System.out.printf("  max=%d MB | total=%d MB | used=%d MB | free=%d MB%n",
                max / (1024 * 1024), total / (1024 * 1024), used / (1024 * 1024), free / (1024 * 1024));
    }
}
