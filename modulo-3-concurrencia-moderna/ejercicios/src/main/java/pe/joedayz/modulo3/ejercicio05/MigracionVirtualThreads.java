package pe.joedayz.modulo3.ejercicio05;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Ejercicio 05 — Laboratorio: migrar de pool fijo a Virtual Threads.
 *
 * <p>El flujo de negocio ya usa {@link #crearExecutor()}. Solo debes cambiar
 * la implementación de {@code crearExecutor()} para devolver un executor de
 * virtual threads en lugar del pool fijo de platform threads.
 *
 * <p>Lógica de negocio: para cada id, dormir 20ms (I/O simulado) y devolver {@code id * 10},
 * en el mismo orden de entrada.
 */
public final class MigracionVirtualThreads {

    private MigracionVirtualThreads() {}

    /**
     * TODO: reemplazar el pool fijo por
     * {@code Executors.newVirtualThreadPerTaskExecutor()}.
     */
    public static ExecutorService crearExecutor() {
        // Legado: pool grande de platform threads para "aguantar" I/O
        return Executors.newFixedThreadPool(200);
    }

    /**
     * Consulta "servicios" externos en paralelo usando {@link #crearExecutor()}.
     */
    public static List<Integer> consultarServicios(List<Integer> ids) throws Exception {
        try (ExecutorService executor = crearExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();
            for (Integer id : ids) {
                futures.add(executor.submit(() -> consultarUno(id)));
            }
            List<Integer> resultados = new ArrayList<>();
            for (Future<Integer> future : futures) {
                resultados.add(future.get());
            }
            return resultados;
        }
    }

    /** Simula I/O bloqueante. No modificar. */
    public static int consultarUno(int id) throws InterruptedException {
        Thread.sleep(20);
        return id * 10;
    }
}
