package pe.joedayz.modulo2.ejercicio02;

/**
 * TODO: convertir a {@code public record Tarjeta(double monto, String ultimosDigitos) implements FormaPago {}}
 */
public final class Tarjeta implements FormaPago {
    private final double monto;
    private final String ultimosDigitos;

    public Tarjeta(double monto, String ultimosDigitos) {
        this.monto = monto;
        this.ultimosDigitos = ultimosDigitos;
    }

    public double monto() {
        return monto;
    }

    public String ultimosDigitos() {
        return ultimosDigitos;
    }
}
