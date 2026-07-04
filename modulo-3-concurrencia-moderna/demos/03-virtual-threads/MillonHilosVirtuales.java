import java.util.concurrent.Executors;

public class MillonHilosVirtuales {



    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 0; i < 1_000_000; i++) {
                final int id = i;

                executor.submit(() -> {
                    // Simula trabajo bloqueante
                    Thread.sleep(1000);

                    // Para no imprimir un millón de líneas
                    if (id % 100_000 == 0) {
                        System.out.println("Virtual Thread " + id +
                                " -> " + Thread.currentThread());
                    }

                    return null;
                });
            }

        } // Espera automáticamente que todas las tareas finalicen

        long end = System.currentTimeMillis();

        System.out.println("\nFinalizado en " + (end - start) + " ms");
    }
}
