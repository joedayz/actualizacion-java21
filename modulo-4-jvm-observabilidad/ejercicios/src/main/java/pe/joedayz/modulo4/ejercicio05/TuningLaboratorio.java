package pe.joedayz.modulo4.ejercicio05;

/**
 * Ejercicio 05 — Laboratorio: reducir allocations en código legado.
 *
 * <p>Misma lógica de negocio que el laboratorio {@code LegacyAllocationService}:
 * generar {@code lineas} entradas con formato {@code "linea-{i};"}.
 */
public final class TuningLaboratorio {

    private TuningLaboratorio() {}

    /**
     * TODO: reemplazar la concatenación naive por una estrategia eficiente (StringBuilder).
     *
     * <p>Debe generar {@code lineas} entradas con formato {@code "linea-{i};"}.
     */
    public static String generarReporte(int lineas) {
        throw new UnsupportedOperationException("TODO: generarReporte");
    }

    /** Referencia naive (solo para comparar en demos/JFR). No usar en producción. */
    static String generarReporteNaive(int lineas) {
        String result = "";
        for (int i = 0; i < lineas; i++) {
            result += "linea-" + i + ";";
        }
        return result;
    }
}
