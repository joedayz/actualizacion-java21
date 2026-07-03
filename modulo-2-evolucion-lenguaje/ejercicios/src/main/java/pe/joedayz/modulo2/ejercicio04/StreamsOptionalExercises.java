package pe.joedayz.modulo2.ejercicio04;

import java.util.List;
import java.util.Optional;

/**
 * Ejercicio 04 — Streams modernos, Optional y colecciones inmutables.
 */
public final class StreamsOptionalExercises {

    private StreamsOptionalExercises() {}

    public record Producto(String nombre, double precio, boolean activo) {}

    /**
     * Nombres de productos activos, en el mismo orden de entrada.
     * Usa Stream + {@code filter} + {@code map} + {@code toList()} (Java 16+),
     * no {@code collect(Collectors.toList())}.
     */
    public static List<String> nombresActivos(List<Producto> productos) {
        // TODO
        throw new UnsupportedOperationException("TODO: nombresActivos");
    }

    /**
     * Suma de precios de productos activos. Si la lista está vacía o no hay activos, retorna 0.0.
     */
    public static double totalActivos(List<Producto> productos) {
        // TODO: stream + mapToDouble + sum
        throw new UnsupportedOperationException("TODO: totalActivos");
    }

    /**
     * Producto activo más caro. Si no hay ninguno activo, {@link Optional#empty()}.
     */
    public static Optional<Producto> masCaroActivo(List<Producto> productos) {
        // TODO: filter + max
        throw new UnsupportedOperationException("TODO: masCaroActivo");
    }

    /**
     * Mensaje según el optional:
     * <ul>
     *   <li>Presente → {@code "Más caro: Laptop (S/ 3500.0)"}</li>
     *   <li>Vacío → {@code "Sin productos activos"}</li>
     * </ul>
     * Usa {@link Optional#ifPresentOrElse} o equivalente moderno (no {@code isPresent}/{@code get}).
     */
    public static String mensajeMasCaro(Optional<Producto> producto) {
        // TODO
        throw new UnsupportedOperationException("TODO: mensajeMasCaro");
    }

    /**
     * Catálogo fijo e inmutable con {@code List.of}:
     * Laptop 3500 activo, Mouse 45 activo, Teclado 120 inactivo.
     */
    public static List<Producto> catalogoBase() {
        // TODO: List.of(...)
        throw new UnsupportedOperationException("TODO: catalogoBase");
    }

    /**
     * Primero y último de la lista usando Sequenced Collections (Java 21):
     * {@code getFirst()} y {@code getLast()}. Formato: {@code "A|C"} para lista [A,B,C].
     * Si la lista está vacía, retorna {@code ""}.
     */
    public static String primeroYUltimo(List<String> valores) {
        // TODO
        throw new UnsupportedOperationException("TODO: primeroYUltimo");
    }
}
