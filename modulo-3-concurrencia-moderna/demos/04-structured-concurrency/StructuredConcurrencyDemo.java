import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

/**
 * Demo: Structured Concurrency (preview en Java 21).
 *
 * Ejecutar:
 *   java --source 21 --enable-preview StructuredConcurrencyDemo.java
 */
public class StructuredConcurrencyDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("=== ShutdownOnFailure: usuario + pedido en paralelo ===");
        System.out.println(cargarFicha(7));

        System.out.println();
        System.out.println("=== Si una subtarea falla, se cancela el resto ===");
        try {
            cargarFichaConError(7);
        } catch (Exception e) {
            System.out.println("Error esperado: " + e.getClass().getSimpleName()
                    + " — " + rootMessage(e));
        }
    }

    static String cargarFicha(int id) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask<String> usuario = scope.fork(() -> buscarUsuario(id));
            Subtask<String> pedido = scope.fork(() -> buscarPedido(id));

            scope.join();
            scope.throwIfFailed();

            return usuario.get() + " | " + pedido.get();
        }
    }

    static String cargarFichaConError(int id) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask<String> usuario = scope.fork(() -> buscarUsuario(id));
            Subtask<String> pedido = scope.fork(() -> {
                Thread.sleep(30);
                throw new IllegalStateException("pedido no encontrado");
            });

            scope.join();
            scope.throwIfFailed();
            return usuario.get() + " | " + pedido.get();
        }
    }

    static String buscarUsuario(int id) throws InterruptedException {
        Thread.sleep(80);
        return "user-" + id;
    }

    static String buscarPedido(int id) throws InterruptedException {
        Thread.sleep(80);
        return "order-" + id;
    }

    static String rootMessage(Throwable t) {
        Throwable actual = t;
        while (actual.getCause() != null) {
            actual = actual.getCause();
        }
        return actual.getMessage();
    }
}
