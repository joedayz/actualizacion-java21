package pe.joedayz.modulo7.ejercicio02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 02 — jdeprscan")
class JdeprscanExercisesTest {

  @Test
  void esDeprecado() {
    assertTrue(JdeprscanExercises.esDeprecado(
        "class pe/joedayz/legacy/LegacyApp uses deprecated method java/util/Date.<init>(III)V"));
    assertFalse(JdeprscanExercises.esDeprecado("no findings"));
  }

  @Test
  void esForRemoval() {
    assertTrue(JdeprscanExercises.esForRemoval(
        "java.lang.Thread.stop() is deprecated for removal"));
    assertFalse(JdeprscanExercises.esForRemoval(
        "java.util.Date.<init>(III)V is deprecated"));
  }

  @Test
  void prioridad() {
    assertEquals("ALTA", JdeprscanExercises.prioridad("deprecated for removal"));
    assertEquals("MEDIA", JdeprscanExercises.prioridad("uses deprecated method"));
    assertEquals("BAJA", JdeprscanExercises.prioridad("clean scan"));
  }
}
