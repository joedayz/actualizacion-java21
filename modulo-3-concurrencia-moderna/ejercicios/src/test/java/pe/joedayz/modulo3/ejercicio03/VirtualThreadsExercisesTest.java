package pe.joedayz.modulo3.ejercicio03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 03 — Virtual Threads")
class VirtualThreadsExercisesTest {

    @Test
    void crearExecutorVirtualUsaVirtualThreads() throws Exception {
        try (ExecutorService executor = VirtualThreadsExercises.crearExecutorVirtual()) {
            Future<Boolean> future = executor.submit(() -> Thread.currentThread().isVirtual());
            assertTrue(future.get(2, TimeUnit.SECONDS));
        }
    }

    @Test
    void ejecutarEnVirtualDevuelveResultado() throws Exception {
        assertEquals(42, VirtualThreadsExercises.ejecutarEnVirtual(() -> 42));
    }

    @Test
    void ejecutarEnVirtualCorreEnVt() throws Exception {
        assertTrue(VirtualThreadsExercises.ejecutarEnVirtual(() -> Thread.currentThread().isVirtual()));
    }

    @Test
    void correEnVirtualThread() throws Exception {
        assertTrue(VirtualThreadsExercises.correEnVirtualThread());
    }

    @Test
    void procesarEnVirtualesOrdenYResultado() throws Exception {
        List<String> resultado = VirtualThreadsExercises.procesarEnVirtuales(
                List.of("a", "b", "c"),
                String::toUpperCase);
        assertEquals(List.of("A", "B", "C"), resultado);
    }

    @Test
    void procesarEnVirtualesUsaVirtualThreads() throws Exception {
        AtomicInteger virtuales = new AtomicInteger();
        VirtualThreadsExercises.procesarEnVirtuales(List.of("1", "2", "3", "4"), valor -> {
            if (Thread.currentThread().isVirtual()) {
                virtuales.incrementAndGet();
            }
            return valor;
        });
        assertEquals(4, virtuales.get());
    }
}
