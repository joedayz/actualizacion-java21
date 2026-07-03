import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Código legado: pool fijo grande para I/O bloqueante.
 * Punto de partida del laboratorio (sin Maven).
 */
public class LegacyConcurrentService {

    public static void main(String[] args) throws Exception {
        List<Integer> ids = List.of(1, 2, 3, 4, 5);
        System.out.println(consultarServicios(ids));
    }

    static List<Integer> consultarServicios(List<Integer> ids) throws Exception {
        // Anti-patrón típico pre-Loom: inflar el pool para "soportar más I/O"
        ExecutorService pool = Executors.newFixedThreadPool(200);
        try {
            List<Future<Integer>> futures = new ArrayList<>();
            for (Integer id : ids) {
                futures.add(pool.submit(() -> {
                    Thread.sleep(50); // simula I/O
                    return id * 10;
                }));
            }
            List<Integer> resultados = new ArrayList<>();
            for (Future<Integer> future : futures) {
                resultados.add(future.get());
            }
            return resultados;
        } finally {
            pool.shutdown();
        }
    }
}
