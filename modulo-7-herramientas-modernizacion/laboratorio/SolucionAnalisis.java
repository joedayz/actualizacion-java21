/**
 * Referencia: clasificación esperada tras analizar la app legacy.
 */
public class SolucionAnalisis {

    public static void main(String[] args) {
        System.out.println("Unsafe / jdk.unsupported → CRITICO");
        System.out.println("Date deprecated → DEPRECADO");
        System.out.println("Solo java.base → OK");
        System.out.println("Pipeline: jdeps → jdeprscan → OpenRewrite → SonarQube");
    }
}
