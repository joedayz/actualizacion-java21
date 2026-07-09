import sun.misc.Unsafe;

/**
 * Demo intencional: uso de API interna del JDK (patrón legacy Java 8).
 * Compilar y analizar con: {@code jdeps --jdk-internals ...}
 */
public class UsaUnsafeLegacy {

    public static void main(String[] args) {
        Unsafe unsafe = getUnsafe();
        System.out.println("Unsafe disponible: " + (unsafe != null));
        System.out.println("Ejecuta jdeps --jdk-internals sobre el JAR de demos.");
    }

    @SuppressWarnings("restriction")
    private static Unsafe getUnsafe() {
        try {
            var field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (ReflectiveOperationException e) {
            System.err.println("No se pudo obtener Unsafe: " + e.getMessage());
            return null;
        }
    }
}
