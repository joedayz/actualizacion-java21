package pe.joedayz.modulo5.ejercicio04;

import java.util.List;
import java.util.Set;

/**
 * Ejercicio 04 — TLS y criptografía en Java 21.
 */
public final class TlsCriptografia {

    private static final List<String> INSEGUROS = List.of("SSLv3", "TLSv1", "TLSv1.1");
    private static final List<String> RECOMENDADOS = List.of("TLSv1.2", "TLSv1.3");
    private static final Set<String> SEGUROS = Set.copyOf(RECOMENDADOS);


    private TlsCriptografia() {}

    /**
     * Protocolos considerados inseguros y deshabilitados por defecto en JDK moderno.
     */
    public static List<String> protocolosInseguros() {
        return INSEGUROS;
    }

    /**
     * Protocolos recomendados para nuevas integraciones en Java 21.
     */
    public static List<String> protocolosRecomendados() {
        return RECOMENDADOS;
    }

    /**
     * Filtra una lista de protocolos configurados y devuelve solo los seguros
     * (intersección con {@link #protocolosRecomendados()}).
     */
    public static List<String> filtrarProtocolosSeguros(List<String> configurados) {
        List<String> seguros = configurados.stream()
                .filter(SEGUROS::contains)
                .toList();
        return seguros;
    }
}
