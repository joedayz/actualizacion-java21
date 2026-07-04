package pe.joedayz.modulo4.ejercicio02;

/**
 * Ejercicio 02 — Interpretar líneas de unified GC logging (G1).
 */
public final class GcLogParser {

    private GcLogParser() {}

    /**
     * Extrae la duración de pausa en milisegundos de una línea de log G1.
     *
     * <p>Ejemplo de entrada:
     * {@code [0.456s][info][gc] GC(12) Pause Young (Normal) (G1 Evacuation Pause) 24M->8M(64M) 3.456ms}
     *
     * @return pausa en ms (ej. 3.456) o {@code null} si la línea no contiene pausa
     */
    public static Double extraerPausaMs(String linea) {
        // TODO: buscar el token que termina en "ms" al final de la línea
        throw new UnsupportedOperationException("TODO: extraerPausaMs");
    }

    /**
     * Indica si la línea corresponde a un evento de Full GC
     * (contiene "Full GC" o "Pause Full").
     */
    public static boolean esFullGc(String linea) {
        // TODO
        throw new UnsupportedOperationException("TODO: esFullGc");
    }

    /**
     * Extrae el uso de heap "antes->después" en MB desde una línea G1.
     *
     * <p>Para {@code 24M->8M(64M)} devuelve {@code [24, 8]}.
     *
     * @return array de 2 enteros [antesMb, despuesMb] o {@code null} si no parsea
     */
    public static int[] extraerHeapAntesDespuesMb(String linea) {
        // TODO: regex o parsing sobre el patrón NNM->NNM
        throw new UnsupportedOperationException("TODO: extraerHeapAntesDespuesMb");
    }
}
