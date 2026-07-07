# 🚀 Guía Rápida: Multi-Release JAR Demo

## ¿Qué es un Multi-Release JAR?

Un **Multi-Release JAR** contiene múltiples versiones compiladas del mismo código, optimizado para diferentes versiones de Java. El JVM automáticamente selecciona la versión más apropiada en tiempo de ejecución.

## 📋 Cómo Ejecutar el Demo

### Opción 1: Usar el script principal (Recomendado)

```bash
# Desde el directorio raíz del proyecto (modulo08)
./run-demo.sh multi
```

Este comando:
1. Compila el Multi-Release JAR con todas las versiones
2. Ejecuta el demo desde el JAR empaquetado

### Opción 2: Paso a paso

```bash
# 1. Construir el Multi-Release JAR
./run-demo.sh multi-build

# 2. Ejecutar el demo
cd com.banking.multirelease
java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar \
    com.banking.multirelease.MultiReleaseDemo

# 3. Verificar el contenido del JAR
cd ..
./run-demo.sh multi-verify
```

### Opción 3: Con Maven

```bash
cd com.banking.multirelease

# Compilar
mvn clean compile

# Ejecutar (ejecutará desde clases, no desde JAR multi-release)
mvn exec:java -Dexec.mainClass="com.banking.multirelease.MultiReleaseDemo"
```

⚠️ **Nota**: Ejecutar con `mvn exec:java` NO usa el multi-release JAR. Siempre usará la implementación base (Java 9).

## 🔍 Verificar el Multi-Release JAR

Para ver la estructura del JAR creado:

```bash
./run-demo.sh multi-verify
```

Esto mostrará:
- El contenido del MANIFEST.MF (debe tener `Multi-Release: true`)
- La estructura de versiones dentro del JAR

## 📊 ¿Qué Esperar?

Cuando ejecutes el demo, verás:

1. **Información del sistema**
   - Versión de Java actual
   - Java Home

2. **Implementación cargada**
   - Si ejecutas desde el JAR: cargará la versión específica (Java 11, 17, o 21)
   - Si ejecutas desde clases: cargará la versión base (Java 9)

3. **Características disponibles**
   - Lista de características de esa versión de Java

4. **Benchmark**
   - Prueba de rendimiento usando características de esa versión

## 🐛 Solución de Problemas

### Error: "No se pudo cargar la implementación"

**Causa**: Estás ejecutando desde clases compiladas, no desde el JAR.

**Solución**:
```bash
./run-demo.sh multi
```

### El JAR no se encuentra

**Solución**:
```bash
./run-demo.sh multi-build
```

### Quiero ver el código de cada versión

Las implementaciones están en:
- `src/main/java/` - Base (Java 9)
- `src/main/java-11/` - Java 11
- `src/main/java-17/` - Java 17
- `src/main/java-21/` - Java 21

## 🎓 Para Aprender Más

Consulta:
- [TUTORIAL_PASO_A_PASO.md](TUTORIAL_PASO_A_PASO.md) - Tutorial completo
- [MULTIRELEASE_GUIDE.md](MULTIRELEASE_GUIDE.md) - Guía detallada
- [EJERCICIOS_PRACTICOS.md](EJERCICIOS_PRACTICOS.md) - Ejercicios prácticos

## 💡 Tip

Para comparar el comportamiento entre versiones:
1. Ejecuta el demo con Java 21
2. Cambia a Java 17 (usando SDKMAN o similar)
3. Ejecuta el demo nuevamente
4. Compara las características y el rendimiento
