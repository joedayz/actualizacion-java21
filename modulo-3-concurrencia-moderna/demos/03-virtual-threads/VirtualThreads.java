import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demo: Virtual Threads (Project Loom, final en Java 21).
 * Ejecutar: java VirtualThreads.java
 *
 * Compara un pool fijo de platform threads vs un executor de virtual threads
 * para muchas tareas con I/O bloqueante simulado.
 */
public class VirtualThreads {

    static final int TAREAS = 5_000;
    static final long BLOQUEO_MS = 50;

    public static void main(String[] args) throws Exception {
        System.out.println("Tareas=" + TAREAS + " bloqueo=" + BLOQUEO_MS + "ms cada una");
        System.out.println();

        System.out.println("=== Platform threads: FixedThreadPool(200) ===");
        medir(() -> correrCon(Executors.newFixedThreadPool(200)));

        System.out.println();
        System.out.println("=== Virtual threads: newVirtualThreadPerTaskExecutor() ===");
        medir(() -> correrCon(Executors.newVirtualThreadPerTaskExecutor()));

        System.out.println();
        System.out.println("=== Crear un VT suelto ===");
        Thread vt = Thread.ofVirtual().name("demo-vt").start(() -> {
            System.out.println("Hola desde " + Thread.currentThread()
                    + " virtual=" + Thread.currentThread().isVirtual());
        });
        vt.join();
    }

    static void medir(ThrowingRunnable accion) throws Exception {
        Instant inicio = Instant.now();
        accion.run();
        System.out.println("tiempo=" + Duration.between(inicio, Instant.now()).toMillis() + " ms");
    }

    static void correrCon(ExecutorService executor) throws Exception {
        try (executor) {
            AtomicInteger virtuales = new AtomicInteger();
            @SuppressWarnings("unchecked")
            Future<?>[] futures = new Future<?>[TAREAS];
            for (int i = 0; i < TAREAS; i++) {
                futures[i] = executor.submit(() -> {
                    if (Thread.currentThread().isVirtual()) {
                        virtuales.incrementAndGet();
                    }
                    Thread.sleep(BLOQUEO_MS);
                    return null;
                });
            }
            for (Future<?> future : futures) {
                future.get();
            }
            System.out.println("tareas en virtual threads: " + virtuales.get() + "/" + TAREAS);
        }
    }

    @FunctionalInterface
    interface ThrowingRunnable {
        void run() throws Exception;
    }
}
