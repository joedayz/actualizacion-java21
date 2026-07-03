package pe.joedayz.modulo2.ejercicio02;

/**
 * TODO: convertir a {@code public record Efectivo(double monto) implements FormaPago {}}
 */
public final class Efectivo implements FormaPago {
    private final double monto;

    public Efectivo(double monto) {
        this.monto = monto;
    }

    public double monto() {
        return monto;
    }
}
