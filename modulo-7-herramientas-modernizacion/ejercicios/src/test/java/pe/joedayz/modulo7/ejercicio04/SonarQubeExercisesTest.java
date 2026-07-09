package pe.joedayz.modulo7.ejercicio04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 04 — SonarQube y Revapi")
class SonarQubeExercisesTest {

  @Test
  void qualityGatePasa() {
    assertTrue(SonarQubeExercises.qualityGatePasa(0, 0));
    assertFalse(SonarQubeExercises.qualityGatePasa(1, 0));
    assertFalse(SonarQubeExercises.qualityGatePasa(0, 2));
  }

  @Test
  void clasificarCambioApi() {
    assertEquals("BREAKING", SonarQubeExercises.clasificarCambioApi("method removed: deleteAll()"));
    assertEquals("BREAKING", SonarQubeExercises.clasificarCambioApi("added method to interface Listener"));
    assertEquals("DEPRECACION", SonarQubeExercises.clasificarCambioApi("method deprecated: findLegacy()"));
    assertEquals("COMPATIBLE", SonarQubeExercises.clasificarCambioApi("added overload find(UUID)"));
  }

  @Test
  void herramientaPara() {
    assertEquals("REVAPI", SonarQubeExercises.herramientaPara("detectar breaking changes de API"));
    assertEquals("SONAR", SonarQubeExercises.herramientaPara("bugs y vulnerabilidades en el PR"));
  }
}
