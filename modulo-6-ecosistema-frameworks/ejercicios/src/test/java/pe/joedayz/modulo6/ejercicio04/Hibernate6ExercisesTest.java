package pe.joedayz.modulo6.ejercicio04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 04 — Hibernate 6")
class Hibernate6ExercisesTest {

  @Test
  void migrarDialecto() {
    assertEquals("org.hibernate.dialect.MySQLDialect",
        Hibernate6Exercises.migrarDialecto("org.hibernate.dialect.MySQL5Dialect"));
    assertEquals("org.hibernate.dialect.PostgreSQLDialect",
        Hibernate6Exercises.migrarDialecto("org.hibernate.dialect.PostgreSQL95Dialect"));
    assertEquals("org.hibernate.dialect.MySQLDialect",
        Hibernate6Exercises.migrarDialecto("org.hibernate.dialect.MySQLDialect"));
  }

  @Test
  void esPropiedadDeprecada() {
    assertTrue(Hibernate6Exercises.esPropiedadDeprecada("hibernate.id.new_generator_mappings"));
    assertTrue(Hibernate6Exercises.esPropiedadDeprecada("hibernate.jdbc.lob.non_contextual_creation"));
    assertFalse(Hibernate6Exercises.esPropiedadDeprecada("hibernate.dialect"));
  }

  @Test
  void namespaceJpa() {
    assertEquals("jakarta.persistence", Hibernate6Exercises.namespaceJpa());
  }
}
