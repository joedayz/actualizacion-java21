package pe.joedayz.modulo7.ejercicio05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 05 — Laboratorio análisis legacy")
class AnalisisLegacyExercisesTest {

  @Test
  void clasificarHallazgo() {
    assertEquals("CRITICO", AnalisisLegacyExercises.clasificarHallazgo(
        "UsaUnsafeLegacy -> sun.misc.Unsafe    JDK internal API (jdk.unsupported)"));
    assertEquals("DEPRECADO", AnalisisLegacyExercises.clasificarHallazgo(
        "class LegacyApp uses deprecated method java/util/Date.<init>(III)V"));
    assertEquals("OK", AnalisisLegacyExercises.clasificarHallazgo(
        "modulo-7-app-legacy.jar -> java.base"));
  }

  @Test
  void ordenPipeline() {
    assertEquals(
        List.of("jdeps", "jdeprscan", "OpenRewrite", "SonarQube"),
        AnalisisLegacyExercises.ordenPipeline());
  }

  @Test
  void plataformaEscala() {
    assertEquals("Moderne", AnalisisLegacyExercises.plataformaEscala());
  }
}
