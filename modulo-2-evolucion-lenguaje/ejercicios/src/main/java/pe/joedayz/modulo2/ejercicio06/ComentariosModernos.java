package pe.joedayz.modulo2.ejercicio06;

/**
 * Ejercicio 06 — Cambios en el uso de comentarios / JavaDoc.
 *
 * <p>Además de implementar los métodos, debes documentarlos con los estilos modernos:
 * <ol>
 *   <li>{@code formatearNombre} — documentación Markdown con líneas {@code ///}
 *       (JEP 467; la herramienta javadoc la procesa desde JDK 23; el compilador acepta
 *       esas líneas desde siempre porque son comentarios de fin de línea).</li>
 *   <li>{@code porcentaje} — JavaDoc tradicional con un ejemplo embebido mediante la
 *       etiqueta snippet de Java 18 (JEP 413): llave-arroba-snippet.</li>
 * </ol>
 *
 * <p>Los tests comprueban el comportamiento y que el código fuente use esos estilos.
 */
public final class ComentariosModernos {

    private ComentariosModernos() {}

    // -------------------------------------------------------------------------
    // TODO 1: documenta formatearNombre con líneas que empiecen por tres barras.
    //
    // Requisitos del comentario Markdown:
    //   - al menos 2 líneas cuyo texto (tras espacios) empiece por tres barras
    //   - usar backticks en algún punto, p.ej. alrededor de Ana
    //   - incluir @param nombre y @return
    //
    // Lógica:
    //   - null o blank → IllegalArgumentException
    //   - si no: strip + primera letra mayúscula + resto minúsculas
    //   - "  aNA " → "Ana"
    // -------------------------------------------------------------------------

    public static String formatearNombre(String nombre) {
        // TODO: implementar + documentar con tres barras encima de este método
        throw new UnsupportedOperationException("TODO: formatearNombre");
    }

    // -------------------------------------------------------------------------
    // TODO 2: documenta porcentaje con JavaDoc tradicional /** ... */
    //         e incluye dentro un ejemplo con la etiqueta snippet (Java 18).
    //
    // Lógica:
    //   - total <= 0 → IllegalArgumentException
    //   - si no: (parte * 100) / total  (división entera)
    //   - porcentaje(1, 4) → 25
    // -------------------------------------------------------------------------

    public static int porcentaje(int parte, int total) {
        // TODO: implementar + documentar con /** */ y etiqueta snippet
        throw new UnsupportedOperationException("TODO: porcentaje");
    }

    /**
     * Referencia: dentro de un text block, las dos barras son texto del string,
     * no un comentario de Java. No modifiques este método.
     */
    public static String sqlConComentarioSql() {
        return """
                SELECT id
                FROM pedidos
                // filtro aplicado en la app, no en SQL
                WHERE activo = true
                """;
    }
}
