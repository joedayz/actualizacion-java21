package pe.joedayz.modulo7.ejercicio03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 03 — OpenRewrite")
class OpenRewriteExercisesTest {

  @Test
  void clasificarGoal() {
    assertEquals("dryRun", OpenRewriteExercises.clasificarGoal("dryRun"));
    assertEquals("dryRun", OpenRewriteExercises.clasificarGoal("rewrite:dryRun"));
    assertEquals("run", OpenRewriteExercises.clasificarGoal("run"));
    assertEquals("run", OpenRewriteExercises.clasificarGoal("rewrite:run"));
    assertEquals("DESCONOCIDO", OpenRewriteExercises.clasificarGoal("compile"));
  }

  @Test
  void esRecetaMigracionJava() {
    assertTrue(OpenRewriteExercises.esRecetaMigracionJava(
        "org.openrewrite.java.migrate.UpgradeToJava21"));
    assertTrue(OpenRewriteExercises.esRecetaMigracionJava(
        "org.openrewrite.java.migrate.DeprecatedRuntimeXmlApis"));
    assertFalse(OpenRewriteExercises.esRecetaMigracionJava(
        "org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_2"));
  }

  @Test
  void ordenFamilia() {
    assertEquals(1, OpenRewriteExercises.ordenFamilia("JDK"));
    assertEquals(2, OpenRewriteExercises.ordenFamilia("JAKARTA"));
    assertEquals(3, OpenRewriteExercises.ordenFamilia("SPRING"));
    assertEquals(99, OpenRewriteExercises.ordenFamilia("OTRO"));
  }
}
