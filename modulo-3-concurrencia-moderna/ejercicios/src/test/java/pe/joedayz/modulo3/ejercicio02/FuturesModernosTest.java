package pe.joedayz.modulo3.ejercicio02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 02 — CompletableFuture")
class FuturesModernosTest {

    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        executor = Executors.newFixedThreadPool(4);
    }

    @AfterEach
    void tearDown() {
        executor.shutdownNow();
    }

    @Test
    void sumarAsync() throws Exception {
        assertEquals(7, FuturesModernos.sumarAsync(3, 4, executor).get(2, TimeUnit.SECONDS));
    }

    @Test
    void transformar() throws Exception {
        CompletableFuture<String> origen = CompletableFuture.completedFuture("ana");
        String resultado = FuturesModernos.transformar(origen, String::toUpperCase, executor)
                .get(2, TimeUnit.SECONDS);
        assertEquals("ANA", resultado);
    }

    @Test
    void combinar() throws Exception {
        CompletableFuture<Integer> a = CompletableFuture.completedFuture(10);
        CompletableFuture<Integer> b = CompletableFuture.completedFuture(5);
        assertEquals(15, FuturesModernos.combinar(a, b).get(2, TimeUnit.SECONDS));
    }

    @Test
    void conFallbackCuandoFalla() throws Exception {
        CompletableFuture<String> origen = CompletableFuture.failedFuture(new RuntimeException("boom"));
        assertEquals("default", FuturesModernos.conFallback(origen, "default").get(2, TimeUnit.SECONDS));
    }

    @Test
    void conFallbackCuandoOk() throws Exception {
        CompletableFuture<String> origen = CompletableFuture.completedFuture("ok");
        assertEquals("ok", FuturesModernos.conFallback(origen, "default").get(2, TimeUnit.SECONDS));
    }
}
