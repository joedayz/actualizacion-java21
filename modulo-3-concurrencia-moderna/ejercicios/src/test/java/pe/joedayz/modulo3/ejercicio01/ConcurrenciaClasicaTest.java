package pe.joedayz.modulo3.ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 01 — Concurrencia clásica (ExecutorService)")
class ConcurrenciaClasicaTest {

    @Test
    void ejecutarTodasRespetaOrden() throws Exception {
        List<Callable<String>> tareas = List.of(
                () -> "A",
                () -> "B",
                () -> "C"
        );
        assertEquals(List.of("A", "B", "C"), ConcurrenciaClasica.ejecutarTodas(tareas, 2));
    }

    @Test
    void ejecutarTodasCorreEnParalelo() throws Exception {
        AtomicInteger enCurso = new AtomicInteger();
        AtomicInteger maxEnCurso = new AtomicInteger();

        Callable<Integer> tarea = () -> {
            int actual = enCurso.incrementAndGet();
            maxEnCurso.updateAndGet(prev -> Math.max(prev, actual));
            Thread.sleep(80);
            enCurso.decrementAndGet();
            return 1;
        };

        List<Callable<Integer>> tareas = List.of(tarea, tarea, tarea, tarea);
        ConcurrenciaClasica.ejecutarTodas(tareas, 3);

        // Con pool de 3, al menos 2 deben solaparse (en la práctica llegan a 3)
        org.junit.jupiter.api.Assertions.assertTrue(maxEnCurso.get() >= 2,
                "se esperaba solapamiento de tareas, maxEnCurso=" + maxEnCurso.get());
    }

    @Test
    void sumarEnParalelo() throws Exception {
        assertEquals(15, ConcurrenciaClasica.sumarEnParalelo(List.of(1, 2, 3, 4, 5), 3));
        assertEquals(0, ConcurrenciaClasica.sumarEnParalelo(List.of(), 2));
    }
}
