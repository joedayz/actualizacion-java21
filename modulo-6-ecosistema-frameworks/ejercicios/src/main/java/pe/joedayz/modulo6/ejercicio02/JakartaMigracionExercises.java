package pe.joedayz.modulo6.ejercicio02;

import java.util.List;
import java.util.Map;

/**
 * Ejercicio 02 — Migración javax → jakarta.
 */
public final class JakartaMigracionExercises {


    private static final List<String> JDK_JAVAX_PREFIXES = List.of(
            "javax.crypto.",
            "javax.net.",
            "javax.sql.",
            "javax.swing.",
            "javax.management.",
            "javax.security.",
            "javax.naming.",
            "javax.tools.",
            "javax.imageio.",
            "javax.sound.",
            "javax.print.",
            "javax.accessibility.",
            "javax.lang.model.",
            "javax.xml.parsers.",
            "javax.xml.transform.",
            "javax.xml.xpath.",
            "javax.xml.validation.",
            "javax.xml.stream.",
            "javax.xml.datatype.",
            "javax.xml.namespace.");

    private static final Map<String, String> MAVEN_COORDS = Map.of(
            "javax.persistence:javax.persistence-api", "jakarta.persistence:jakarta.persistence-api",
            "javax.servlet:javax.servlet-api", "jakarta.servlet:jakarta.servlet-api",
            "javax.validation:validation-api", "jakarta.validation:jakarta.validation-api",
            "javax.annotation:javax.annotation-api", "jakarta.annotation:jakarta.annotation-api",
            "javax.xml.bind:jaxb-api", "jakarta.xml.bind:jakarta.xml.bind-api");

    private JakartaMigracionExercises() {}

    /**
     * Migra un nombre calificado de tipo EE de {@code javax} a {@code jakarta}.
     *
     * <p>Ejemplo: {@code javax.persistence.Entity} → {@code jakarta.persistence.Entity}
     *
     * <p>Tipos del JDK que no migran (ej. {@code javax.sql.DataSource}) se devuelven sin cambios.
     */
    public static String migrarTipo(String qualifiedType) {
        if(!requiereMigracionJakarta(qualifiedType)) {
            return qualifiedType;
        }
        return qualifiedType.replaceFirst("javax.", "jakarta.");
    }

    /**
     * {@code true} si el nombre calificado pertenece a un paquete EE que debe migrar a jakarta.
     */
    public static boolean requiereMigracionJakarta(String qualifiedName) {
        if (!qualifiedName.startsWith("javax.")) {
            return false;
        }
        return JDK_JAVAX_PREFIXES.stream().noneMatch(qualifiedName::startsWith);
    }

    /**
     * Devuelve la coordenada Maven jakarta equivalente a una dependencia javax legacy.
     *
     * <p>Ejemplo: {@code javax.persistence:javax.persistence-api}
     * → {@code jakarta.persistence:jakarta.persistence-api}
     */
    public static String coordenadaMavenJakarta(String coordenadaLegacy) {
       return MAVEN_COORDS.getOrDefault(coordenadaLegacy, coordenadaLegacy.replace("javax.", "jakarta."));
    }
}
