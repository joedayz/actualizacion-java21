package pe.joedayz.modulo2.ejercicio02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 02 — Records y Sealed Classes")
class RecordsSealedTest {

    @Nested
    @DisplayName("Producto (record + validación)")
    class ProductoTests {

        @Test
        void esUnRecord() {
            assertTrue(Producto.class.isRecord());
        }

        @Test
        void accesoresYEquals() {
            Producto a = new Producto("Laptop", 3500.0);
            Producto b = new Producto("Laptop", 3500.0);
            assertEquals("Laptop", a.nombre());
            assertEquals(3500.0, a.precio());
            assertEquals(a, b);
        }

        @Test
        void precioConIgv() {
            assertEquals(118.0, new Producto("Mouse", 100.0).precioConIgv(), 0.0001);
        }

        @Test
        void rechazaNombreBlank() {
            assertThrows(IllegalArgumentException.class, () -> new Producto("  ", 10.0));
            assertThrows(IllegalArgumentException.class, () -> new Producto(null, 10.0));
        }

        @Test
        void rechazaPrecioNoPositivo() {
            assertThrows(IllegalArgumentException.class, () -> new Producto("X", 0.0));
            assertThrows(IllegalArgumentException.class, () -> new Producto("X", -1.0));
        }
    }

    @Nested
    @DisplayName("FormaPago (sealed + records)")
    class FormaPagoTests {

        @Test
        void formaPagoEsSealed() {
            assertTrue(FormaPago.class.isSealed());
        }

        @Test
        void soloPermiteTresImplementaciones() {
            Class<?>[] permitidas = FormaPago.class.getPermittedSubclasses();
            assertArrayEquals(
                    new Class<?>[]{Efectivo.class, Tarjeta.class, Yape.class},
                    permitidas);
        }

        @Test
        void implementacionesSonRecords() {
            assertTrue(Efectivo.class.isRecord());
            assertTrue(Tarjeta.class.isRecord());
            assertTrue(Yape.class.isRecord());
        }

        @Test
        void componentesDeCadaRecord() {
            assertEquals(50.0, new Efectivo(50.0).monto());
            assertEquals("4321", new Tarjeta(100.0, "4321").ultimosDigitos());
            assertEquals("999888777", new Yape(30.0, "999888777").celular());
        }
    }
}
