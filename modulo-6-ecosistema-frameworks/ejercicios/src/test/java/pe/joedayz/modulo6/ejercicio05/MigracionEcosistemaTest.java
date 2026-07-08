package pe.joedayz.modulo6.ejercicio05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 05 — Laboratorio ecosistema")
class MigracionEcosistemaTest {

  @Test
  void clasificarDependencia() {
    assertEquals("MIGRAR", MigracionEcosistema.clasificarDependencia("javax.persistence:javax.persistence-api"));
    assertEquals("MIGRAR", MigracionEcosistema.clasificarDependencia("mysql:mysql-connector-java"));
    assertEquals("OK", MigracionEcosistema.clasificarDependencia("com.mysql:mysql-connector-j"));
    assertEquals("OK", MigracionEcosistema.clasificarDependencia("org.springframework.boot:spring-boot-starter-data-jpa"));
    assertEquals("REVISAR", MigracionEcosistema.clasificarDependencia("org.hibernate:hibernate-core:5.6.15.Final"));
  }

  @Test
  void esDriverJdbcLegacy() {
    assertTrue(MigracionEcosistema.esDriverJdbcLegacy("com.mysql.jdbc.Driver"));
    assertFalse(MigracionEcosistema.esDriverJdbcLegacy("com.mysql.cj.jdbc.Driver"));
    assertFalse(MigracionEcosistema.esDriverJdbcLegacy("org.postgresql.Driver"));
  }

  @Test
  void versionSpringBootObjetivo() {
    assertEquals("3.4.1", MigracionEcosistema.versionSpringBootObjetivo());
  }
}
