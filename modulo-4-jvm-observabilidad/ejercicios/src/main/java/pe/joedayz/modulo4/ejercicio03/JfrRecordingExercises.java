package pe.joedayz.modulo4.ejercicio03;

import java.nio.file.Path;
import java.time.Duration;

/**
 * Ejercicio 03 — Grabación programática con Java Flight Recorder.
 */
public final class JfrRecordingExercises {

    private JfrRecordingExercises() {}

    /**
     * Graba eventos JFR durante {@code duracion} y escribe el archivo en {@code destino}.
     *
     * <p>Habilita al menos: {@code jdk.GCPhasePause} y {@code jdk.ExecutionSample}.
     * El archivo resultante debe existir y tener tamaño &gt; 0.
     */
    public static void grabar(Path destino, Duration duracion) throws Exception {
        // TODO: usar jdk.jfr.Recording
        throw new UnsupportedOperationException("TODO: grabar");
    }
}
