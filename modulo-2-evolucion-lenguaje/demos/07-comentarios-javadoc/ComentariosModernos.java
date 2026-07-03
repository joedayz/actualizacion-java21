/**
 * Demo: evolución de los comentarios de documentación (JavaDoc).
 *
 * <p>Ejecutar (solo imprime un resumen; lo importante es leer el código fuente):
 * {@code java ComentariosModernos.java}
 *
 * <h2>Qué cambió</h2>
 * <ul>
 *   <li><b>Java 8 (clásico):</b> {@code /** ... *&#47;} con HTML ({@code <p>}, {@code <pre>}, {@code <code>}).</li>
 *   <li><b>Java 18 (JEP 413):</b> etiqueta {@code {@snippet}} para ejemplos de código en la doc.</li>
 *   <li><b>Java 23 (JEP 467):</b> comentarios de documentación en Markdown con líneas {@code ///}.</li>
 * </ul>
 *
 * <p>Nota: {@code ///} siempre fue un comentario de línea válido para el compilador.
 * Lo nuevo es que la herramienta {@code javadoc} (JDK 23+) lo trata como documentación.
 */
public class ComentariosModernos {

    public static void main(String[] args) {
        System.out.println("=== Estilos de documentación en Java moderno ===");
        System.out.println("1) Tradicional /** */ + HTML  (Java 8 y sigue válido)");
        System.out.println("2) {@snippet} dentro de JavaDoc  (Java 18+)");
        System.out.println("3) /// Markdown documentation comments  (Java 23+ para javadoc)");
        System.out.println();
        System.out.println("saludarTradicional -> " + saludarTradicional("Ana"));
        System.out.println("saludarConSnippet  -> " + saludarConSnippet("Ana"));
        System.out.println("saludarMarkdown    -> " + saludarMarkdown("Ana"));
        System.out.println();
        System.out.println("Text block: el // de adentro NO es comentario de Java:");
        System.out.println(ejemploTextBlockConBarras());
    }

    // -------------------------------------------------------------------------
    // 1) ESTILO CLÁSICO (Java 8): HTML + tags JavaDoc
    // -------------------------------------------------------------------------

    /**
     * Saluda a una persona.
     *
     * <p>Ejemplo de uso (estilo antiguo, incómodo de escribir y mantener):
     * <pre>{@code
     * String msg = ComentariosModernos.saludarTradicional("Ana");
     * }</pre>
     *
     * @param nombre nombre de la persona; no debe ser {@code null}
     * @return saludo en español
     */
    public static String saludarTradicional(String nombre) {
        return "Hola, " + nombre;
    }

    // -------------------------------------------------------------------------
    // 2) JAVA 18+: {@snippet} — ejemplos de código embebidos en la doc
    // -------------------------------------------------------------------------

    /**
     * Saluda a una persona.
     *
     * <p>Ejemplo de uso con snippet (Java 18+, JEP 413):
     * {@snippet :
     * String msg = ComentariosModernos.saludarConSnippet("Ana");
     * System.out.println(msg);
     * }
     *
     * @param nombre nombre de la persona; no debe ser {@code null}
     * @return saludo en español
     */
    public static String saludarConSnippet(String nombre) {
        return "Hola, " + nombre;
    }

    // -------------------------------------------------------------------------
    // 3) JAVA 23+: /// Markdown documentation comments (JEP 467)
    // -------------------------------------------------------------------------

    /// Saluda a una persona usando **Markdown** en la documentación.
    ///
    /// Ventajas frente a `/** ... */` con HTML:
    /// - se lee mejor en el código fuente
    /// - listas, negritas y código inline con sintaxis familiar
    /// - los tags JavaDoc (`@param`, `@return`) siguen funcionando
    ///
    /// Ejemplo:
    /// ```
    /// String msg = ComentariosModernos.saludarMarkdown("Ana");
    /// ```
    ///
    /// @param nombre nombre de la persona; no debe ser `null`
    /// @return saludo en español
    public static String saludarMarkdown(String nombre) {
        return "Hola, " + nombre;
    }

    // -------------------------------------------------------------------------
    // Trampa frecuente: dentro de un text block, // es texto, no comentario
    // -------------------------------------------------------------------------

    /**
     * Muestra que un text block puede contener {@code //} sin “comentar” el código.
     * {@snippet :
     * String sql = """
     *     SELECT id -- columna
     *     FROM pedidos
     *     // esto sigue siendo parte del string
     *     """;
     * }
     */
    public static String ejemploTextBlockConBarras() {
        return """
                SELECT id -- columna
                FROM pedidos
                // esto NO comenta la línea en Java: es contenido del string
                """;
    }
}
