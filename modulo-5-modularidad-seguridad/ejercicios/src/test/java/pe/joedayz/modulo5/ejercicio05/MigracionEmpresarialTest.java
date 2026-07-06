package pe.joedayz.modulo5.ejercicio05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 05 — Laboratorio migración empresarial")
class MigracionEmpresarialTest {

  @Test
  void detectaInternos() {
    assertTrue(MigracionEmpresarial.esPaqueteInternoJdk("jdk.internal.misc.Unsafe"));
    assertFalse(MigracionEmpresarial.esPaqueteInternoJdk("java.net.http.HttpClient"));
  }

  @Test
  void reemplazoJaxb() {
    assertEquals("jakarta.xml.bind:jakarta.xml.bind-api",
        MigracionEmpresarial.reemplazoMaven("javax.xml.bind.JAXBContext"));
  }

  @Test
  void protocolosSeguros() {
    assertTrue(MigracionEmpresarial.esProtocoloSeguro("TLSv1.2"));
    assertTrue(MigracionEmpresarial.esProtocoloSeguro("TLSv1.3"));
    assertFalse(MigracionEmpresarial.esProtocoloSeguro("TLSv1"));
    assertFalse(MigracionEmpresarial.esProtocoloSeguro("TLSv1.1"));
  }
}
