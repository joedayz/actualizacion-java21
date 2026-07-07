#!/bin/bash

# Script para crear un proyecto Multi-Release Module Archives
# Este script configura la estructura completa

set -e

BASE_DIR="/Users/josediaz/Projects/JoeDayz/java21-certification-samples/modulo08"
PROJECT_DIR="$BASE_DIR/com.banking.multirelease"

echo "📦 Creando proyecto Multi-Release Module Archives..."
echo "📍 Ubicación: $PROJECT_DIR"
echo ""

# Crear estructura de directorios
mkdir -p "$PROJECT_DIR/src/main/java/com/banking/multirelease"
mkdir -p "$PROJECT_DIR/src/main/java-9/com/banking/multirelease"
mkdir -p "$PROJECT_DIR/src/main/java-11/com/banking/multirelease"
mkdir -p "$PROJECT_DIR/src/main/java-17/com/banking/multirelease"
mkdir -p "$PROJECT_DIR/src/main/java-21/com/banking/multirelease"
mkdir -p "$PROJECT_DIR/src/main/resources/META-INF"

echo "✅ Directorios creados"
echo ""
echo "Estructura:"
echo "$PROJECT_DIR/"
echo "├── src/main/java/              (Base - Java 9+)"
echo "├── src/main/java-11/           (Versión Java 11+)"
echo "├── src/main/java-17/           (Versión Java 17+)"
echo "├── src/main/java-21/           (Versión Java 21+)"
echo "└── pom.xml"
echo ""

echo "✅ Proyecto preparado para agregar archivos"

