// Demo: compatibilidad hacia atrás
// Este mismo archivo se compila con distintos niveles de --release
// para mostrar en vivo qué se rompe al saltar de Java 8 a Java 21.
public class Demo {

    public static void main(String[] args) {
        // "var" (inferencia de tipos) llegó en Java 10 -> NO compila con --release 8
        var mensaje = construirMensaje("equipo");

        // Text Blocks llegaron en Java 15 -> NO compila con --release 8, 11 o 14
        var plantilla = """
                Hola %s,
                Este texto usa un Text Block (Java 15+).
                """.formatted("equipo");

        System.out.println(mensaje);
        System.out.println(plantilla);
    }

    private static String construirMensaje(String nombre) {
        return "Hola " + nombre + ", este mensaje usa 'var' (Java 10+)";
    }
}
