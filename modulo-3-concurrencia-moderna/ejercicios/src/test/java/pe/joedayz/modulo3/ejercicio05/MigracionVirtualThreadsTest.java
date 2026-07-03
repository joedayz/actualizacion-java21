package pe.joedayz.modulo3.ejercicio05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 05 — Laboratorio: migración a Virtual Threads")
class MigracionVirtualThreadsTest {

    @Nested
    @DisplayName("crearExecutor")
    class CrearExecutor {

        @Test
        void usaVirtualThreads() throws Exception {
            try (ExecutorService executor = MigracionVirtualThreads.crearExecutor()) {
                Future<Boolean> future = executor.submit(() -> Thread.currentThread().isVirtual());
                assertTrue(future.get(2, TimeUnit.SECONDS),
                        "crearExecutor() debe devolver un executor de virtual threads");
            }
        }
    }

    @Nested
    @DisplayName("consultarServicios")
    class ConsultarServicios {

        @Test
        void resultadoDeNegocioEnOrden() throws Exception {
            List<Integer> ids = List.of(1, 2, 3, 4, 5);
            assertEquals(List.of(10, 20, 30, 40, 50), MigracionVirtualThreads.consultarServicios(ids));
        }

        @Test
        void listaVacia() throws Exception {
            assertEquals(List.of(), MigracionVirtualThreads.consultarServicios(List.of()));
        }

        @Test
        void tareasCorrenEnVirtualThreads() throws Exception {
            // Instrumentamos consultarUno indirectamente: el executor de crearExecutor
            // debe usarse. Verificamos que crearExecutor es VT y que el resultado es correcto;
            // además comprobamos que al menos una tarea del flujo usa VT vía un executor propio
            // si el alumno cableó crearExecutor correctamente.
            AtomicInteger virtuales = new AtomicInteger();
            try (ExecutorService executor = MigracionVirtualThreads.crearExecutor()) {
                List<Future<Integer>> futures = List.of(1, 2, 3).stream()
                        .map(id -> executor.submit(() -> {
                            if (Thread.currentThread().isVirtual()) {
                                virtuales.incrementAndGet();
                            }
                            return MigracionVirtualThreads.consultarUno(id);
                        }))
                        .toList();
                for (Future<Integer> future : futures) {
                    future.get(2, TimeUnit.SECONDS);
                }
            }
            assertEquals(3, virtuales.get());

            // Y el método de negocio sigue respondiendo
            assertEquals(List.of(10, 20, 30), MigracionVirtualThreads.consultarServicios(List.of(1, 2, 3)));
        }
    }
}
