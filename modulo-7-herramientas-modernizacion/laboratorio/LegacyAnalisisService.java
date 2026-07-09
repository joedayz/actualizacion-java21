/**
 * Servicio legacy con hallazgos típicos de análisis (jdeps / jdeprscan).
 * Punto de partida del laboratorio (sin scripts).
 */
public class LegacyAnalisisService {

    public static void main(String[] args) {
        System.out.println("Hallazgo jdeps: " + hallazgoJdeps());
        System.out.println("Hallazgo jdeprscan: " + hallazgoJdeprscan());
        System.out.println("Herramienta refactor: " + herramientaRefactor());
        System.out.println("Quality gate: " + qualityGate());
    }

    static String hallazgoJdeps() {
        return "sun.misc.Unsafe -> jdk.unsupported";
    }

    static String hallazgoJdeprscan() {
        return "java.util.Date.<init>(int,int,int) is deprecated";
    }

    static String herramientaRefactor() {
        return "OpenRewrite";
    }

    static String qualityGate() {
        return "SonarQube";
    }
}
