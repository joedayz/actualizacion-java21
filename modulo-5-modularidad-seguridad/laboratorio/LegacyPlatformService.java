import java.util.List;

/**
 * Código legado con patrones problemáticos al migrar a Java 21.
 * Punto de partida del laboratorio (sin Maven).
 */
public class LegacyPlatformService {

    public static void main(String[] args) {
        System.out.println("Paquete interno: " + esPaqueteInterno("jdk.internal.misc.Unsafe"));
        System.out.println("API removida: " + apisRemovidasDelJdk());
        System.out.println("TLS inseguro: " + protocolosInseguros());
    }

    /** Anti-patrón: detectar internals por prefijo (correcto) pero luego usarlos en runtime. */
    static boolean esPaqueteInterno(String qualifiedName) {
        return qualifiedName.startsWith("jdk.internal.")
                || qualifiedName.startsWith("sun.")
                || qualifiedName.startsWith("com.sun.");
    }

    /** APIs que ya no vienen en el JDK desde Java 11+. */
    static List<String> apisRemovidasDelJdk() {
        return List.of(
                "javax.xml.bind.JAXBContext",
                "javax.annotation.PostConstruct",
                "jdk.nashorn.api.scripting.NashornScriptEngineFactory"
        );
    }

    /** Anti-patrón: aceptar TLS 1.0 en configuración "por compatibilidad". */
    static List<String> protocolosInseguros() {
        return List.of("TLSv1", "TLSv1.1", "SSLv3");
    }
}
