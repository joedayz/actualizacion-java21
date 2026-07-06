import java.util.List;
import java.util.Set;

/**
 * Referencia del laboratorio — enfoque alineado con Java 21.
 */
public class SolucionMigrada {

    private static final Set<String> PROTOCOLOS_SEGUROS = Set.of("TLSv1.2", "TLSv1.3");

    public static void main(String[] args) {
        System.out.println("Interno: " + esPaqueteInterno("jdk.internal.misc.Unsafe"));
        System.out.println("Reemplazo JAXB: " + reemplazoApiRemovida("javax.xml.bind.JAXBContext"));
        System.out.println("TLS OK: " + esProtocoloSeguro("TLSv1.2"));
    }

    static boolean esPaqueteInterno(String qualifiedName) {
        return qualifiedName.startsWith("jdk.internal.")
                || qualifiedName.startsWith("sun.")
                || qualifiedName.startsWith("com.sun.");
    }

    static String reemplazoApiRemovida(String apiRemovida) {
        if (apiRemovida.startsWith("javax.xml.bind")) {
            return "jakarta.xml.bind.JAXBContext + dependencia jakarta.xml.bind-api";
        }
        if (apiRemovida.startsWith("javax.annotation")) {
            return "jakarta.annotation-api";
        }
        if (apiRemovida.contains("nashorn")) {
            return "GraalJS u otro motor JS externo";
        }
        return "revisar dependencia Maven/Gradle";
    }

    static boolean esProtocoloSeguro(String protocolo) {
        return PROTOCOLOS_SEGUROS.contains(protocolo);
    }
}
