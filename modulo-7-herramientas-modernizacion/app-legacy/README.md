# Lab: análisis automatizado de aplicación heredada

Mini app Java con patrones legacy para practicar el kit del Módulo 7:

| Herramienta | Qué verás |
|-------------|-----------|
| **jdeps** | Dependencias hacia `java.base` (y demos con `Unsafe`) |
| **jdeprscan** | Uso de APIs deprecadas (`Date` constructor, etc.) |
| **OpenRewrite** | Plugin configurado (`UpgradeToJava17`) |
| **IntelliJ** | Inspect Code sobre este módulo |

## Scripts

| SO | Script | Uso |
|----|--------|-----|
| **Windows** | `analyze.ps1` | `.\analyze.ps1 all` |
| macOS / Linux | `analyze.sh` | `./analyze.sh all` |

Comandos: `all`, `jdeps`, `jdeprscan`, `rewrite-dry`, `rewrite-run`.

---

## Antes de empezar

```bash
cd modulo-7-herramientas-modernizacion/app-legacy
mvn -q clean package -DskipTests
```

---

## Flujo sugerido (30–40 min)

| Min | Actividad |
|-----|-----------|
| 5 | Revisar `LegacyApp.java` (Date, Vector, string Unsafe) |
| 10 | `./analyze.sh jdeps` y `./analyze.sh jdeprscan` |
| 10 | `./analyze.sh rewrite-dry` — revisar patch |
| 5 | Inspect Code en IntelliJ |
| 5 | Completar Ejercicio 05 (`AnalisisLegacyExercisesTest`) |

---

## Relación con el Ejercicio 05

| Script / herramienta | Método del ejercicio |
|----------------------|----------------------|
| Línea `jdk.unsupported` / `sun.misc` | `clasificarHallazgo` → `CRITICO` |
| Línea `deprecated` / `for-removal` | `clasificarHallazgo` → `DEPRECADO` |
| Solo `java.base` | `clasificarHallazgo` → `OK` |
| Orden del pipeline | `ordenPipeline()` |

```bash
cd ../ejercicios && mvn test -Dtest=AnalisisLegacyExercisesTest
```
