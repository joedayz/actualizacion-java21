package pe.joedayz.modulo3.ejercicio04;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Ejercicio 04 — Structured Concurrency y Scoped Values (preview en Java 21).
 *
 * <p>El {@code pom.xml} ya activa {@code --enable-preview}.
 */
public final class StructuredConcurrencyExercises {

    /** Scoped value para el id de request. */
    public static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

    private StructuredConcurrencyExercises() {}

    /**
     * Ejecuta {@code usuario} y {@code pedido} en paralelo con
     * {@link java.util.concurrent.StructuredTaskScope.ShutdownOnFailure}.
     * Devuelve {@code "<usuario>|<pedido>"} (ej. {@code "ana|orden-1"}).
     *
     * <p>Si alguna subtarea falla, la excepción debe propagarse
     * ({@code join} + {@code throwIfFailed}).
     */
    public static String cargarUsuarioYPedido(
            Callable<String> usuario,
            Callable<String> pedido) throws Exception {
        // TODO: StructuredTaskScope.ShutdownOnFailure
        throw new UnsupportedOperationException("TODO: cargarUsuarioYPedido");
    }

    /**
     * Bind de {@link #REQUEST_ID} con {@code requestId} y ejecuta {@code trabajo}.
     * Dentro de {@code trabajo}, {@code REQUEST_ID.get()} debe devolver ese id.
     */
    public static String conRequestId(String requestId, Supplier<String> trabajo) {
        // TODO: ScopedValue.where(REQUEST_ID, requestId).call/run
        throw new UnsupportedOperationException("TODO: conRequestId");
    }

    /**
     * Igual que {@link #cargarUsuarioYPedido}, pero ambas subtareas deben poder
     * leer {@link #REQUEST_ID} (ya binded por el caller con {@link #conRequestId}).
     * Devuelve {@code "<requestId>:<usuario>|<pedido>"}.
     */
    public static String cargarConContexto(
            Callable<String> usuario,
            Callable<String> pedido) throws Exception {
        // TODO: scope + anteponer REQUEST_ID.get() + ":"
        throw new UnsupportedOperationException("TODO: cargarConContexto");
    }
}
