import java.util.concurrent.StructuredTaskScope;

/**
 * Demo: Scoped Values (preview en Java 21).
 *
 * Ejecutar:
 *   java --source 21 --enable-preview ScopedValuesDemo.java
 */
public class ScopedValuesDemo {

    static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();
    static final ScopedValue<String> USUARIO = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        System.out.println("=== Binding de un request id ===");
        ScopedValue.where(REQUEST_ID, "req-1001").run(() -> {
            System.out.println("en handler: " + REQUEST_ID.get());
            servicioDominio();
        });

        System.out.println();
        System.out.println("=== Fuera del where no hay valor (isBound=false) ===");
        System.out.println("bound=" + REQUEST_ID.isBound());

        System.out.println();
        System.out.println("=== Herencia a subtareas (structured concurrency) ===");
        ScopedValue.where(REQUEST_ID, "req-2002")
                .where(USUARIO, "ana")
                .run(() -> {
                    try {
                        System.out.println(trabajoParalelo());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    static void servicioDominio() {
        System.out.println("en dominio: " + REQUEST_ID.get());
    }

    static String trabajoParalelo() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var a = scope.fork(() -> "A[" + REQUEST_ID.get() + "," + USUARIO.get() + "]");
            var b = scope.fork(() -> "B[" + REQUEST_ID.get() + "," + USUARIO.get() + "]");
            scope.join();
            scope.throwIfFailed();
            return a.get() + " + " + b.get();
        }
    }
}
