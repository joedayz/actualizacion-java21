package pe.joedayz.modulo4.ejercicio01;

import java.util.List;

/**
 * Ejercicio 01 — Elegir flags de JVM según el perfil de la aplicación.
 */
public final class GcFlagsExercises {

    private GcFlagsExercises() {}

    public enum PerfilGc {
        /** Servicios con requisitos estrictos de latencia (P99). */
        LATENCIA,
        /** Procesamiento batch donde importa más throughput que pausas cortas. */
        THROUGHPUT,
        /** Entornos con RAM limitada; heap fijo y predecible. */
        MEMORIA_RESTRINGIDA
    }

    /**
     * Devuelve flags JVM recomendados (sin el prefijo {@code java}).
     *
     * <ul>
     *   <li>{@code LATENCIA}: activar ZGC y fijar heap mínimo/máximo al valor dado.</li>
     *   <li>{@code THROUGHPUT}: activar G1GC con el heap indicado.</li>
     *   <li>{@code MEMORIA_RESTRINGIDA}: G1GC con {@code -Xms} = {@code -Xmx} = heapMb.</li>
     * </ul>
     *
     * <p>Cada flag es un elemento de la lista, por ejemplo {@code "-XX:+UseZGC"}.
     * Incluye {@code -Xms<N>m} y {@code -Xmx<N>m} con el {@code heapMb} recibido.
     */
    public static List<String> flagsRecomendados(PerfilGc perfil, int heapMb) {
        // TODO: implementar según perfil
        throw new UnsupportedOperationException("TODO: flagsRecomendados");
    }
}
