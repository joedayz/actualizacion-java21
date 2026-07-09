# Demo: Moderne

Plataforma organizacional sobre OpenRewrite (guía conceptual para clase).

## Qué mostrar (sin cuenta obligatoria)

1. Sitio: https://www.moderne.io/ y docs OpenRewrite recipes.
2. Contraste: un `rewrite:run` local vs “aplicar la misma receta a N repos”.
3. Roles: plataforma (Moderne) vs motor (OpenRewrite) vs CI (Maven plugin).

## Flujo enterprise típico

```
1. Conectar SCM (GitHub/GitLab/Bitbucket)
2. Indexar LST de cada repo
3. Seleccionar recipe (p. ej. UpgradeToJava21)
4. Dry-run masivo → PRs automáticos
5. Dashboard de % migrado
```

## Discusión en clase (5 min)

- ¿Cuándo basta OpenRewrite en CI de un monorepo?
- ¿Cuándo justifica Moderne (o similar) el costo?
- ¿Quién aprueba los PRs generados? (Quality Gate + owners)

## Takeaway

Moderne no reemplaza tests ni SonarQube: **orquesta** el mismo tipo de transformaciones
que ya conoces, a escala de organización.
