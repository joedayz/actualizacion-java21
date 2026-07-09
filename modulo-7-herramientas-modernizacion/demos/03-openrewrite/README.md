# Demo: OpenRewrite

Refactoring automatizado con recetas (mismo motor que en Módulo 6).

## En este módulo

El submódulo [`app-legacy/`](../../app-legacy/) ya trae el plugin `rewrite-maven-plugin`
con la receta `UpgradeToJava17` (paso intermedio típico hacia 21).

## Comandos

```bash
cd modulo-7-herramientas-modernizacion/app-legacy

# Preview (no modifica archivos)
mvn -U org.openrewrite.maven:rewrite-maven-plugin:dryRun

# Aplicar
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run

# Verificar
mvn -q test
git checkout -- .   # revertir si es demo
```

También: `./analyze.sh rewrite-dry` / `./analyze.sh rewrite-run`.

## Relación con Módulo 6

| Módulo 6 | Módulo 7 |
|----------|----------|
| Recetas Jakarta / Spring Boot 3 | Inventario + recetas JDK + pipeline |
| Lab `migracion-legacy` | Lab `app-legacy` + clasificación de hallazgos |

## Recetas útiles en migraciones JDK

- `org.openrewrite.java.migrate.UpgradeToJava17`
- `org.openrewrite.java.migrate.UpgradeToJava21`
- `org.openrewrite.java.migrate.DeprecatedRuntimeXmlApis`
- Familias Spring Boot 3 (ya vistas en Módulo 6)
