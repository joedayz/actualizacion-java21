import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.util.Arrays;

/**
 * Demo: protocolos TLS habilitados por defecto en este JDK.
 * Ejecutar: java TlsDefaults.java
 */
public class TlsDefaults {

    public static void main(String[] args) throws Exception {
        SSLContext context = SSLContext.getDefault();
        SSLParameters params = context.getDefaultSSLParameters();

        System.out.println("=== Protocolos TLS por defecto ===");
        Arrays.stream(params.getProtocols()).sorted().forEach(p -> System.out.println("  " + p));

        System.out.println();
        System.out.println("=== Cipher suites (primeros 10) ===");
        Arrays.stream(params.getCipherSuites()).sorted().limit(10)
                .forEach(c -> System.out.println("  " + c));

        System.out.println();
        boolean tls10 = Arrays.asList(params.getProtocols()).contains("TLSv1");
        boolean tls11 = Arrays.asList(params.getProtocols()).contains("TLSv1.1");
        System.out.println("TLS 1.0 habilitado: " + tls10);
        System.out.println("TLS 1.1 habilitado: " + tls11);
        System.out.println();
        System.out.println("Desde Java 11+, TLS 1.0/1.1 suelen estar deshabilitados.");
        System.out.println("Servicios legacy que solo hablan TLS 1.0 fallarán al conectar.");
    }
}
