package pe.joedayz.modulo2.ejercicio02;

/**
 * Ejercicio 02a — Record con validación en constructor compacto.
 *
 * <p>Ya es un {@code record}. Completa el constructor compacto:
 * <ul>
 *   <li>{@code nombre} no puede ser {@code null} ni blank ({@code isBlank()})</li>
 *   <li>{@code precio} debe ser {@code > 0}</li>
 * </ul>
 * En ambos casos lanza {@link IllegalArgumentException}.
 */
public record Producto(String nombre, double precio) {

    public Producto {
        // TODO: validar nombre y precio
    }

    /** Precio con IGV (18%). */
    public double precioConIgv() {
        // TODO: return precio * 1.18
        throw new UnsupportedOperationException("TODO: precioConIgv");
    }
}
