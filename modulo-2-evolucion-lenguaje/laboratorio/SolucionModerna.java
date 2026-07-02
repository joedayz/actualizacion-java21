import java.util.List;

// ============================================================
// SOLUCIÓN DE REFERENCIA (no repartir hasta el final del ejercicio).
// Misma lógica de negocio que LegacyOrderService.java, aplicando
// todo lo visto en el Módulo 2.
// ============================================================
public class SolucionModerna {

    // record en vez de POJO con boilerplate
    record Cliente(String nombre, String email) {}

    record Item(String nombre, double precio, int cantidad, boolean disponible) {
        double subtotal() { return precio * cantidad; }
    }

    enum EstadoOrden { PENDIENTE, PAGADA, ENVIADA, CANCELADA }

    // sealed interface: el compilador conoce TODAS las implementaciones posibles
    sealed interface Descuento permits DescuentoPorcentaje, DescuentoFijo, SinDescuento {}
    record DescuentoPorcentaje(double porcentaje) implements Descuento {}
    record DescuentoFijo(double monto) implements Descuento {}
    record SinDescuento() implements Descuento {}

    public static void main(String[] args) {
        var cliente = new Cliente("Ana Torres", "ana.torres@example.com");

        var items = List.of(
                new Item("Laptop", 3500.0, 1, true),
                new Item("Mouse", 45.0, 2, true),
                new Item("Teclado", 120.0, 1, false), // no disponible
                new Item("Monitor", 800.0, 1, true)
        );

        var estado = EstadoOrden.PAGADA;
        Descuento descuento = new DescuentoPorcentaje(10.0);

        // Streams en vez de for + ArrayList mutable
        var itemsDisponibles = items.stream()
                .filter(Item::disponible)
                .toList();

        double subtotal = itemsDisponibles.stream()
                .mapToDouble(Item::subtotal)
                .sum();

        // Pattern matching for switch + record patterns: sin instanceof en cascada,
        // exhaustivo por ser "sealed" (no necesita default)
        double totalConDescuento = switch (descuento) {
            case DescuentoPorcentaje(double porcentaje) -> subtotal - (subtotal * porcentaje / 100.0);
            case DescuentoFijo(double monto) -> subtotal - monto;
            case SinDescuento ignored -> subtotal;
        };

        // Switch expression exhaustivo para el texto de estado
        String estadoTexto = switch (estado) {
            case PENDIENTE -> "Pendiente de pago";
            case PAGADA -> "Pagada";
            case ENVIADA -> "Enviada";
            case CANCELADA -> "Cancelada";
        };

        // Text block en vez de concatenación con StringBuilder
        var lineasItems = itemsDisponibles.stream()
                .map(item -> "  - %s x%d = S/ %.1f".formatted(item.nombre(), item.cantidad(), item.subtotal()))
                .collect(java.util.stream.Collectors.joining("\n"));

        String recibo = """
                === RECIBO ===
                Cliente: %s (%s)
                Estado: %s
                Items:
                %s
                Subtotal: S/ %.1f
                Total con descuento: S/ %.1f
                """.formatted(
                        cliente.nombre(), cliente.email(),
                        estadoTexto,
                        lineasItems,
                        subtotal,
                        totalConDescuento
                );

        System.out.println(recibo);
    }
}
