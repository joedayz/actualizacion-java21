package pe.joedayz.modulo5.ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 01 — module-info simplificado")
class ModuleInfoExercisesTest {

  private static final String SAMPLE = """
      module com.empresa.pedidos {
          requires java.sql;
          requires transitive java.logging;
          exports com.empresa.pedidos.api;
          opens com.empresa.pedidos.dto to com.fasterxml.jackson.databind;
      }
      """;

  @Test
  void extraerNombreModulo() {
    assertEquals("com.empresa.pedidos", ModuleInfoExercises.extraerNombreModulo(SAMPLE));
  }

  @Test
  void extraerRequires() {
    assertEquals(List.of("java.logging", "java.sql"), ModuleInfoExercises.extraerRequires(SAMPLE));
  }

  @Test
  void extraerExports() {
    assertEquals(List.of("com.empresa.pedidos.api"), ModuleInfoExercises.extraerExports(SAMPLE));
  }
}
