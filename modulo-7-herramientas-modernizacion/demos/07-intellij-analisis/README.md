# Demo: análisis en IntelliJ IDEA

Herramientas del IDE para inventariar deuda en `app-legacy`.

## Preparación

1. Abre `modulo-7-herramientas-modernizacion/pom.xml` en IntelliJ.
2. Espera a que Maven importe los 4 submódulos.
3. SDK del proyecto: **21+**.

## Inspect Code

1. Click derecho en el módulo `app-legacy` → **Analyze → Inspect Code**.
2. Scope: Module `modulo-7-app-legacy`.
3. Revisa grupos:
   - **Declaration redundancy**
   - **Probable bugs**
   - **Java → Code maturity → Deprecated API usage** (si aparece)

## Otras vistas útiles

| Vista | Para qué |
|-------|----------|
| Problems | Issues al editar |
| Maven → Dependencies | Conflictos / árboles |
| Find in Files `sun.` / `javax.` | Búsqueda rápida de patrones |
| Structural Search | Patrones custom (p. ej. `new Date($d$)`) |

## Contraste con CLI

| IntelliJ | CLI |
|----------|-----|
| Feedback al editar | Inventario reproducible en CI |
| Bueno para demos 1:1 | Bueno para reportes / tickets |

Ejecuta también `./analyze.sh` en `app-legacy` y compara hallazgos.
