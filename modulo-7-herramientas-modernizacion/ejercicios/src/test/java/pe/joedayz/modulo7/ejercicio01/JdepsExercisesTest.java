package pe.joedayz.modulo7.ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 01 — jdeps")
class JdepsExercisesTest {

  private static final String SALIDA_INTERNA = """
      modulo-7-demos.jar -> jdk.unsupported
         UsaUnsafeLegacy -> sun.misc.Unsafe    JDK internal API (jdk.unsupported)
      """;

  private static final String SALIDA_OK = """
      modulo-7-app-legacy.jar -> java.base
         pe.joedayz.legacy -> java.lang
         pe.joedayz.legacy -> java.util
      """;

  @Test
  void tieneApisInternas() {
    assertTrue(JdepsExercises.tieneApisInternas(SALIDA_INTERNA));
    assertFalse(JdepsExercises.tieneApisInternas(SALIDA_OK));
    assertFalse(JdepsExercises.tieneApisInternas(""));
  }

  @Test
  void extraerApisInternas() {
    assertEquals(List.of("sun.misc.Unsafe"), JdepsExercises.extraerApisInternas(SALIDA_INTERNA));
    assertTrue(JdepsExercises.extraerApisInternas(SALIDA_OK).isEmpty());
  }

  @Test
  void severidad() {
    assertEquals("CRITICO", JdepsExercises.severidad("UsaUnsafeLegacy -> sun.misc.Unsafe"));
    assertEquals("OK", JdepsExercises.severidad("pe.joedayz.legacy -> java.base"));
    assertEquals("REVISAR", JdepsExercises.severidad("algo desconocido"));
  }
}
