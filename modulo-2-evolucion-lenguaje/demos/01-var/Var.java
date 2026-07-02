import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Var {

    public static void main(String[] args) {
        System.out.println("=== ANTES (Java 8): tipo explícito repetido dos veces ===");
        List<String> clientesJava8 = new ArrayList<String>();
        clientesJava8.add("Ana");
        clientesJava8.add("Luis");
        System.out.println(clientesJava8);

        System.out.println();
        System.out.println("=== AHORA (Java 10+): var infiere el tipo en compilación ===");
        var clientes = new ArrayList<String>(); // el tipo real sigue siendo ArrayList<String>
        clientes.add("Ana");
        clientes.add("Luis");
        System.out.println(clientes);

        // Buen uso: tipo genérico largo, evita ruido visual
        var totalesPorRegion = Map.of(
                "Norte", 15000,
                "Sur", 9800,
                "Centro", 21000
        );
        System.out.println(totalesPorRegion);

        // var también funciona en for-each y try-with-resources
        for (var entry : totalesPorRegion.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Mal uso (comentado a propósito): reduce legibilidad si el tipo no es obvio
        // var resultado = servicioExterno.procesar(datos); // ¿qué tipo retorna esto?

        // Lo que var NO puede hacer:
        // var sinInicializar;              // ERROR: necesita inicializador
        // var nulo = null;                 // ERROR: no puede inferir tipo de null


        System.out.println();
        System.out.println("El tipo real de 'clientes' sigue siendo: " + clientes.getClass().getSimpleName());
    }
}
