import java.util.*;
import java.util.stream.Collectors;

public class StreamsOptional {

    record Producto(String nombre, double precio, boolean activo) {
    }

    public static void main(String[] args) {
        List<Producto> productos = List.of(
                new Producto("Laptop", 3500.0, true),
                new Producto("Mouse", 45.0, true),
                new Producto("Teclado", 120.0, false),
                new Producto("Monitor", 800.0, true)
        );

        System.out.println("=== ANTES (Java 8): collect(Collectors.toList()) ===");
        var nombresClasico = productos.stream()
                .filter(Producto::activo)
                .map(Producto::nombre)
                .collect(Collectors.toList());
        System.out.println(nombresClasico);

        System.out.println();
        System.out.println("=== AHORA (Java 16+): Stream.toList() ===");
        var nombresModerno = productos.stream()
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


        List<String> lenguajes = List.of(
                "Java",
                "Python",
                "Go"
        );
        try {
            lenguajes.add("Rust"); // lanzará UnsupportedOperationException, porque List.of() devuelve una lista inmutable
        } catch (UnsupportedOperationException e) {
            System.out.println("Como se esperaba, la lista de List.of() es inmutable: " + e.getClass().getSimpleName());
        }

        Set<String> colores = Set.of(
                "Rojo",
                "Verde",
                "Azul"
        );

        System.out.println(colores);


        Map<Integer, String> meses = Map.ofEntries(
                Map.entry(1, "Enero"),
                Map.entry(2, "Febrero"),
                Map.entry(3, "Marzo"),
                Map.entry(4, "Abril")
        );

        //NOTAS ADICIONALES
        //List.of("Java" , null); // lanzará NullPointerException, porque List.of() no permite elementos nulos

        //Set.of("Rojo", "Verde", null); // lanzará NullPointerException, porque Set.of() no permite elementos nulos

        //Map.of(1, null); // lanzará NullPointerException, porque Map.of() no permite valores nulos



        System.out.println();
        System.out.println("=== Sequenced Collections (Java 21): getFirst/getLast/reversed ===");
        List<String> orden = new ArrayList<>(nombresModerno);
        System.out.println("Primero: " + orden.getFirst());
        System.out.println("Último: " + orden.getLast());
        System.out.println("Invertido: " + orden.reversed());


//        Collection
//     │
//        SequencedCollection<E>   ← NUEVA
//     │
//     ├── List<E>
//     ├── Deque<E>
//     └── SequencedSet<E>


        List<String> list = new ArrayList<>(List.of("A", "B", "C"));

        list.removeFirst();
        System.out.println(list); // [B, C]


        list.removeLast();
        System.out.println(list); // [B]



        List<String> list2 = new ArrayList<>();

        list2.addFirst("Java");
        list2.addFirst("Spring");

        System.out.println(list2); // [Spring, Java]


        list2.add(0, "Spring"); // [Spring, Java]
        list2.addLast("AWS"); // [Spring, Java, AWS]
        list2.reversed(); // [AWS, Java, Spring]

        //Que otras colecciones han sido afectadas
        // LinkedList
        // ArrayList
        // Deque
        // LinkedHashSet   (SequencedSet)
        // LinkedHashMap   (SequencedMap)

    }
}
