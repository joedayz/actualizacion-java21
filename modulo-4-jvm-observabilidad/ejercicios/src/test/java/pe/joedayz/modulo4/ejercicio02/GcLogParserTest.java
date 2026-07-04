package pe.joedayz.modulo4.ejercicio02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 02 — Parser de logs GC")
class GcLogParserTest {

    private static final String YOUNG =
            "[0.456s][info][gc] GC(12) Pause Young (Normal) (G1 Evacuation Pause) 24M->8M(64M) 3.456ms";

    private static final String FULL =
            "[1.200s][info][gc] GC(15) Pause Full (G1 Compaction Pause) 48M->12M(64M) 120.5ms";

    @Test
    void extraerPausaYoungGc() {
        assertEquals(3.456, GcLogParser.extraerPausaMs(YOUNG));
    }

    @Test
    void extraerPausaFullGc() {
        assertEquals(120.5, GcLogParser.extraerPausaMs(FULL));
    }

    @Test
    void extraerPausaLineaInvalida() {
        assertNull(GcLogParser.extraerPausaMs("[info] JVM started"));
    }

    @Test
    void detectarFullGc() {
        assertTrue(GcLogParser.esFullGc(FULL));
        assertFalse(GcLogParser.esFullGc(YOUNG));
    }

    @Test
    void extraerHeapAntesDespues() {
        assertArrayEquals(new int[] {24, 8}, GcLogParser.extraerHeapAntesDespuesMb(YOUNG));
        assertArrayEquals(new int[] {48, 12}, GcLogParser.extraerHeapAntesDespuesMb(FULL));
    }
}
