package pe.joedayz.modulo2.ejercicio05;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Ejercicio 05 — Laboratorio: moderniza este servicio estilo Java 8.
 *
 * <p>Objetivo: aplicar todo lo del módulo manteniendo el mismo comportamiento de negocio.
 * Pistas en cada método. Los tests validan el resultado, no el estilo; el facilitador
 * revisará el uso de records, sealed, pattern matching, streams, text blocks y {@code var}.
 *
 * <p>Checklist sugerido:
 * <ul>
 *   <li>{@link Cliente} e {@link Item} → {@code record}</li>
 *   <li>{@link Descuento} → {@code sealed interface} + records</li>
 *   <li>{@link #aplicarDescuento} → pattern matching for switch</li>
 *   <li>{@link #textoEstado} → switch expression</li>
 *   <li>{@link #itemsDisponibles} / {@link #subtotal} → Stream + {@code toList()}</li>
 *   <li>{@link #generarRecibo} → text block + {@code formatted}</li>
 * </ul>
 */
public final class OrderService {

    // TODO: convertir a record Cliente(String nombre, String email)
    public static final class Cliente {
        private final String nombre;
        private final String email;

        public Cliente(String nombre, String email) {
            this.nombre = nombre;
            this.email = email;
        }

        public String nombre() { return nombre; }
        public String email() { return email; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cliente)) return false;
            Cliente cliente = (Cliente) o;
            return Objects.equals(nombre, cliente.nombre) && Objects.equals(email, cliente.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nombre, email);
        }
    }

    // TODO: convertir a record Item(String nombre, double precio, int cantidad, boolean disponible)
    public static final class Item {
        private final String nombre;
        private final double precio;
        private final int cantidad;
        private final boolean disponible;

        public Item(String nombre, double precio, int cantidad, boolean disponible) {
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
            this.disponible = disponible;
        }

        public String nombre() { return nombre; }
        public double precio() { return precio; }
        public int cantidad() { return cantidad; }
        public boolean disponible() { return disponible; }

        public double subtotal() {
            return precio * cantidad;
        }
    }

    public enum EstadoOrden { PENDIENTE, PAGADA, ENVIADA, CANCELADA }

    // TODO: sealed interface Descuento permits DescuentoPorcentaje, DescuentoFijo, SinDescuento
    public interface Descuento {}

    // TODO: records que implementen Descuento
    public static final class DescuentoPorcentaje implements Descuento {
        private final double porcentaje;

        public DescuentoPorcentaje(double porcentaje) {
            this.porcentaje = porcentaje;
        }

        public double porcentaje() { return porcentaje; }
    }

    public static final class DescuentoFijo implements Descuento {
        private final double monto;

        public DescuentoFijo(double monto) {
            this.monto = monto;
        }

        public double monto() { return monto; }
    }

    public static final class SinDescuento implements Descuento {}

    /** Solo items con {@code disponible == true}, en el mismo orden. */
    public static List<Item> itemsDisponibles(List<Item> items) {
        // TODO: modernizar (Stream + toList). La lógica de negocio debe conservarse.
        List<Item> disponibles = new ArrayList<>();
        for (Item item : items) {
            if (item.disponible()) {
                disponibles.add(item);
            }
        }
        return disponibles;
    }

    public static double subtotal(List<Item> itemsDisponibles) {
        // TODO: modernizar con stream
        double total = 0.0;
        for (Item item : itemsDisponibles) {
            total = total + item.subtotal();
        }
        return total;
    }

    /**
     * Aplica el descuento al subtotal.
     * Porcentaje: {@code subtotal - subtotal * porcentaje / 100}.
     * Fijo: {@code subtotal - monto}.
     * Sin descuento: {@code subtotal}.
     */
    public static double aplicarDescuento(double subtotal, Descuento descuento) {
        // TODO: pattern matching for switch + record patterns (sin instanceof en cascada)
        if (descuento instanceof DescuentoPorcentaje) {
            DescuentoPorcentaje dp = (DescuentoPorcentaje) descuento;
            return subtotal - (subtotal * dp.porcentaje() / 100.0);
        } else if (descuento instanceof DescuentoFijo) {
            DescuentoFijo df = (DescuentoFijo) descuento;
            return subtotal - df.monto();
        } else {
            return subtotal;
        }
    }

    public static String textoEstado(EstadoOrden estado) {
        // TODO: switch expression exhaustivo
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
        return estadoTexto;
    }

    /**
     * Recibo multilínea. Debe contener al menos las líneas de negocio esperadas
     * (ver tests). Preferible text block.
     */
    public static String generarRecibo(Cliente cliente, EstadoOrden estado,
                                       List<Item> items, Descuento descuento) {
        // TODO: modernizar con text block; la lógica puede reutilizar los métodos de arriba
        List<Item> disponibles = itemsDisponibles(items);
        double sub = subtotal(disponibles);
        double total = aplicarDescuento(sub, descuento);
        String estadoTexto = textoEstado(estado);

        StringBuilder recibo = new StringBuilder();
        recibo.append("=== RECIBO ===\n");
        recibo.append("Cliente: ").append(cliente.nombre()).append(" (").append(cliente.email()).append(")\n");
        recibo.append("Estado: ").append(estadoTexto).append("\n");
        recibo.append("Items:\n");
        for (Item item : disponibles) {
            recibo.append("  - ").append(item.nombre())
                    .append(" x").append(item.cantidad())
                    .append(" = S/ ").append(item.subtotal()).append("\n");
        }
        recibo.append("Subtotal: S/ ").append(sub).append("\n");
        recibo.append("Total con descuento: S/ ").append(total).append("\n");
        return recibo.toString();
    }
}
