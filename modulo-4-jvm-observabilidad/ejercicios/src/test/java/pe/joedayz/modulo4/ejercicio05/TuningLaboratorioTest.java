package pe.joedayz.modulo4.ejercicio05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 05 — Laboratorio: tuning de allocations")
class TuningLaboratorioTest {

    @Test
    void reporteVacio() {
        assertEquals("", TuningLaboratorio.generarReporte(0));
    }

    @Test
    void reporteTresLineas() {
        assertEquals("linea-0;linea-1;linea-2;", TuningLaboratorio.generarReporte(3));
    }

    @Test
    void reporteGrandeLongitudEsperada() {
        String reporte = TuningLaboratorio.generarReporte(1_000);
        assertEquals(1_000, reporte.split(";", -1).length - 1);
        assertTrue(reporte.startsWith("linea-0;"));
        assertTrue(reporte.contains("linea-999;"));
    }
}
