package pe.joedayz.modulo2.ejercicio03;

/**
 * Ejercicio 03 — Pattern Matching for {@code instanceof} y {@code switch}.
 *
 * <p>La jerarquía {@link Forma} ya está sellada. Tú implementas la lógica.
 */
public final class PatternMatchingExercises {

    private PatternMatchingExercises() {}

    public sealed interface Forma permits Circulo, Rectangulo, Cuadrado {}

    public record Circulo(double radio) implements Forma {}

    public record Rectangulo(double ancho, double alto) implements Forma {}

    public record Cuadrado(double lado) implements Forma {}

    /**
     * Pattern matching for {@code instanceof} (Java 16+).
     * <ul>
     *   <li>Si es {@link String} no blank → su {@code length()}</li>
     *   <li>Si es {@link Integer} → su valor int</li>
     *   <li>En cualquier otro caso → {@code -1}</li>
     * </ul>
     * Usa {@code if (obj instanceof String s)} (sin cast manual).
     */
    public static int medir(Object obj) {
        // TODO
        throw new UnsupportedOperationException("TODO: medir");
    }

    /**
     * Pattern matching for {@code switch} + record patterns (Java 21).
     * Calcula el área. Como {@link Forma} es sealed, el switch debe ser exhaustivo
     * <strong>sin</strong> {@code default}.
     * <ul>
     *   <li>{@code Circulo(r)} → {@code Math.PI * r * r}</li>
     *   <li>{@code Rectangulo(a, h)} → {@code a * h}</li>
     *   <li>{@code Cuadrado(l)} → {@code l * l}</li>
     * </ul>
     */
    public static double area(Forma forma) {
        // TODO: switch (forma) { case Circulo(double r) -> ... }
        throw new UnsupportedOperationException("TODO: area");
    }

    /**
     * Describe la forma con record patterns.
     * <ul>
     *   <li>Circulo → {@code "Círculo r=2.0"}</li>
     *   <li>Rectangulo → {@code "Rectángulo 3.0x4.0"}</li>
     *   <li>Cuadrado → {@code "Cuadrado l=5.0"}</li>
     * </ul>
     */
    public static String describir(Forma forma) {
        // TODO
        throw new UnsupportedOperationException("TODO: describir");
    }
}
