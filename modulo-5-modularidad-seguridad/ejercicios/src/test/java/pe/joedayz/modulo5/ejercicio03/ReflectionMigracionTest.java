package pe.joedayz.modulo5.ejercicio03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 03 — Migración de reflection")
class ReflectionMigracionTest {

  @Test
  void codigoPropioDebeUsarApiPublica() {
    assertEquals(ReflectionMigracion.EstrategiaMigracion.USAR_API_PUBLICA,
        ReflectionMigracion.recomendar("CODIGO_PROPIO", false));
  }

  @Test
  void libreriaConVersionNueva() {
    assertEquals(ReflectionMigracion.EstrategiaMigracion.ACTUALIZAR_DEPENDENCIA,
        ReflectionMigracion.recomendar("LIBRERIA_TERCEROS", true));
  }

  @Test
  void libreriaSinVersionNueva() {
    assertEquals(ReflectionMigracion.EstrategiaMigracion.ADD_OPENS_TEMPORAL,
        ReflectionMigracion.recomendar("LIBRERIA_TERCEROS", false));
  }

  @Test
  void frameworkPuenteDocumentado() {
    assertEquals(ReflectionMigracion.EstrategiaMigracion.ADD_OPENS_TEMPORAL,
        ReflectionMigracion.recomendar("FRAMEWORK", true));
  }

  @Test
  void sugerirJaxb() {
    assertEquals("jakarta.xml.bind:jakarta.xml.bind-api",
        ReflectionMigracion.sugerirDependenciaMaven("javax.xml.bind.JAXBContext"));
  }

  @Test
  void sugerirAnnotation() {
    assertEquals("jakarta.annotation:jakarta.annotation-api",
        ReflectionMigracion.sugerirDependenciaMaven("javax.annotation.PostConstruct"));
  }
}
