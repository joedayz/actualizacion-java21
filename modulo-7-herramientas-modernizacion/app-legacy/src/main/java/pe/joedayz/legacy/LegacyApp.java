package pe.joedayz.legacy;

import java.util.Date;
import java.util.Vector;

/**
 * Mini aplicación "legacy" para practicar análisis automatizado
 * (jdeps, jdeprscan, OpenRewrite, Inspect Code).
 */
public class LegacyApp {

    public static void main(String[] args) {
        System.out.println(saludo("Conastec"));
        System.out.println("Epoch legacy: " + fechaEpoch());
        System.out.println("Items: " + listaLegacy().size());
        System.out.println("API interna (nombre): " + apiInternaReferenciada());
    }

    static String saludo(String nombre) {
        return "Hola, " + nombre;
    }

    /** Uso intencional de constructor deprecado de {@link Date}. */
    @SuppressWarnings("deprecation")
    static Date fechaEpoch() {
        return new Date(70, 0, 1);
    }

    /** {@link Vector} sincronizado: patrón pre-Java 8 Collections. */
    static Vector<String> listaLegacy() {
        Vector<String> v = new Vector<>();
        v.add("pedido");
        v.add("cliente");
        return v;
    }

    /**
     * Solo el nombre de la API interna (string) — no importa el tipo,
     * para que el JAR compile en JDK 21 sin {@code --add-exports}.
     * El demo {@code UsaUnsafeLegacy} sí importa {@code sun.misc.Unsafe}.
     */
    static String apiInternaReferenciada() {
        return "sun.misc.Unsafe";
    }
}
