/**
 * Referencia del laboratorio — versión optimizada con StringBuilder.
 */
public class SolucionOptimizada {

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        String resultado = generarReporte(5_000);
        long ms = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("Longitud=" + resultado.length() + " tiempo=" + ms + " ms");
        System.out.println("Prefijo=" + resultado.substring(0, Math.min(40, resultado.length())));
    }

    static String generarReporte(int lineas) {
        StringBuilder sb = new StringBuilder(lineas * 12);
        for (int i = 0; i < lineas; i++) {
            sb.append("linea-").append(i).append(';');
        }
        return sb.toString();
    }
}
