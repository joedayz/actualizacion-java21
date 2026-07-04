public class MillonPlatformThreads {

    public static void main(String[] args) throws Exception {

        Thread[] threads = new Thread[1_000_000];

        long start = System.currentTimeMillis();

        try {
            for (int i = 0; i < threads.length; i++) {
                final int id = i;

                threads[i] = new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }

                    if (id % 100_000 == 0) {
                        System.out.println("Platform Thread " + id +
                                " -> " + Thread.currentThread());
                    }
                });

                threads[i].start();
            }

            for (Thread thread : threads) {
                if (thread != null) {
                    thread.join();
                }
            }

            System.out.println("Se ejecutaron " + threads.length + " hilos.");

        } catch (Throwable e) {
            System.err.println("Error al crear el hilo:");
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Tiempo: " + (end - start) + " ms");


    }
}
