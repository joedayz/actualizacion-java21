package pe.joedayz.modulo4.ejercicio04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 04 — Optimización medible")
class OptimizacionMedibleTest {

    @Test
    void optimizadoCoincideConNaive() {
        assertEquals(OptimizacionMedible.concatenarNaive(0), OptimizacionMedible.concatenarOptimizado(0));
        assertEquals(OptimizacionMedible.concatenarNaive(1), OptimizacionMedible.concatenarOptimizado(1));
        assertEquals(OptimizacionMedible.concatenarNaive(100), OptimizacionMedible.concatenarOptimizado(100));
        assertEquals(OptimizacionMedible.concatenarNaive(500), OptimizacionMedible.concatenarOptimizado(500));
    }

    @Test
    void naiveEjemploConocido() {
        assertEquals("012", OptimizacionMedible.concatenarNaive(3));
    }
}
