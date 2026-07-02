import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamsOptional {

    record Producto(String nombre, double precio, boolean activo) {}

    public static void main(String[] args) {
        List<Producto> productos = List.of(
                new Producto("Laptop", 3500.0, true),
                new Producto("Mouse", 45.0, true),
                new Producto("Teclado", 120.0, false),
                new Producto("Monitor", 800.0, true)
        );

        System.out.println("=== ANTES (Java 8): collect(Collectors.toList()) ===");
        List<String> nombresClasico = productos.stream()
                .filter(Producto::activo)
                .map(Producto::nombre)
                .collect(Collectors.toList());
        System.out.println(nombresClasico);

        System.out.println();
        System.out.println("=== AHORA (Java 16+): Stream.toList() ===");
        List<String> nombresModerno = productos.stream()
                .filter(Producto::activo)
                .map(Producto::nombre)
                .toList(); // más corto, e inmutable
        System.out.println(nombresModerno);
        try {
            nombresModerno.add("Otro"); // debe fallar: toList() es inmutable
        } catch (UnsupportedOperationException e) {
            System.out.println("Como se esperaba, la lista de toList() es inmutable: " + e.getClass().getSimpleName());
        }

        System.out.println();
        System.out.println("=== Optional: ifPresentOrElse (Java 9+) ===");
        Optional<Producto> masCaro = productos.stream()
                .max((a, b) -> Double.compare(a.precio(), b.precio()));
        masCaro.ifPresentOrElse(
                p -> System.out.println("Producto más caro: " + p.nombre() + " (S/ " + p.precio() + ")"),
                () -> System.out.println("No hay productos")
        );

        System.out.println();
        System.out.println("=== Colecciones inmutables: List.of / Map.of (Java 9+) ===");
        // productos.add(...) -> lanzaría UnsupportedOperationException, a diferencia de un ArrayList normal
        System.out.println("productos.getClass() = " + productos.getClass().getSimpleName());

        System.out.println();
        System.out.println("=== Sequenced Collections (Java 21): getFirst/getLast/reversed ===");
        List<String> orden = new ArrayList<>(nombresModerno);
        System.out.println("Primero: " + orden.getFirst());
        System.out.println("Último: " + orden.getLast());
        System.out.println("Invertido: " + orden.reversed());
    }
}
