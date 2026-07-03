import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demo: ExecutorService + CompletableFuture.
 * Ejecutar: java ExecutorCompletableFuture.java
 */
public class ExecutorCompletableFuture {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        try {
            System.out.println("=== supplyAsync + thenApply + thenCompose ===");
            String resultado = CompletableFuture
                    .supplyAsync(() -> fetchUser(42), pool)
                    .thenApply(user -> user.toUpperCase())
                    .thenCompose(user -> fetchOrderCount(user, pool))
                    .thenApply(count -> "Usuario procesado, pedidos=" + count)
                    .get(3, TimeUnit.SECONDS);
            System.out.println(resultado);

            System.out.println();
            System.out.println("=== thenCombine: dos llamadas en paralelo ===");
            CompletableFuture<Double> precio = CompletableFuture.supplyAsync(() -> precioProducto("SKU-1"), pool);
            CompletableFuture<Double> igv = CompletableFuture.supplyAsync(() -> tasaIgv(), pool);
            double total = precio.thenCombine(igv, (p, i) -> p * (1 + i)).get(3, TimeUnit.SECONDS);
            System.out.println("Total con IGV: " + total);

            System.out.println();
            System.out.println("=== exceptionally: recuperación de error ===");
            String fallback = CompletableFuture
                    .supplyAsync(() -> {
                        throw new RuntimeException("servicio caído");
                    }, pool)
                    .exceptionally(ex -> "valor-por-defecto")
                    .get(3, TimeUnit.SECONDS).toString();
            System.out.println(fallback);
        } finally {
            pool.shutdown();
        }
    }

    static String fetchUser(int id) {
        dormir(100);
        return "user-" + id;
    }

    static CompletableFuture<Integer> fetchOrderCount(String user, ExecutorService pool) {
        return CompletableFuture.supplyAsync(() -> {
            dormir(100);
            return user.length();
        }, pool);
    }

    static double precioProducto(String sku) {
        dormir(80);
        return 100.0;
    }

    static double tasaIgv() {
        dormir(80);
        return 0.18;
    }

    static void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
