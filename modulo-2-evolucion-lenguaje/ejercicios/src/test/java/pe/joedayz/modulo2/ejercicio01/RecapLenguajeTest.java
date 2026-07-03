package pe.joedayz.modulo2.ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pe.joedayz.modulo2.ejercicio01.RecapLenguaje.DiaSemana.DOMINGO;
import static pe.joedayz.modulo2.ejercicio01.RecapLenguaje.DiaSemana.JUEVES;
import static pe.joedayz.modulo2.ejercicio01.RecapLenguaje.DiaSemana.LUNES;
import static pe.joedayz.modulo2.ejercicio01.RecapLenguaje.DiaSemana.MARTES;
import static pe.joedayz.modulo2.ejercicio01.RecapLenguaje.DiaSemana.MIERCOLES;
import static pe.joedayz.modulo2.ejercicio01.RecapLenguaje.DiaSemana.SABADO;
import static pe.joedayz.modulo2.ejercicio01.RecapLenguaje.DiaSemana.VIERNES;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("Ejercicio 01 — Recap: var, switch expressions, text blocks")
class RecapLenguajeTest {

    @Nested
    @DisplayName("tipoDeDia")
    class TipoDeDia {

        @Test
        void martesTienePromocion() {
            assertEquals("Laborable - Promoción 2x1", RecapLenguaje.tipoDeDia(MARTES));
        }

        @ParameterizedTest
        @EnumSource(value = RecapLenguaje.DiaSemana.class, names = {"LUNES", "MIERCOLES", "JUEVES", "VIERNES"})
        void diasLaborablesNormales(RecapLenguaje.DiaSemana dia) {
            assertEquals("Laborable", RecapLenguaje.tipoDeDia(dia));
        }

        @ParameterizedTest
        @EnumSource(value = RecapLenguaje.DiaSemana.class, names = {"SABADO", "DOMINGO"})
        void finDeSemana(RecapLenguaje.DiaSemana dia) {
            assertEquals("Fin de semana", RecapLenguaje.tipoDeDia(dia));
        }
    }

    @Nested
    @DisplayName("horasDeTrabajo")
    class HorasDeTrabajo {

        @ParameterizedTest
        @CsvSource({
                "LUNES, 8",
                "MARTES, 8",
                "MIERCOLES, 8",
                "JUEVES, 8",
                "VIERNES, 6",
                "SABADO, 0",
                "DOMINGO, 0"
        })
        void horasSegunDia(RecapLenguaje.DiaSemana dia, int horasEsperadas) {
            assertEquals(horasEsperadas, RecapLenguaje.horasDeTrabajo(dia));
        }
    }

    @Nested
    @DisplayName("consultaActivos")
    class ConsultaActivos {

        @Test
        void generaSqlConTextBlock() {
            String sql = RecapLenguaje.consultaActivos("clientes", "id, nombre").strip();
            String esperado = """
                    SELECT id, nombre
                    FROM clientes
                    WHERE activo = true
                    ORDER BY nombre""".strip();
            assertEquals(esperado, sql);
        }

        @Test
        void interpolaTablaYColumnas() {
            String sql = RecapLenguaje.consultaActivos("pedidos", "id, total").strip();
            assertTrue(sql.contains("SELECT id, total"));
            assertTrue(sql.contains("FROM pedidos"));
        }
    }

    @Nested
    @DisplayName("totalesPorRegion")
    class TotalesPorRegion {

        @Test
        void mapaInmutableConValoresEsperados() {
            Map<String, Integer> totales = RecapLenguaje.totalesPorRegion();
            assertEquals(15000, totales.get("Norte"));
            assertEquals(9800, totales.get("Sur"));
            assertEquals(21000, totales.get("Centro"));
            assertEquals(3, totales.size());
            assertThrows(UnsupportedOperationException.class, () -> totales.put("Este", 1));
        }
    }
}
