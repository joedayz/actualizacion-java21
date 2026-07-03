package pe.joedayz.modulo3.ejercicio01;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Ejercicio 01 — Revisión de concurrencia clásica (ExecutorService).
 */
public final class ConcurrenciaClasica {

    private ConcurrenciaClasica() {}

    /**
     * Ejecuta todas las tareas en un {@code ExecutorService} con pool fijo de
     * {@code poolSize} platform threads y devuelve los resultados <strong>en el mismo orden</strong>
     * de la lista de entrada.
     *
     * <p>Debes cerrar el pool al terminar (try-with-resources o {@code shutdown}).
     */
    public static <T> List<T> ejecutarTodas(List<Callable<T>> tareas, int poolSize) throws Exception {
        // TODO: Executors.newFixedThreadPool(poolSize) + submit + get en orden
        throw new UnsupportedOperationException("TODO: ejecutarTodas");
    }

    /**
     * Suma todos los enteros usando el pool. Puede repartir el trabajo en tareas
     * (por ejemplo, una Callable por número) o una sola reducción; el resultado debe ser la suma total.
     */
    public static int sumarEnParalelo(List<Integer> numeros, int poolSize) throws Exception {
        // TODO: reutiliza ejecutarTodas o implementa con ExecutorService
        throw new UnsupportedOperationException("TODO: sumarEnParalelo");
    }
}
