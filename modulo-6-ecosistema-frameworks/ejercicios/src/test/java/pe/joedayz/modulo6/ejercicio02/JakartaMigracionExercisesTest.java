package pe.joedayz.modulo6.ejercicio02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 02 — Migración javax → jakarta")
class JakartaMigracionExercisesTest {

  @Test
  void migrarTipo() {
    assertEquals("jakarta.persistence.Entity",
        JakartaMigracionExercises.migrarTipo("javax.persistence.Entity"));
    assertEquals("jakarta.servlet.http.HttpServlet",
        JakartaMigracionExercises.migrarTipo("javax.servlet.http.HttpServlet"));
    assertEquals("javax.sql.DataSource",
        JakartaMigracionExercises.migrarTipo("javax.sql.DataSource"));
  }

  @Test
  void requiereMigracionJakarta() {
    assertTrue(JakartaMigracionExercises.requiereMigracionJakarta("javax.persistence.Entity"));
    assertTrue(JakartaMigracionExercises.requiereMigracionJakarta("javax.validation.constraints.NotNull"));
    assertFalse(JakartaMigracionExercises.requiereMigracionJakarta("javax.sql.DataSource"));
    assertFalse(JakartaMigracionExercises.requiereMigracionJakarta("javax.crypto.Cipher"));
  }

  @Test
  void coordenadaMavenJakarta() {
    assertEquals("jakarta.persistence:jakarta.persistence-api",
        JakartaMigracionExercises.coordenadaMavenJakarta("javax.persistence:javax.persistence-api"));
    assertEquals("jakarta.servlet:jakarta.servlet-api",
        JakartaMigracionExercises.coordenadaMavenJakarta("javax.servlet:javax.servlet-api"));
  }
}
