package pe.joedayz.modulo2.ejercicio05;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pe.joedayz.modulo2.ejercicio05.OrderService.Cliente;
import static pe.joedayz.modulo2.ejercicio05.OrderService.Descuento;
import static pe.joedayz.modulo2.ejercicio05.OrderService.DescuentoFijo;
import static pe.joedayz.modulo2.ejercicio05.OrderService.DescuentoPorcentaje;
import static pe.joedayz.modulo2.ejercicio05.OrderService.EstadoOrden;
import static pe.joedayz.modulo2.ejercicio05.OrderService.Item;
import static pe.joedayz.modulo2.ejercicio05.OrderService.SinDescuento;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 05 — Laboratorio: modernizar OrderService")
class OrderServiceTest {

    private static final List<Item> ITEMS = List.of(
            new Item("Laptop", 3500.0, 1, true),
            new Item("Mouse", 45.0, 2, true),
            new Item("Teclado", 120.0, 1, false),
            new Item("Monitor", 800.0, 1, true)
    );

    @Nested
    @DisplayName("Modernización de tipos (records / sealed)")
    class ModernizacionTipos {

        @Test
        void clienteEItemSonRecords() {
            assertTrue(Cliente.class.isRecord());
            assertTrue(Item.class.isRecord());
        }

        @Test
        void descuentoEsSealedConTresPermits() {
            assertTrue(Descuento.class.isSealed());
            assertArrayEquals(
                    new Class<?>[]{DescuentoPorcentaje.class, DescuentoFijo.class, SinDescuento.class},
                    Descuento.class.getPermittedSubclasses());
        }

        @Test
        void implementacionesDeDescuentoSonRecords() {
            assertTrue(DescuentoPorcentaje.class.isRecord());
            assertTrue(DescuentoFijo.class.isRecord());
            assertTrue(SinDescuento.class.isRecord());
        }
    }

    @Nested
    @DisplayName("Lógica de negocio")
    class LogicaNegocio {

        @Test
        void itemsDisponiblesFiltraInactivos() {
            List<Item> disponibles = OrderService.itemsDisponibles(ITEMS);
            assertEquals(3, disponibles.size());
            assertTrue(disponibles.stream().allMatch(Item::disponible));
            assertFalse(disponibles.stream().anyMatch(i -> i.nombre().equals("Teclado")));
        }

        @Test
        void subtotalSoloDisponibles() {
            // Laptop 3500 + Mouse 90 + Monitor 800 = 4390 (Teclado no disponible)
            assertEquals(4390.0, OrderService.subtotal(OrderService.itemsDisponibles(ITEMS)), 0.0001);
        }

        @Test
        void descuentoPorcentaje() {
            assertEquals(3951.0, OrderService.aplicarDescuento(4390.0, new DescuentoPorcentaje(10.0)), 0.0001);
        }

        @Test
        void descuentoFijo() {
            assertEquals(4290.0, OrderService.aplicarDescuento(4390.0, new DescuentoFijo(100.0)), 0.0001);
        }

        @Test
        void sinDescuento() {
            assertEquals(4390.0, OrderService.aplicarDescuento(4390.0, new SinDescuento()), 0.0001);
        }

        @Test
        void textosDeEstado() {
            assertEquals("Pendiente de pago", OrderService.textoEstado(EstadoOrden.PENDIENTE));
            assertEquals("Pagada", OrderService.textoEstado(EstadoOrden.PAGADA));
            assertEquals("Enviada", OrderService.textoEstado(EstadoOrden.ENVIADA));
            assertEquals("Cancelada", OrderService.textoEstado(EstadoOrden.CANCELADA));
        }
    }

    @Nested
    @DisplayName("Recibo")
    class Recibo {

        @Test
        void contieneDatosDeNegocio() {
            Cliente cliente = new Cliente("Ana Torres", "ana.torres@example.com");
            String recibo = OrderService.generarRecibo(
                    cliente, EstadoOrden.PAGADA, ITEMS, new DescuentoPorcentaje(10.0));

            assertTrue(recibo.contains("=== RECIBO ==="));
            assertTrue(recibo.contains("Cliente: Ana Torres (ana.torres@example.com)"));
            assertTrue(recibo.contains("Estado: Pagada"));
            assertTrue(recibo.contains("Laptop"));
            assertTrue(recibo.contains("Mouse"));
            assertTrue(recibo.contains("Monitor"));
            assertFalse(recibo.contains("Teclado"));
            assertTrue(recibo.contains("Subtotal: S/ 4390.0") || recibo.contains("Subtotal: S/ 4390"));
            assertTrue(recibo.contains("Total con descuento: S/ 3951.0")
                    || recibo.contains("Total con descuento: S/ 3951"));
        }
    }
}
