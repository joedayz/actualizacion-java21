package pe.joedayz.modulo5.ejercicio04;

import java.util.List;

/**
 * Ejercicio 04 — TLS y criptografía en Java 21.
 */
public final class TlsCriptografia {

    private TlsCriptografia() {}

    /**
     * Protocolos considerados inseguros y deshabilitados por defecto en JDK moderno.
     */
    public static List<String> protocolosInseguros() {
        // TODO: TLSv1, TLSv1.1, SSLv3
        throw new UnsupportedOperationException("TODO: protocolosInseguros");
    }

    /**
     * Protocolos recomendados para nuevas integraciones en Java 21.
     */
    public static List<String> protocolosRecomendados() {
        // TODO: TLSv1.2, TLSv1.3
        throw new UnsupportedOperationException("TODO: protocolosRecomendados");
    }

    /**
     * Filtra una lista de protocolos configurados y devuelve solo los seguros
     * (intersección con {@link #protocolosRecomendados()}).
     */
    public static List<String> filtrarProtocolosSeguros(List<String> configurados) {
        // TODO
        throw new UnsupportedOperationException("TODO: filtrarProtocolosSeguros");
    }
}
