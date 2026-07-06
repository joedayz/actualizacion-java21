import java.lang.reflect.Field;

/**
 * Demo: encapsulación fuerte — reflection sobre API interna del JDK.
 * Ejecutar: java ReflectionIlegal.java
 *
 * En Java 17+ suele lanzar InaccessibleObjectException.
 * Prueba con el flag de migración:
 *   java --add-opens java.base/java.lang=ALL-UNNAMED ReflectionIlegal.java
 */
public class ReflectionIlegal {

    public static void main(String[] args) {
        System.out.println("Intentando acceder por reflection a un campo interno de String...");
        try {
            Field field = String.class.getDeclaredField("value");
            field.setAccessible(true);
            String s = "hola";
            Object raw = field.get(s);
            System.out.println("Acceso exitoso (inesperado en JDK moderno): " + raw);
        } catch (InaccessibleObjectException e) {
            System.out.println("InaccessibleObjectException (esperado):");
            System.out.println("  " + e.getMessage());
            System.out.println();
            System.out.println("Migración temporal:");
            System.out.println("  --add-opens java.base/java.lang=ALL-UNNAMED");
            System.out.println();
            System.out.println("Solución correcta: no usar campos internos; usar API pública.");
        } catch (ReflectiveOperationException e) {
            System.out.println("Otro error de reflection: " + e.getClass().getSimpleName());
            System.out.println("  " + e.getMessage());
        }
    }
}
