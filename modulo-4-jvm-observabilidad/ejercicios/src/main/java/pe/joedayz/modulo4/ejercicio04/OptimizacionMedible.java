package pe.joedayz.modulo4.ejercicio04;

/**
 * Ejercicio 04 — Comparar implementación naive vs optimizada.
 *
 * <p>Para medir rendimiento con JMH, usa la demo en {@code demos/03-jmh-benchmark/}.
 */
public final class OptimizacionMedible {

    private OptimizacionMedible() {}

    /**
     * Concatena enteros de 0 a {@code n - 1} usando {@code +=} en bucle (anti-patrón).
     */
    public static String concatenarNaive(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += i;
        }
        return result;
    }

    /**
     * Misma salida que {@link #concatenarNaive(int)} pero sin recrear strings en cada iteración.
     */
    public static String concatenarOptimizado(int n) {
        // TODO: StringBuilder u otra estrategia eficiente
        throw new UnsupportedOperationException("TODO: concatenarOptimizado");
    }
}
