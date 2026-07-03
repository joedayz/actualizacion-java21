package pe.joedayz.modulo3.ejercicio02;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

/**
 * Ejercicio 02 — CompletableFuture.
 */
public final class FuturesModernos {

    private FuturesModernos() {}

    /**
     * Suma {@code a + b} de forma asíncrona usando el {@code executor} dado
     * ({@code supplyAsync}).
     */
    public static CompletableFuture<Integer> sumarAsync(int a, int b, Executor executor) {
        // TODO
        throw new UnsupportedOperationException("TODO: sumarAsync");
    }

    /**
     * Encadena: toma el resultado de {@code origen}, le aplica {@code transform}
     * de forma asíncrona en {@code executor} ({@code thenApplyAsync}).
     */
    public static CompletableFuture<String> transformar(
            CompletableFuture<String> origen,
            Function<String, String> transform,
            Executor executor) {
        // TODO
        throw new UnsupportedOperationException("TODO: transformar");
    }

    /**
     * Combina dos futures sumando sus enteros ({@code thenCombine}).
     */
    public static CompletableFuture<Integer> combinar(
            CompletableFuture<Integer> izquierda,
            CompletableFuture<Integer> derecha) {
        // TODO
        throw new UnsupportedOperationException("TODO: combinar");
    }

    /**
     * Si {@code origen} falla, devuelve {@code valorPorDefecto}
     * ({@code exceptionally}).
     */
    public static CompletableFuture<String> conFallback(
            CompletableFuture<String> origen,
            String valorPorDefecto) {
        // TODO
        throw new UnsupportedOperationException("TODO: conFallback");
    }
}
