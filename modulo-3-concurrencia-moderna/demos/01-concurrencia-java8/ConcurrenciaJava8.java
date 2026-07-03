import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Demo: concurrencia clásica estilo Java 8.
 * Ejecutar: java ConcurrenciaJava8.java
 */
public class ConcurrenciaJava8 {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Platform thread actual ===");
        Thread actual = Thread.currentThread();
        System.out.println("nombre=" + actual.getName()
                + " virtual=" + actual.isVirtual());

        System.out.println();
        System.out.println("=== Pool fijo de 3 workers (platform threads) ===");
        ExecutorService pool = Executors.newFixedThreadPool(3);
        try {
            List<Callable<String>> tareas = List.of(
                    () -> trabajo("A", 200),
                    () -> trabajo("B", 200),
                    () -> trabajo("C", 200),
                    () -> trabajo("D", 200)
            );

            List<Future<String>> futures = new ArrayList<>();
            for (Callable<String> tarea : tareas) {
                futures.add(pool.submit(tarea));
            }

            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } finally {
            pool.shutdown();
        }

        System.out.println();
        System.out.println("Observación: solo 3 tareas corren a la vez; la 4ª espera un worker libre.");
        System.out.println("Cada worker es un platform thread (caro si el pool crece a cientos).");
    }

    static String trabajo(String id, long millis) throws InterruptedException {
        Thread t = Thread.currentThread();
        Thread.sleep(millis); // simula I/O bloqueante
        return "tarea " + id + " en " + t.getName() + " (virtual=" + t.isVirtual() + ")";
    }
}
