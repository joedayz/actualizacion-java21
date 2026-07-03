import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Solución de referencia del laboratorio (no repartir al inicio).
 * Misma lógica de negocio, executor de virtual threads.
 */
public class SolucionVirtualThreads {

    public static void main(String[] args) throws Exception {
        List<Integer> ids = List.of(1, 2, 3, 4, 5);
        System.out.println(consultarServicios(ids));
    }

    static List<Integer> consultarServicios(List<Integer> ids) throws Exception {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();
            for (Integer id : ids) {
                futures.add(executor.submit(() -> {
                    Thread.sleep(50);
                    return id * 10;
                }));
            }
            List<Integer> resultados = new ArrayList<>();
            for (Future<Integer> future : futures) {
                resultados.add(future.get());
            }
            return resultados;
        }
    }
}
