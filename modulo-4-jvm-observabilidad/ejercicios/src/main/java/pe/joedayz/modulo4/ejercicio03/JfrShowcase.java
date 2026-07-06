package pe.joedayz.modulo4.ejercicio03;


import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class JfrShowcase {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws Exception {

        System.out.println("===== JFR Showcase =====");

        cpuDemo();

        memoryDemo();

        lockDemo();

        fileDemo();

        httpDemo();

        virtualThreadsDemo();

        exceptionDemo();

        System.out.println("Fin.");
    }

    static void cpuDemo() {

        System.out.println("CPU...");

        long sum = 0;

        for (int i = 2; i < 80_000; i++) {

            boolean prime = true;

            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    prime = false;
                    break;
                }
            }

            if (prime)
                sum += i;
        }

        System.out.println(sum);
    }

    static void memoryDemo() {

        System.out.println("Memory...");

        List<byte[]> basura = new ArrayList<>();

        for (int i = 0; i < 500; i++) {

            basura.add(new byte[1024 * 1024]);

            if (i % 50 == 0) {

                System.out.println("MB=" + i);
            }
        }

        basura.clear();

        System.gc();
    }

    static void lockDemo() throws Exception {

        System.out.println("Locks...");

        Runnable r = () -> {

            for (int i = 0; i < 30; i++) {

                synchronized (LOCK) {

                    try {

                        Thread.sleep(30);

                    } catch (InterruptedException ignored) {
                    }
                }
            }
        };

        Thread t1 = Thread.ofPlatform().start(r);
        Thread t2 = Thread.ofPlatform().start(r);

        t1.join();
        t2.join();
    }

    static void fileDemo() throws IOException {

        System.out.println("File IO...");

        Path file = Files.createTempFile("jfr", ".txt");

        Files.writeString(file, "Hola JFR");

        for (int i = 0; i < 200; i++) {

            Files.readString(file);
        }

        Files.deleteIfExists(file);
    }


    static void httpDemo() throws Exception {

        System.out.println("HTTP...");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create("https://example.com"))
                        .timeout(Duration.ofSeconds(5))
                        .build();

        for (int i = 0; i < 5; i++) {

            client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        }
    }

    static void virtualThreadsDemo() throws Exception {

        System.out.println("Virtual Threads...");

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 500; i++) {

            Thread t = Thread.ofVirtual().start(() -> {

                try {

                    Thread.sleep(200);

                } catch (InterruptedException ignored) {
                }

            });

            threads.add(t);
        }

        for (Thread t : threads)
            t.join();
    }

    static void exceptionDemo() {

        System.out.println("Exceptions...");

        for (int i = 0; i < 20; i++) {

            try {

                Integer.parseInt("abc");

            } catch (Exception ignored) {
            }
        }
    }


}
