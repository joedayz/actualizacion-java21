/**
 * Código legado: concatenación ineficiente en bucle caliente.
 * Punto de partida del laboratorio (sin Maven).
 */
public class LegacyAllocationService {

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        String resultado = generarReporte(5_000);
        long ms = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("Longitud=" + resultado.length() + " tiempo=" + ms + " ms");
        System.out.println("Prefijo=" + resultado.substring(0, Math.min(40, resultado.length())));
    }

    /**
     * Genera un reporte concatenando líneas. Anti-patrón: {@code result += linea}
     * crea un String nuevo en cada iteración.
     */
    static String generarReporte(int lineas) {
        String result = "";
        for (int i = 0; i < lineas; i++) {
            result += "linea-" + i + ";";
        }
        return result;
    }
}
