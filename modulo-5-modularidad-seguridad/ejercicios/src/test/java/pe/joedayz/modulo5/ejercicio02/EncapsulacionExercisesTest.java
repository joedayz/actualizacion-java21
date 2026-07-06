package pe.joedayz.modulo5.ejercicio02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 02 — Encapsulación JPMS")
class EncapsulacionExercisesTest {

  @Test
  void detectaPaquetesInternos() {
    assertTrue(EncapsulacionExercises.esPaqueteInternoJdk("jdk.internal.misc.Unsafe"));
    assertTrue(EncapsulacionExercises.esPaqueteInternoJdk("sun.misc.Unsafe"));
    assertTrue(EncapsulacionExercises.esPaqueteInternoJdk("com.sun.crypto.provider.SunJCE"));
    assertFalse(EncapsulacionExercises.esPaqueteInternoJdk("java.util.ArrayList"));
  }

  @Test
  void construirAddOpens() {
    assertEquals(
        "--add-opens java.base/java.lang=ALL-UNNAMED",
        EncapsulacionExercises.construirAddOpens("java.base", "java.lang", "ALL-UNNAMED"));
  }

  @Test
  void exportsPublicoPermiteLectura() {
    String moduleInfo = """
        module com.util {
            exports com.util.api;
            exports com.util.internal to com.admin;
        }
        """;
    assertTrue(EncapsulacionExercises.puedeLeerseConExports(
        "com.util", "com.util.api", "com.cliente", moduleInfo));
    assertFalse(EncapsulacionExercises.puedeLeerseConExports(
        "com.util", "com.util.internal", "com.cliente", moduleInfo));
    assertTrue(EncapsulacionExercises.puedeLeerseConExports(
        "com.util", "com.util.internal", "com.admin", moduleInfo));
  }
}
