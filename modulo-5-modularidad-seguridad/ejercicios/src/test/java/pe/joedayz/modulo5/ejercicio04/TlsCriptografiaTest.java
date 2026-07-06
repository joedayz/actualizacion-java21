package pe.joedayz.modulo5.ejercicio04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 04 — TLS y criptografía")
class TlsCriptografiaTest {

  @Test
  void protocolosInseguros() {
    assertEquals(List.of("SSLv3", "TLSv1", "TLSv1.1"), TlsCriptografia.protocolosInseguros());
  }

  @Test
  void protocolosRecomendados() {
    assertEquals(List.of("TLSv1.2", "TLSv1.3"), TlsCriptografia.protocolosRecomendados());
  }

  @Test
  void filtrarProtocolosSeguros() {
    List<String> entrada = List.of("TLSv1", "TLSv1.2", "TLSv1.3", "TLSv1.1");
    assertEquals(List.of("TLSv1.2", "TLSv1.3"), TlsCriptografia.filtrarProtocolosSeguros(entrada));
  }

  @Test
  void tls12EsSeguro() {
    assertTrue(TlsCriptografia.protocolosRecomendados().contains("TLSv1.2"));
    assertFalse(TlsCriptografia.protocolosInseguros().contains("TLSv1.2"));
  }
}
