import java.util.Date;

/**
 * Demo: uso de APIs deprecadas típicas en código Java 8.
 * Analizar con: {@code jdeprscan --for-removal ...}
 */
public class UsaApiDeprecada {

    public static void main(String[] args) {
        @SuppressWarnings("deprecation")
        Date epoch = new Date(70, 0, 1); // constructor deprecado
        System.out.println("Fecha legacy: " + epoch);
        System.out.println("Ejecuta jdeprscan sobre el JAR de demos.");
    }
}
