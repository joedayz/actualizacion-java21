# Módulo 5 — Ejercicios (participantes)

Proyecto Maven con **5 ejercicios** y tests **JUnit 5**. Completa los `TODO` hasta que `mvn test` quede en verde.

## Requisitos

- JDK **21+**
- Maven **3.9+**

## IntelliJ IDEA

Abre el proyecto Maven **raíz del módulo** (no la carpeta del repo completo):

```
modulo-5-modularidad-seguridad/pom.xml
```

IntelliJ importará 3 submódulos:

| Módulo | Contenido | Ejecutar |
|--------|-----------|----------|
| `modulo-5-ejercicios` | Ejercicios con tests JUnit | Click derecho en `*Test` → Run |
| `modulo-5-demos` | `ModulosEnRuntime`, `ReflectionIlegal`, `TlsDefaults`, `ApisCambiadas` | Run en la clase `main` |
| `modulo-5-laboratorio` | `LegacyPlatformService`, `SolucionMigrada` | Run en la clase `main` |

**Demo JPMS banking** (módulos `com.banking.*`): abre por separado
`modulo-5-modularidad-seguridad/modulos-ejemplo/pom.xml` en IntelliJ.
El módulo `com.banking.app` está en `modulos-ejemplo/com.banking.app/` — no exporta
nada y requiere core, operations y ui. Ejecuta `ModuleInspector` para verlo en runtime.

Tras abrir el `pom.xml`, espera a que Maven termine de importar y verifica que el SDK del proyecto sea **21+** (`File → Project Structure → Project SDK`).

### Demo `ReflectionIlegal` (VM options opcionales)

Por defecto verás `InaccessibleObjectException` (comportamiento esperado). Para probar el flag de migración, en la configuración Run añade:

```
--add-opens java.base/java.lang=ALL-UNNAMED
```

## Cómo trabajar

```bash
cd modulo-5-modularidad-seguridad/ejercicios

mvn test

# Por ejercicio
mvn test -Dtest=ModuleInfoExercisesTest
mvn test -Dtest=EncapsulacionExercisesTest
mvn test -Dtest=ReflectionMigracionTest
mvn test -Dtest=TlsCriptografiaTest
mvn test -Dtest=MigracionEmpresarialTest
```

## Ejercicios

| # | Paquete | Tema | Tests |
|---|---------|------|-------|
| 01 | `ejercicio01` | Parser de `module-info` simplificado | `ModuleInfoExercisesTest` |
| 02 | `ejercicio02` | Encapsulación y `--add-opens` | `EncapsulacionExercisesTest` |
| 03 | `ejercicio03` | Migración de reflection / APIs internas | `ReflectionMigracionTest` |
| 04 | `ejercicio04` | TLS y protocolos seguros | `TlsCriptografiaTest` |
| 05 | `ejercicio05` | Laboratorio: migración empresarial | `MigracionEmpresarialTest` |

## Demo jdeps (opcional)

```bash
mvn -q package -DskipTests
jdeps --jdk-internals --multi-release 21 target/modulo-5-ejercicios-1.0.0-SNAPSHOT.jar
```

**Salida vacía = correcto** para este JAR (no usa APIs internas en bytecode).
Para ver hallazgos reales, usa el JAR de demos: ver `demos/03-jdeps-internals/README.md`.
