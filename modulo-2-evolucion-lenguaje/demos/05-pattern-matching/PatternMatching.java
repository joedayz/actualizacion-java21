public class PatternMatching {

    // Reutilizamos una jerarquía sellada similar a la del demo 04
    sealed interface FormaPago permits Efectivo, Tarjeta, Yape {}
    record Efectivo(double monto) implements FormaPago {}
    record Tarjeta(double monto, String ultimosDigitos) implements FormaPago {}
    record Yape(double monto, String celular) implements FormaPago {}

    public static void main(String[] args) {
        System.out.println("=== Pattern Matching for instanceof ===");
        Object obj = "Hola mundo";
        procesarClasico(obj);
        procesarModerno(obj);

        System.out.println();
        System.out.println("=== Pattern Matching for switch + Record Patterns (Java 21) ===");
        FormaPago[] pagos = {
                new Efectivo(100.0),
                new Tarjeta(250.5, "4321"),
                new Yape(30.0, "999888777")
        };
        for (FormaPago pago : pagos) {
            System.out.println(describir(pago));
        }
    }

    // ===== ANTES (Java 8): instanceof + cast manual =====
    static void procesarClasico(Object obj) {
        if (obj instanceof String) {
            String s = (String) obj; // cast redundante, típico bug si se olvida o se castea mal
            System.out.println("Clásico -> longitud: " + s.length());
        }
    }

    // ===== AHORA (Java 16+): pattern variable, sin cast =====
    static void procesarModerno(Object obj) {
        if (obj instanceof String s) {
            System.out.println("Moderno -> longitud: " + s.length());
        }
    }

    // ===== Pattern Matching for switch + Record Patterns (Java 21) =====
    // No necesita "default": FormaPago es sealed y el compilador sabe
    // que Efectivo, Tarjeta y Yape son las ÚNICAS implementaciones posibles.
    static String describir(FormaPago pago) {
        return switch (pago) {
            case Efectivo(double monto) ->
                    "Efectivo por S/ " + monto;
            case Tarjeta(double monto, String digitos) ->
                    "Tarjeta terminada en " + digitos + " por S/ " + monto;
            case Yape(double monto, String celular) ->
                    "Yape desde " + celular + " por S/ " + monto;
        };
    }
}
