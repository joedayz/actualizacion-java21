package pe.joedayz.modulo3.ejercicio03;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * Ejercicio 03 — Virtual Threads (Project Loom).
 */
public final class VirtualThreadsExercises {

    private VirtualThreadsExercises() {}

    /**
     * Crea y devuelve un {@link ExecutorService} de <strong>virtual threads</strong>
     * ({@code Executors.newVirtualThreadPerTaskExecutor()}).
     */
    public static ExecutorService crearExecutorVirtual() {
        // TODO
        throw new UnsupportedOperationException("TODO: crearExecutorVirtual");
    }

    /**
     * Ejecuta la tarea en un virtual thread y devuelve su resultado.
     * Puedes usar {@link Thread#ofVirtual()} o el executor virtual.
     */
    public static <T> T ejecutarEnVirtual(Callable<T> tarea) throws Exception {
        // TODO
        throw new UnsupportedOperationException("TODO: ejecutarEnVirtual");
    }

    /**
     * Indica si el hilo que ejecuta la tarea es virtual.
     * Debe correr la comprobación <em>dentro</em> de un virtual thread.
     */
    public static boolean correEnVirtualThread() throws Exception {
        // TODO: ejecutar Thread.currentThread().isVirtual() dentro de un VT
        throw new UnsupportedOperationException("TODO: correEnVirtualThread");
    }

    /**
     * Aplica {@code trabajo} a cada entrada en un virtual thread y devuelve
     * los resultados <strong>en el mismo orden</strong> de {@code entradas}.
     */
    public static List<String> procesarEnVirtuales(
            List<String> entradas,
            Function<String, String> trabajo) throws Exception {
        // TODO: newVirtualThreadPerTaskExecutor + submit por elemento
        throw new UnsupportedOperationException("TODO: procesarEnVirtuales");
    }
}
