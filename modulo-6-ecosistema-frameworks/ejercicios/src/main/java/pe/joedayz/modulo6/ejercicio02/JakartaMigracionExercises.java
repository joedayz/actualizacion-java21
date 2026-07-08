package pe.joedayz.modulo6.ejercicio02;

/**
 * Ejercicio 02 — Migración javax → jakarta.
 */
public final class JakartaMigracionExercises {

    private JakartaMigracionExercises() {}

    /**
     * Migra un nombre calificado de tipo EE de {@code javax} a {@code jakarta}.
     *
     * <p>Ejemplo: {@code javax.persistence.Entity} → {@code jakarta.persistence.Entity}
     *
     * <p>Tipos del JDK que no migran (ej. {@code javax.sql.DataSource}) se devuelven sin cambios.
     */
    public static String migrarTipo(String qualifiedType) {
        // TODO
        throw new UnsupportedOperationException("TODO: migrarTipo");
    }

    /**
     * {@code true} si el nombre calificado pertenece a un paquete EE que debe migrar a jakarta.
     */
    public static boolean requiereMigracionJakarta(String qualifiedName) {
        // TODO
        throw new UnsupportedOperationException("TODO: requiereMigracionJakarta");
    }

    /**
     * Devuelve la coordenada Maven jakarta equivalente a una dependencia javax legacy.
     *
     * <p>Ejemplo: {@code javax.persistence:javax.persistence-api}
     * → {@code jakarta.persistence:jakarta.persistence-api}
     */
    public static String coordenadaMavenJakarta(String coordenadaLegacy) {
        // TODO
        throw new UnsupportedOperationException("TODO: coordenadaMavenJakarta");
    }
}
