public class TextBlocks {

    public static void main(String[] args) {
        String nombre = "Ana";
        int edad = 30;

        System.out.println("=== ANTES (Java 8): concatenación con \\n y escapes ===");
        String jsonClasico = "{\n" +
                "  \"nombre\": \"" + nombre + "\",\n" +
                "  \"edad\": " + edad + "\n" +
                "}";
        System.out.println(jsonClasico);

        System.out.println();
        System.out.println("=== AHORA (Java 15+): Text Block ===");
        String jsonModerno = """
                {
                  "nombre": "%s",
                  "edad": %d
                }""".formatted(nombre, edad);
        System.out.println(jsonModerno);

        System.out.println();
        System.out.println("=== Ejemplo con SQL (mucho más legible) ===");
        String tabla = "clientes";
        String sql = """
                SELECT id, nombre, email
                FROM %s
                WHERE activo = true
                ORDER BY nombre ASC
                """.formatted(tabla);
        System.out.println(sql);

        System.out.println("=== Ambos JSON son iguales como String? " + jsonClasico.equals(jsonModerno) + " ===");
        // El Text Block quita el espacio en blanco "incidental" común a todas las líneas,
        // basándose en la indentación de la línea de cierre """ -> por eso el resultado
        // es idéntico al armado a mano, pero mucho más legible de escribir y mantener.
    }
}
