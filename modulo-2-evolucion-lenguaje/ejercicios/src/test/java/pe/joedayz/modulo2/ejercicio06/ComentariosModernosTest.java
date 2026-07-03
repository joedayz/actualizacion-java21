package pe.joedayz.modulo2.ejercicio06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 06 — Comentarios / JavaDoc moderno")
class ComentariosModernosTest {

    private static final Pattern MARKDOWN_DOC = Pattern.compile(
            "(?m)^[ \\t]*///.*(?:\\R[ \\t]*///.*)+\\R[ \\t]*public\\s+static\\s+\\S+\\s+formatearNombre\\s*\\(");

    private static final Pattern SNIPPET_JAVADOC = Pattern.compile(
            "/\\*\\*[\\s\\S]*?\\{@snippet[\\s\\S]*?\\*/\\s*public\\s+static\\s+\\S+\\s+porcentaje\\s*\\(");

    @Nested
    @DisplayName("Comportamiento")
    class Comportamiento {

        @Test
        void formatearNombreTitleCase() {
            assertEquals("Ana", ComentariosModernos.formatearNombre("  aNA "));
            assertEquals("Jose", ComentariosModernos.formatearNombre("JOSE"));
        }

        @Test
        void formatearNombreRechazaInvalidos() {
            assertThrows(IllegalArgumentException.class, () -> ComentariosModernos.formatearNombre(null));
            assertThrows(IllegalArgumentException.class, () -> ComentariosModernos.formatearNombre("   "));
        }

        @Test
        void porcentajeEntero() {
            assertEquals(25, ComentariosModernos.porcentaje(1, 4));
            assertEquals(100, ComentariosModernos.porcentaje(5, 5));
            assertEquals(0, ComentariosModernos.porcentaje(0, 10));
        }

        @Test
        void porcentajeTotalInvalido() {
            assertThrows(IllegalArgumentException.class, () -> ComentariosModernos.porcentaje(1, 0));
            assertThrows(IllegalArgumentException.class, () -> ComentariosModernos.porcentaje(1, -3));
        }

        @Test
        void textBlockConservaBarrasComoTexto() {
            String sql = ComentariosModernos.sqlConComentarioSql();
            assertTrue(sql.contains("// filtro aplicado en la app, no en SQL"));
            assertTrue(sql.contains("WHERE activo = true"));
        }
    }

    @Nested
    @DisplayName("Estilo de documentación en el código fuente")
    class EstiloDocumentacion {

        @Test
        void formatearNombreUsaComentariosMarkdownTripleSlash() throws IOException {
            String fuente = leerFuente();
            assertTrue(MARKDOWN_DOC.matcher(fuente).find(),
                    "formatearNombre debe ir precedido de al menos 2 líneas /// (Markdown docs, JEP 467)");

            List<String> lineasMarkdown = lineasMarkdownAntesDe(fuente, "formatearNombre");
            assertTrue(lineasMarkdown.stream().anyMatch(l -> l.contains("`")),
                    "el comentario /// debe usar backticks de Markdown, p.ej. `Ana`");
            assertTrue(lineasMarkdown.stream().anyMatch(l -> l.contains("@param")),
                    "el comentario /// debe incluir @param");
            assertTrue(lineasMarkdown.stream().anyMatch(l -> l.contains("@return")),
                    "el comentario /// debe incluir @return");
        }

        @Test
        void porcentajeUsaSnippetEnJavaDocTradicional() throws IOException {
            String fuente = leerFuente();
            assertTrue(SNIPPET_JAVADOC.matcher(fuente).find(),
                    "porcentaje debe tener un JavaDoc /** ... */ que contenga {@snippet (JEP 413)");
        }
    }

    private static String leerFuente() throws IOException {
        Path path = Path.of("src/main/java/pe/joedayz/modulo2/ejercicio06/ComentariosModernos.java");
        assertTrue(Files.exists(path), "no se encontró el fuente en " + path.toAbsolutePath());
        return Files.readString(path);
    }

    private static List<String> lineasMarkdownAntesDe(String fuente, String nombreMetodo) {
        Pattern firma = Pattern.compile(
                "public\\s+static\\s+\\S+\\s+" + Pattern.quote(nombreMetodo) + "\\s*\\(");
        Matcher matcher = firma.matcher(fuente);
        assertTrue(matcher.find(), "no se encontró el método " + nombreMetodo);
        String antes = fuente.substring(Math.max(0, matcher.start() - 600), matcher.start());
        return antes.lines()
                .map(String::stripLeading)
                .filter(l -> l.startsWith("///"))
                .toList();
    }
}
