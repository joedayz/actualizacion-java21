package pe.joedayz.modulo5.ejercicio03;

/**
 * Ejercicio 03 — Estrategias ante illegal reflective access.
 */
public final class ReflectionMigracion {

    private ReflectionMigracion() {}

    public enum EstrategiaMigracion {
        /** Usar API pública del JDK o de la librería. */
        USAR_API_PUBLICA,
        /** Flag temporal: --add-opens. */
        ADD_OPENS_TEMPORAL,
        /** Actualizar la dependencia de terceros. */
        ACTUALIZAR_DEPENDENCIA
    }

    /**
     * Recomienda estrategia según el origen del acceso por reflection.
     *
     * <ul>
     *   <li>Código propio sobre {@code jdk.internal.*} → {@link EstrategiaMigracion#USAR_API_PUBLICA}</li>
     *   <li>Librería legacy sin actualizar → {@link EstrategiaMigracion#ACTUALIZAR_DEPENDENCIA}
     *       (o {@code ADD_OPENS_TEMPORAL} si no hay versión)</li>
     *   <li>Framework conocido con guía oficial → {@link EstrategiaMigracion#ADD_OPENS_TEMPORAL}
     *       solo como puente documentado</li>
     * </ul>
     */
    public static EstrategiaMigracion recomendar(String origen, boolean hayVersionNuevaDependencia) {
        // TODO: origen = "CODIGO_PROPIO" | "LIBRERIA_TERCEROS" | "FRAMEWORK"
        throw new UnsupportedOperationException("TODO: recomendar");
    }

    /**
     * Sugiere reemplazo Maven para APIs javax removidas del JDK.
     *
     * <p>Ejemplos:
     * {@code javax.xml.bind.JAXBContext} → coordenada jakarta
     * {@code javax.annotation.PostConstruct} → coordenada jakarta
     */
    public static String sugerirDependenciaMaven(String apiRemovida) {
        // TODO
        throw new UnsupportedOperationException("TODO: sugerirDependenciaMaven");
    }
}
