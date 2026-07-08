package pe.joedayz.modulo6.ejercicio01;

import java.util.List;

/**
 * Ejercicio 01 — Analizar fragmentos de POM Maven moderno.
 */
public final class MavenPomExercises {

    private MavenPomExercises() {}

    /**
     * Extrae la versión de Java del POM.
     *
     * <p>Busca, en este orden: {@code maven.compiler.release}, {@code java.version},
     * o {@code maven.compiler.source}.
     */
    public static String extraerJavaVersion(String pom) {
        // TODO
        throw new UnsupportedOperationException("TODO: extraerJavaVersion");
    }

    /**
     * Extrae coordenadas {@code groupId:artifactId} de bloques {@code <dependency>}.
     *
     * @return lista ordenada alfabéticamente, sin duplicados
     */
    public static List<String> extraerDependencias(String pom) {
        // TODO
        throw new UnsupportedOperationException("TODO: extraerDependencias");
    }

    /**
     * {@code true} si el POM importa un BOM ({@code type=pom}, {@code scope=import}
     * dentro de {@code dependencyManagement}).
     */
    public static boolean usaBomImport(String pom) {
        // TODO
        throw new UnsupportedOperationException("TODO: usaBomImport");
    }
}
