import sun.misc.Unsafe;

/**
 * Demo: código legacy que referencia API interna del JDK ({@code sun.misc.Unsafe}).
 * No ejecutar en producción — solo para ver salida de {@code jdeps --jdk-internals}.
 *
 * <p>Compila con warnings; luego:
 * <pre>
 *   cd modulo-5-modularidad-seguridad
 *   mvn -q package -pl demos -DskipTests
 *   jdeps --jdk-internals --multi-release 21 demos/target/modulo-5-demos-1.0.0-SNAPSHOT.jar
 * </pre>
 */
public class UsaApiInternaLegacy {

    public static void main(String[] args) throws Exception {
        var field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        System.out.println("Unsafe obtenido: " + (unsafe != null));
    }
}
