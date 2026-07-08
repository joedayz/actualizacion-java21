package pe.joedayz.modulo6.ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 01 — POM Maven moderno")
class MavenPomExercisesTest {

  private static final String POM = """
      <project>
          <properties>
              <maven.compiler.release>21</maven.compiler.release>
          </properties>
          <dependencies>
              <dependency>
                  <groupId>org.junit.jupiter</groupId>
                  <artifactId>junit-jupiter</artifactId>
              </dependency>
              <dependency>
                  <groupId>com.mysql</groupId>
                  <artifactId>mysql-connector-j</artifactId>
              </dependency>
          </dependencies>
          <dependencyManagement>
              <dependencies>
                  <dependency>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-dependencies</artifactId>
                      <version>3.4.1</version>
                      <type>pom</type>
                      <scope>import</scope>
                  </dependency>
              </dependencies>
          </dependencyManagement>
      </project>
      """;

  @Test
  void extraerJavaVersion() {
    assertEquals("21", MavenPomExercises.extraerJavaVersion(POM));
    assertEquals("17", MavenPomExercises.extraerJavaVersion("""
        <properties><java.version>17</java.version></properties>
        """));
  }

  @Test
  void extraerDependencias() {
    assertEquals(
        List.of("com.mysql:mysql-connector-j", "org.junit.jupiter:junit-jupiter"),
        MavenPomExercises.extraerDependencias(POM));
  }

  @Test
  void usaBomImport() {
    assertTrue(MavenPomExercises.usaBomImport(POM));
    assertFalse(MavenPomExercises.usaBomImport("""
        <dependencies>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
            </dependency>
        </dependencies>
        """));
  }
}
