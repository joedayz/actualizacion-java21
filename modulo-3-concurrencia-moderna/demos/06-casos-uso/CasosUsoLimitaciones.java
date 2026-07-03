/**
 * Demo de cierre: cuándo usar cada herramienta.
 * Ejecutar: java CasosUsoLimitaciones.java
 */
public class CasosUsoLimitaciones {

    public static void main(String[] args) {
        System.out.println("""
                === Mapa rápido (Java 21) ===

                1) Muchas operaciones I/O bloqueantes
                   → Virtual Threads (Executors.newVirtualThreadPerTaskExecutor)

                2) Cálculo CPU intensivo (compresión, crypto, ML)
                   → Pool fijo ≈ cantidad de núcleos
                     Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

                3) Componer pasos asíncronos / pipelines
                   → CompletableFuture (pasando un Executor adecuado)

                4) Varias subtareas de UN mismo caso de uso (request)
                   → Structured Concurrency (preview en 21)

                5) Contexto de request (trace id, usuario)
                   → Scoped Values (preview en 21), mejor que abusar de ThreadLocal

                === Limitaciones a recordar ===

                - Virtual threads no aceleran CPU-bound: el límite es la CPU.
                - synchronized + I/O largo puede hacer pinning del VT al carrier (Java 21).
                - Structured / Scoped requieren --enable-preview en Java 21.
                - Un pool JDBC o un rate-limit externo sigue siendo un cuello de botella.
                - Migración típica: cambiar el executor, no reescribir todo a reactive.
                """);
    }
}
