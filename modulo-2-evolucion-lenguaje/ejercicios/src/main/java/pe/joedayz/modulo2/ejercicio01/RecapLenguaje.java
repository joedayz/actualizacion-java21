package pe.joedayz.modulo2.ejercicio01;

import java.util.Map;

/**
 * Ejercicio 01 — Recap de la sesión anterior:
 * {@code var}, switch expressions y text blocks.
 *
 * <p>Completa cada método. Los tests en {@code RecapLenguajeTest} deben pasar.
 */
public final class RecapLenguaje {

    private RecapLenguaje() {}

    public enum DiaSemana {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    }

    /**
     * Clasifica el día usando un <strong>switch expression</strong> (flecha {@code ->}).
     * <ul>
     *   <li>MARTES → {@code "Laborable - Promoción 2x1"}</li>
     *   <li>LUNES, MIERCOLES, JUEVES, VIERNES → {@code "Laborable"}</li>
     *   <li>SABADO, DOMINGO → {@code "Fin de semana"}</li>
     * </ul>
     * Debe ser exhaustivo (sin {@code default} innecesario).
     */
    public static String tipoDeDia(DiaSemana dia) {
        // TODO: implementar con switch expression
        throw new UnsupportedOperationException("TODO: tipoDeDia");
    }

    /**
     * Horas de trabajo del día, usando switch expression.
     * Si el cuerpo de una rama necesita más de una línea, usa bloque + {@code yield}.
     * <ul>
     *   <li>LUNES–JUEVES → 8</li>
     *   <li>VIERNES → 6 (8 base menos 2 de home office; calcula con variables locales)</li>
     *   <li>SABADO, DOMINGO → 0</li>
     * </ul>
     */
    public static int horasDeTrabajo(DiaSemana dia) {
        // TODO: implementar; en VIERNES usa bloque + yield
        throw new UnsupportedOperationException("TODO: horasDeTrabajo");
    }

    /**
     * Construye un fragmento SQL con un <strong>text block</strong> y {@code formatted}.
     * El resultado (tras {@code strip()}) debe ser exactamente:
     * <pre>
     * SELECT id, nombre
     * FROM clientes
     * WHERE activo = true
     * ORDER BY nombre
     * </pre>
     * cuando {@code tabla = "clientes"} y {@code columnas = "id, nombre"}.
     */
    public static String consultaActivos(String tabla, String columnas) {
        // TODO: text block + .formatted(columnas, tabla)
        throw new UnsupportedOperationException("TODO: consultaActivos");
    }

    /**
     * Devuelve un mapa inmutable región → total usando {@code Map.of} y {@code var} localmente.
     * Contenido esperado: Norte=15000, Sur=9800, Centro=21000.
     */
    public static Map<String, Integer> totalesPorRegion() {
        // TODO: var + Map.of(...)
        throw new UnsupportedOperationException("TODO: totalesPorRegion");
    }
}
