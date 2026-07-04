import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.List;

/**
 * Demo: diagnóstico básico con APIs estándar del JDK (sin agentes externos).
 * Ejecutar: java DiagnosticoJvm.java
 */
public class DiagnosticoJvm {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Runtime / PID ===");
        System.out.println("PID (aprox): " + ProcessHandle.current().pid());
        System.out.println("Uptime JVM: " + ManagementFactory.getRuntimeMXBean().getUptime() + " ms");

        System.out.println();
        System.out.println("=== Memoria heap ===");
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        imprimirUsage("heap", memory.getHeapMemoryUsage());
        imprimirUsage("non-heap", memory.getNonHeapMemoryUsage());

        System.out.println();
        System.out.println("=== Threads ===");
        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        System.out.println("Thread count: " + threads.getThreadCount());
        System.out.println("Peak thread count: " + threads.getPeakThreadCount());

        System.out.println();
        System.out.println("=== Input arguments (flags JVM) ===");
        List<String> jvmArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
        if (jvmArgs.isEmpty()) {
            System.out.println("(ninguno — ejecución default)");
        } else {
            jvmArgs.forEach(arg -> System.out.println("  " + arg));
        }

        System.out.println();
        System.out.println("=== Tip: herramientas CLI ===");
        long pid = ProcessHandle.current().pid();
        System.out.println("  jcmd " + pid + " VM.flags");
        System.out.println("  jcmd " + pid + " GC.heap_info");
        System.out.println("  jcmd " + pid + " Thread.print");
        System.out.println("  jstat -gc " + pid + " 1s");
    }

    static void imprimirUsage(String nombre, MemoryUsage usage) {
        System.out.printf("  %s: used=%d KB | committed=%d KB | max=%d KB%n",
                nombre,
                usage.getUsed() / 1024,
                usage.getCommitted() / 1024,
                usage.getMax() > 0 ? usage.getMax() / 1024 : -1);
    }
}
