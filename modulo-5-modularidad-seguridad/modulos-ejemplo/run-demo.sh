#!/bin/bash

# Script para demostración de módulos Java 21
# Uso: ./run-demo.sh [opción]

set -e

echo ""
echo "╔════════════════════════════════════════════════════════════════╗"
echo "║          Java 21 - Demostración de Módulos                    ║"
echo "║          Banking Application with Module System               ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

cd "$(dirname "$0")"

build_banking_modules() {
    mvn -q package -DskipTests \
        -pl com.banking.core,com.banking.operations,com.banking.ui,com.banking.reports,com.banking.app -am
}

banking_module_path() {
    echo "com.banking.app/target/com.banking.app-1.0-SNAPSHOT.jar:com.banking.ui/target/com.banking.ui-1.0-SNAPSHOT.jar:com.banking.operations/target/com.banking.operations-1.0-SNAPSHOT.jar:com.banking.reports/target/com.banking.reports-1.0-SNAPSHOT.jar:com.banking.core/target/com.banking.core-1.0-SNAPSHOT.jar"
}

run_banking_module() {
    local main_module="$1"
    build_banking_modules
    java --module-path "$(banking_module_path)" --module "$main_module"
}

# Mostrar opciones
if [ $# -eq 0 ]; then
    echo "Uso: ./run-demo.sh [opción]"
    echo ""
    echo "Opciones disponibles:"
    echo "  build        - Compilar el proyecto"
    echo "  app          - Ejecutar la aplicación bancaria interactiva"
    echo "  inspect      - Inspeccionar todos los módulos del sistema"
    echo "  jar          - Empaquetar como JARs"
    echo "  multi        - Demo de Multi-Release JAR (Java 9/11/17/21)"
    echo "  multi-build  - Compilar el Multi-Release JAR"
    echo "  multi-verify - Verificar contenido del Multi-Release JAR"
    echo "  clean        - Limpiar archivos compilados"
    echo ""
    exit 0
fi

case "$1" in
    build)
        echo "🔨 Compilando el proyecto..."
        mvn clean compile
        echo "✅ Compilación completada"
        ;;

    app)
        echo "🏦 Iniciando aplicación bancaria..."
        echo ""
        run_banking_module com.banking.app/com.banking.app.BankingApplication
        ;;

    inspect)
        echo "🔍 Inspeccionando módulos del sistema..."
        echo ""
        run_banking_module com.banking.app/com.banking.app.ModuleInspector
        ;;

    jar)
        echo "📦 Empaquetando como JARs..."
        mvn clean package -DskipTests
        echo "✅ JAR files creados:"
        find target -name "*.jar" -type f | head -10
        ;;

    multi)
        echo "📦 Ejecutando Multi-Release JAR Demo..."
        echo ""
        echo "🔨 Paso 1: Compilando Multi-Release JAR..."
        cd com.banking.multirelease
        ./build-multirelease.sh
        echo ""
        echo "▶️  Paso 2: Ejecutando desde JAR..."
        java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar \
            com.banking.multirelease.MultiReleaseDemo
        cd ..
        ;;

    multi-build)
        echo "🔨 Compilando Multi-Release JAR..."
        echo ""
        cd com.banking.multirelease
        ./build-multirelease.sh
        cd ..
        echo "✅ Multi-Release JAR creado"
        ;;

    multi-verify)
        echo "🔍 Verificando Multi-Release JAR..."
        echo ""
        JAR_FILE="com.banking.multirelease/target/com.banking.multirelease-1.0-SNAPSHOT.jar"
        if [ -f "$JAR_FILE" ]; then
            echo "📋 Manifest:"
            jar xf "$JAR_FILE" META-INF/MANIFEST.MF 2>/dev/null || true
            cat META-INF/MANIFEST.MF 2>/dev/null || echo "No manifest found"
            rm -rf META-INF 2>/dev/null || true
            echo ""
            echo "📊 Estructura del JAR:"
            jar tf "$JAR_FILE" | grep -E "versions|multirelease.*\.class$" | head -20
        else
            echo "❌ JAR no encontrado. Ejecuta primero: ./run-demo.sh multi-build"
            exit 1
        fi
        ;;

    clean)
        echo "🧹 Limpiando archivos compilados..."
        mvn clean
        echo "✅ Archivos limpios"
        ;;

    *)
        echo "❌ Opción no reconocida: $1"
        echo "Opciones válidas: build, app, inspect, jar, multi, multi-build, multi-verify, clean"
        exit 1
        ;;
esac

echo ""

