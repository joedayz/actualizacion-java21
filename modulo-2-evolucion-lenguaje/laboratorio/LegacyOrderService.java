import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// ============================================================
// Código en ESTILO JAVA 8. Este es el punto de partida del
// laboratorio: los participantes deben modernizarlo.
// ============================================================
public class LegacyOrderService {

    // POJO clásico, con todo el boilerplate escrito a mano
    static final class Cliente {
        private final String nombre;
        private final String email;

        Cliente(String nombre, String email) {
            this.nombre = nombre;
            this.email = email;
        }

        String getNombre() { return nombre; }
        String getEmail() { return email; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cliente)) return false;
            Cliente cliente = (Cliente) o;
            return Objects.equals(nombre, cliente.nombre) && Objects.equals(email, cliente.email);
        }

        @Override
        public int hashCode() { return Objects.hash(nombre, email); }

        @Override
        public String toString() { return "Cliente{nombre='" + nombre + "', email='" + email + "'}"; }
    }

    static final class Item {
        private final String nombre;
        private final double precio;
        private final int cantidad;
        private final boolean disponible;

        Item(String nombre, double precio, int cantidad, boolean disponible) {
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
            this.disponible = disponible;
        }

        String getNombre() { return nombre; }
        double getPrecio() { return precio; }
        int getCantidad() { return cantidad; }
        boolean isDisponible() { return disponible; }

        double subtotal() { return precio * cantidad; }
    }

    enum EstadoOrden { PENDIENTE, PAGADA, ENVIADA, CANCELADA }

    // Jerarquía de descuentos con instanceof en cascada (sin sellar: cualquiera podría
    // crear una subclase nueva y el compilador no obligaría a manejarla en ningún lado)
    static abstract class Descuento {}

    static final class DescuentoPorcentaje extends Descuento {
        final double porcentaje;
        DescuentoPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }
    }

    static final class DescuentoFijo extends Descuento {
        final double monto;
        DescuentoFijo(double monto) { this.monto = monto; }
    }

    static final class SinDescuento extends Descuento {}

    public static void main(String[] args) {
        Cliente cliente = new Cliente("Ana Torres", "ana.torres@example.com");

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Laptop", 3500.0, 1, true));
        items.add(new Item("Mouse", 45.0, 2, true));
        items.add(new Item("Teclado", 120.0, 1, false)); // no disponible, no debería entrar al recibo
        items.add(new Item("Monitor", 800.0, 1, true));

        EstadoOrden estado = EstadoOrden.PAGADA;
        Descuento descuento = new DescuentoPorcentaje(10.0);

        // Filtrar disponibles con for clásico
        List<Item> itemsDisponibles = new ArrayList<Item>();
        for (Item item : items) {
            if (item.isDisponible()) {
                itemsDisponibles.add(item);
            }
        }

        // Calcular subtotal con for clásico
        double subtotal = 0.0;
        for (Item item : itemsDisponibles) {
            subtotal = subtotal + item.subtotal();
        }

        // Aplicar descuento con instanceof en cascada
        double totalConDescuento;
        if (descuento instanceof DescuentoPorcentaje) {
            DescuentoPorcentaje dp = (DescuentoPorcentaje) descuento;
            totalConDescuento = subtotal - (subtotal * dp.porcentaje / 100.0);
        } else if (descuento instanceof DescuentoFijo) {
            DescuentoFijo df = (DescuentoFijo) descuento;
            totalConDescuento = subtotal - df.monto;
        } else {
            totalConDescuento = subtotal;
        }

        // Texto de estado con switch clásico (statement, no expression)
        String estadoTexto;
        switch (estado) {
            case PENDIENTE:
                estadoTexto = "Pendiente de pago";
                break;
            case PAGADA:
                estadoTexto = "Pagada";
                break;
            case ENVIADA:
                estadoTexto = "Enviada";
                break;
            case CANCELADA:
                estadoTexto = "Cancelada";
                break;
            default:
                estadoTexto = "Desconocido";
        }

        // Construcción del recibo con concatenación de Strings
        StringBuilder recibo = new StringBuilder();
        recibo.append("=== RECIBO ===\n");
        recibo.append("Cliente: ").append(cliente.getNombre()).append(" (").append(cliente.getEmail()).append(")\n");
        recibo.append("Estado: ").append(estadoTexto).append("\n");
        recibo.append("Items:\n");
        for (Item item : itemsDisponibles) {
            recibo.append("  - ").append(item.getNombre())
                    .append(" x").append(item.getCantidad())
                    .append(" = S/ ").append(item.subtotal()).append("\n");
        }
        recibo.append("Subtotal: S/ ").append(subtotal).append("\n");
        recibo.append("Total con descuento: S/ ").append(totalConDescuento).append("\n");

        System.out.println(recibo.toString());
    }
}
