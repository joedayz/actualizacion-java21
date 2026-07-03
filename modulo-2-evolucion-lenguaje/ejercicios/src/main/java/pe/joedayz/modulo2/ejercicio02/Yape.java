package pe.joedayz.modulo2.ejercicio02;

/**
 * TODO: convertir a {@code public record Yape(double monto, String celular) implements FormaPago {}}
 */
public final class Yape implements FormaPago {
    private final double monto;
    private final String celular;

    public Yape(double monto, String celular) {
        this.monto = monto;
        this.celular = celular;
    }

    public double monto() {
        return monto;
    }

    public String celular() {
        return celular;
    }
}
