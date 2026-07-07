package pe.joedayz.modulo5.ejercicio05;

import java.util.Set;

/**
 * Ejercicio 05 — Laboratorio: migración empresarial de patrones legacy.
 */
public final class MigracionEmpresarial {

    private static final Set<String> PROTOCOLOS_SEGUROS =
            Set.of("TLSv1.2", "TLSv1.3");


    private MigracionEmpresarial() {
    }


    public static boolean esPaqueteInternoJdk(String qualifiedName) {
        if(qualifiedName == null || qualifiedName.isBlank()) {
            return false;
         }
        return qualifiedName.startsWith("jdk.internal.") || qualifiedName.startsWith("sun.")
                || qualifiedName.startsWith("com.sun.");
    }


    public static String reemplazoMaven(String apiRemovida) {
        if(apiRemovida.startsWith("javax.xml.bind")){
            return "jakarta.xml.bind:jakarta.xml.bind-api";
        }
        if (apiRemovida.startsWith("javax.annotation")) {
            return "jakarta.annotation:jakarta.annotation-api";
        }
        if(apiRemovida.startsWith("nashorn")) {
            return "org.graalvm.js:js-scriptengine";
        }
        return "revisar dependencia Maven/Gradle";
    }


    public static boolean esProtocoloSeguro(String protocolo) {
        return PROTOCOLOS_SEGUROS.contains(protocolo);
    }
}
