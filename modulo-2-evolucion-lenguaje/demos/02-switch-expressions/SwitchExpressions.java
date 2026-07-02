public class SwitchExpressions {

    enum DiaSemana { LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO }

    public static void main(String[] args) {
        for (DiaSemana dia : DiaSemana.values()) {
            System.out.println(dia + " (clásico)   -> " + tipoDeDiaClasico(dia));
            System.out.println(dia + " (moderno)   -> " + tipoDeDiaModerno(dia));
        }

        System.out.println();
        System.out.println("Horas de trabajo (switch con bloque + yield):");
        for (DiaSemana dia : DiaSemana.values()) {
            System.out.println(dia + " -> " + horasDeTrabajo(dia) + "h");
        }
    }

    // Regla de negocio esperada: los martes hay "Promoción 2x1", el resto de
    // días laborables son normales, y el fin de semana no se trabaja.

    // ===== ANTES (Java 8): switch clásico, sentencia =====
    // Bug clásico incluido a propósito: el desarrollador quiso marcar el martes
    // como "Promoción 2x1" pero olvidó el "break" -> el valor se sobreescribe
    // al caer (fall-through) en el case MIERCOLES. Resultado: el martes se
    // reporta como un día laborable normal, perdiendo la promoción.
    static String tipoDeDiaClasico(DiaSemana dia) {
        String resultado;
        switch (dia) {
            case LUNES:
                resultado = "Laborable";
                break;
            case MARTES:
                resultado = "Laborable - Promoción 2x1";
                // uy, se olvidó el "break" aquí -> cae al siguiente caso (bug real y común en Java 8)
            case MIERCOLES:
            case JUEVES:
            case VIERNES:
                resultado = "Laborable";
                break;
            case SABADO:
            case DOMINGO:
                resultado = "Fin de semana";
                break;
            default:
                resultado = "Desconocido";
        }
        return resultado;
    }

    // ===== AHORA (Java 14+): switch expression =====
    // - Es una EXPRESIÓN: se puede asignar/retornar directamente.
    // - Sintaxis de flecha: cada rama termina ahí, sin fall-through posible.
    // - Múltiples etiquetas por rama.
    // - Exhaustivo: si falta un caso del enum (y no hay default), NO COMPILA.
    static String tipoDeDiaModerno(DiaSemana dia) {
        return switch (dia) {
            case LUNES, MIERCOLES, JUEVES, VIERNES -> "Laborable";
            case MARTES -> "Laborable - Promoción 2x1";
            case SABADO, DOMINGO -> "Fin de semana";
        };
    }

    // Switch expression con bloque + yield, cuando se necesita más de una línea
    static int horasDeTrabajo(DiaSemana dia) {
        return switch (dia) {
            case LUNES, MARTES, MIERCOLES, JUEVES -> 8;
            case VIERNES -> {
                int horasBase = 8;
                int reduccionPorHomeOffice = 2;
                yield horasBase - reduccionPorHomeOffice;
            }
            case SABADO, DOMINGO -> 0;
        };
    }
}
