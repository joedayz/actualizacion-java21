package pe.joedayz.modulo6.ejercicio03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 03 — Spring Boot 3")
class SpringBoot3ExercisesTest {

  @Test
  void soportaJava21() {
    assertTrue(SpringBoot3Exercises.soportaJava21("3.2.0"));
    assertTrue(SpringBoot3Exercises.soportaJava21("3.4.1"));
    assertFalse(SpringBoot3Exercises.soportaJava21("3.1.5"));
    assertFalse(SpringBoot3Exercises.soportaJava21("2.7.18"));
  }

  @Test
  void usaJakarta() {
    assertTrue(SpringBoot3Exercises.usaJakarta("3.0.0"));
    assertTrue(SpringBoot3Exercises.usaJakarta("3.4.1"));
    assertFalse(SpringBoot3Exercises.usaJakarta("2.7.18"));
  }

  @Test
  void versionMinimaJava() {
    assertEquals("17", SpringBoot3Exercises.versionMinimaJava("3.2.0"));
    assertEquals("8", SpringBoot3Exercises.versionMinimaJava("2.7.18"));
  }
}
