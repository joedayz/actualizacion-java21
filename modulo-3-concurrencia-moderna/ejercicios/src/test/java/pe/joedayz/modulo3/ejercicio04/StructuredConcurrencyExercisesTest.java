package pe.joedayz.modulo3.ejercicio04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 04 — Structured Concurrency y Scoped Values")
class StructuredConcurrencyExercisesTest {

    @Test
    void cargarUsuarioYPedidoEnParalelo() throws Exception {
        String resultado = StructuredConcurrencyExercises.cargarUsuarioYPedido(
                () -> {
                    Thread.sleep(50);
                    return "ana";
                },
                () -> {
                    Thread.sleep(50);
                    return "orden-1";
                });
        assertEquals("ana|orden-1", resultado);
    }

    @Test
    void cargarUsuarioYPedidoPropagaError() {
        Exception error = assertThrows(Exception.class, () ->
                StructuredConcurrencyExercises.cargarUsuarioYPedido(
                        () -> "ana",
                        () -> {
                            throw new IllegalStateException("pedido roto");
                        }));
        assertTrue(contieneMensaje(error, "pedido roto"));
    }

    @Test
    void conRequestIdBind() {
        String valor = StructuredConcurrencyExercises.conRequestId("req-9",
                () -> StructuredConcurrencyExercises.REQUEST_ID.get());
        assertEquals("req-9", valor);
    }

    @Test
    void cargarConContextoLeeScopedValue() throws Exception {
        String resultado = StructuredConcurrencyExercises.conRequestId("req-42", () -> {
            try {
                return StructuredConcurrencyExercises.cargarConContexto(
                        () -> "ana",
                        () -> "orden-7");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        assertEquals("req-42:ana|orden-7", resultado);
    }

    private static boolean contieneMensaje(Throwable error, String fragmento) {
        Throwable actual = error;
        while (actual != null) {
            if (actual.getMessage() != null && actual.getMessage().contains(fragmento)) {
                return true;
            }
            actual = actual.getCause();
        }
        return false;
    }
}
