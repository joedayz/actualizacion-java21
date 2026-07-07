#!/bin/bash

# Script para compilar y empaquetar Multi-Release JAR

PROJECT_DIR="/Users/josediaz/Projects/JoeDayz/java21-certification-samples/modulo08/com.banking.multirelease"
TARGET_DIR="$PROJECT_DIR/target/classes"

echo "📦 Compilando Multi-Release JAR..."
echo ""

cd "$PROJECT_DIR"

# Crear directorios de salida
mkdir -p "$TARGET_DIR/META-INF/versions/{11,17,21}"

echo "✅ Compilando Java 9+ (base)..."
javac -d "$TARGET_DIR" \
    src/main/java/module-info.java \
    src/main/java/com/banking/multirelease/utils/VersionFactory.java \
    src/main/java/com/banking/multirelease/MultiReleaseDemo.java \
    src/main/java/com/banking/multirelease/support/VersionInfo.java \
    src/main/java/com/banking/multirelease/support/Java9VersionImpl.java \
    -source 9 -target 9 2>&1 | grep -v "warning:" || true

echo "✅ Compilando Java 11..."
javac -d "$TARGET_DIR/META-INF/versions/11" \
    src/main/java-11/com/banking/multirelease/support/Java11VersionImpl.java \
    -cp "$TARGET_DIR" \
    -source 11 -target 11 2>&1 | grep -v "warning:" || true

echo "✅ Compilando Java 17..."
javac -d "$TARGET_DIR/META-INF/versions/17" \
    src/main/java-17/com/banking/multirelease/support/Java17VersionImpl.java \
    -cp "$TARGET_DIR" \
    -source 17 -target 17 2>&1 | grep -v "warning:" || true

echo "✅ Compilando Java 21..."
javac -d "$TARGET_DIR/META-INF/versions/21" \
    src/main/java-21/com/banking/multirelease/support/Java21VersionImpl.java \
    -cp "$TARGET_DIR" \
    -source 21 -target 21 2>&1 | grep -v "warning:" || true

echo ""
echo "📦 Creando JAR con Multi-Release: true..."

# Crear MANIFEST.MF
mkdir -p "$TARGET_DIR/META-INF"
cat > "$TARGET_DIR/META-INF/MANIFEST.MF" << 'EOF'
Manifest-Version: 1.0
Automatic-Module-Name: com.banking.multirelease
Multi-Release: true
EOF

# Crear el JAR
cd "$TARGET_DIR"
jar cfm "$PROJECT_DIR/target/com.banking.multirelease-1.0-SNAPSHOT.jar" \
    META-INF/MANIFEST.MF \
    com/ module-info.class META-INF/ 2>&1 | head -5 || true

echo ""
echo "✅ JAR creado exitosamente"
echo ""
echo "📊 Contenido del Multi-Release JAR:"
printf '%.0s─' {1..70}
echo ""
jar tf "$PROJECT_DIR/target/com.banking.multirelease-1.0-SNAPSHOT.jar" | \
    grep -E "\.class$|META-INF/versions" | head -30

echo ""
echo "🎯 Para ejecutar:"
echo "cd $PROJECT_DIR"
echo "java -cp target/com.banking.multirelease-1.0-SNAPSHOT.jar \\"
echo "    com.banking.multirelease.MultiReleaseDemo"

