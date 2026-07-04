package pe.joedayz.modulo4.ejercicio03;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Ejercicio 03 — JFR programático")
class JfrRecordingExercisesTest {

    @Test
    void grabarGeneraArchivoJfr(@TempDir Path tempDir) throws Exception {
        Path destino = tempDir.resolve("test.jfr");
        JfrRecordingExercises.grabar(destino, Duration.ofMillis(500));
        assertTrue(Files.exists(destino));
        assertTrue(Files.size(destino) > 0, "el archivo JFR debe tener contenido");
    }
}
