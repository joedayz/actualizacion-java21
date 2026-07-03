package pe.joedayz.modulo2.ejercicio04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pe.joedayz.modulo2.ejercicio04.StreamsOptionalExercises.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 04 — Streams, Optional y colecciones inmutables")
class StreamsOptionalExercisesTest {

    private static final List<Producto> PRODUCTOS = List.of(
            new Producto("Laptop", 3500.0, true),
            new Producto("Mouse", 45.0, true),
            new Producto("Teclado", 120.0, false),
            new Producto("Monitor", 800.0, true)
    );

    @Nested
    @DisplayName("nombresActivos")
    class NombresActivos {

        @Test
        void filtraYMapeaEnOrden() {
            assertEquals(List.of("Laptop", "Mouse", "Monitor"),
                    StreamsOptionalExercises.nombresActivos(PRODUCTOS));
        }

        @Test
        void resultadoEsInmutable() {
            List<String> nombres = StreamsOptionalExercises.nombresActivos(PRODUCTOS);
            assertThrows(UnsupportedOperationException.class, () -> nombres.add("Otro"));
        }
    }

    @Nested
    @DisplayName("totalActivos")
    class TotalActivos {

        @Test
        void sumaSoloActivos() {
            assertEquals(4345.0, StreamsOptionalExercises.totalActivos(PRODUCTOS), 0.0001);
        }

        @Test
        void listaVaciaEsCero() {
            assertEquals(0.0, StreamsOptionalExercises.totalActivos(List.of()), 0.0001);
        }
    }

    @Nested
    @DisplayName("masCaroActivo y mensaje")
    class MasCaro {

        @Test
        void encuentraLaptop() {
            Optional<Producto> masCaro = StreamsOptionalExercises.masCaroActivo(PRODUCTOS);
            assertTrue(masCaro.isPresent());
            assertEquals("Laptop", masCaro.orElseThrow().nombre());
        }

        @Test
        void vacioSiNoHayActivos() {
            List<Producto> inactivos = List.of(new Producto("X", 1.0, false));
            assertTrue(StreamsOptionalExercises.masCaroActivo(inactivos).isEmpty());
        }

        @Test
        void mensajePresente() {
            assertEquals("Más caro: Laptop (S/ 3500.0)",
                    StreamsOptionalExercises.mensajeMasCaro(Optional.of(new Producto("Laptop", 3500.0, true))));
        }

        @Test
        void mensajeVacio() {
            assertEquals("Sin productos activos",
                    StreamsOptionalExercises.mensajeMasCaro(Optional.empty()));
        }
    }

    @Nested
    @DisplayName("catalogoBase")
    class CatalogoBase {

        @Test
        void contenidoYOrden() {
            List<Producto> catalogo = StreamsOptionalExercises.catalogoBase();
            assertEquals(3, catalogo.size());
            assertEquals(new Producto("Laptop", 3500.0, true), catalogo.get(0));
            assertEquals(new Producto("Mouse", 45.0, true), catalogo.get(1));
            assertEquals(new Producto("Teclado", 120.0, false), catalogo.get(2));
        }

        @Test
        void esInmutable() {
            assertThrows(UnsupportedOperationException.class,
                    () -> StreamsOptionalExercises.catalogoBase().add(new Producto("X", 1, true)));
        }
    }

    @Nested
    @DisplayName("primeroYUltimo (Sequenced Collections)")
    class PrimeroYUltimo {

        @Test
        void listaConElementos() {
            assertEquals("A|C", StreamsOptionalExercises.primeroYUltimo(List.of("A", "B", "C")));
        }

        @Test
        void unSoloElemento() {
            assertEquals("Solo|Solo", StreamsOptionalExercises.primeroYUltimo(List.of("Solo")));
        }

        @Test
        void listaVacia() {
            assertEquals("", StreamsOptionalExercises.primeroYUltimo(new ArrayList<>()));
        }
    }
}
