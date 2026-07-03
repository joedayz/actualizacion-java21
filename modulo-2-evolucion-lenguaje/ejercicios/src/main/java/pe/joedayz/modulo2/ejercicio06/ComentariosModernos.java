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

    /// Convierte el nombre a un formato simple tipo **Title Case**.
    ///
    /// Ejemplo: `"   aNA "` -> `Ana`
    ///
    /// @param nombre texto de entrada; no puede ser `null` ni balnk
    /// @return nombre recortado con la primera letra en mayúscula y el resto en minúscula
    public static String formatearNombre(String nombre) {
        if(nombre==null || nombre.isBlank()){
            throw new IllegalArgumentException("nombre no puede ser null ni blank");
        }
        String limpio = nombre.strip().toLowerCase();
        return Character.toUpperCase(limpio.charAt(0)) + limpio.substring(1);
    }

    /**
     * Calcula el porcentaje entero de {@code parte} respecto a un {@code total}.
     *
     * <p>Ejemplo de uso:
     * {@snippet :
     *   int valor = ComentariosModernos.porcentaje(1, 4); // 25
     * }
     *
     * @param parte cantidad parcial
     * @param total cantidad total; debe ser {@code > 0}
     * @return porcentaje entero  {@code (parte / total) * 100 / total}
     * @throws IllegalArgumentException si {@code total <= 0}
     */

    public static int porcentaje(int parte, int total) {
        if(total<=0){
            throw new IllegalArgumentException("total debe ser > 0");
        }
        return (parte * 100) / total;
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
