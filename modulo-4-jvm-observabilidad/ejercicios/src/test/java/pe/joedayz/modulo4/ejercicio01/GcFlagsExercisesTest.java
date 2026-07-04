package pe.joedayz.modulo4.ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 01 — Flags de GC")
class GcFlagsExercisesTest {

    @Test
    void perfilLatenciaUsaZgc() {
        List<String> flags = GcFlagsExercises.flagsRecomendados(GcFlagsExercises.PerfilGc.LATENCIA, 512);
        assertTrue(flags.contains("-XX:+UseZGC"));
        assertTrue(flags.contains("-Xms512m"));
        assertTrue(flags.contains("-Xmx512m"));
    }

    @Test
    void perfilThroughputUsaG1() {
        List<String> flags = GcFlagsExercises.flagsRecomendados(GcFlagsExercises.PerfilGc.THROUGHPUT, 1024);
        assertTrue(flags.contains("-XX:+UseG1GC"));
        assertTrue(flags.contains("-Xms1024m"));
        assertTrue(flags.contains("-Xmx1024m"));
    }

    @Test
    void perfilMemoriaRestringidaHeapFijo() {
        List<String> flags = GcFlagsExercises.flagsRecomendados(
                GcFlagsExercises.PerfilGc.MEMORIA_RESTRINGIDA, 256);
        assertTrue(flags.contains("-XX:+UseG1GC"));
        assertEquals("-Xms256m", flags.stream().filter(f -> f.startsWith("-Xms")).findFirst().orElseThrow());
        assertEquals("-Xmx256m", flags.stream().filter(f -> f.startsWith("-Xmx")).findFirst().orElseThrow());
    }
}
