package pe.joedayz.modulo6.ejercicio01;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ejercicio 01 — Analizar fragmentos de POM Maven moderno.
 */
public final class MavenPomExercises {

    private static final Pattern RELEASE = Pattern.compile(
            "<maven\\.compiler\\.release>\\s*(\\d+)\\s*</maven\\.compiler\\.release>");
    private static final Pattern JAVA_VERSION = Pattern.compile(
            "<java\\.version>\\s*(\\d+)\\s*</java\\.version>");
    private static final Pattern SOURCE = Pattern.compile(
            "<maven\\.compiler\\.source>\\s*(\\d+)\\s*</maven\\.compiler\\.source>");
    private static final Pattern DEPENDENCY = Pattern.compile(
            "<dependency>\\s*<groupId>\\s*([^<]+)\\s*</groupId>\\s*<artifactId>\\s*([^<]+)\\s*</artifactId>",
            Pattern.DOTALL);


    private MavenPomExercises() {
    }

    /**
     * Extrae la versión de Java del POM.
     *
     * <p>Busca, en este orden: {@code maven.compiler.release}, {@code java.version},
     * o {@code maven.compiler.source}.
     */
    public static String extraerJavaVersion(String pom) {
        Matcher release = RELEASE.matcher(pom);
        if (release.find()) {
            return release.group(1);
        }
        Matcher javaVersion = JAVA_VERSION.matcher(pom);
        if (javaVersion.find()) {
            return javaVersion.group(1);
        }
        Matcher source = SOURCE.matcher(pom);
        if (source.find()) {
            return source.group(1);
        }
        throw new IllegalArgumentException("No se encontró versión de Java en el POM");
    }

    /**
     * Extrae coordenadas {@code groupId:artifactId} de bloques {@code <dependency>}.
     *
     * @return lista ordenada alfabéticamente, sin duplicados
     */
    public static List<String> extraerDependencias(String pom) {
        String depsSection = pom;
        int dmStart = pom.indexOf("<dependencyManagement>");
        if (dmStart >= 0) {
            depsSection = pom.substring(0, dmStart);
        }
        Matcher matcher = DEPENDENCY.matcher(depsSection);
        var deps = new LinkedHashSet<String>();
        while (matcher.find()) {
            deps.add(matcher.group(1).trim() + ":" + matcher.group(2).trim());
        }
        return new ArrayList<>(deps).stream().sorted().toList();
    }

    /**
     * {@code true} si el POM importa un BOM ({@code type=pom}, {@code scope=import}
     * dentro de {@code dependencyManagement}).
     */
    public static boolean usaBomImport(String pom) {
        return pom.contains("<dependencyManagement>") &&
                pom.contains("<scope>import</scope>") &&
                pom.contains("<type>pom</type>");
    }
}
