# Script para crear un proyecto Multi-Release Module Archives
# Este script configura la estructura completa

$ErrorActionPreference = "Stop"

$BASE_DIR = $PSScriptRoot
$PROJECT_DIR = Join-Path $BASE_DIR "com.banking.multirelease"

Write-Host "📦 Creando proyecto Multi-Release Module Archives..." -ForegroundColor Cyan
Write-Host "📍 Ubicación: $PROJECT_DIR" -ForegroundColor Cyan
Write-Host ""

# Crear estructura de directorios
$directories = @(
    "src/main/java/com/banking/multirelease",
    "src/main/java-9/com/banking/multirelease",
    "src/main/java-11/com/banking/multirelease",
    "src/main/java-17/com/banking/multirelease",
    "src/main/java-21/com/banking/multirelease",
    "src/main/resources/META-INF"
)

foreach ($dir in $directories) {
    $fullPath = Join-Path $PROJECT_DIR $dir
    if (-not (Test-Path $fullPath)) {
        New-Item -ItemType Directory -Path $fullPath -Force | Out-Null
    }
}

Write-Host "✅ Directorios creados" -ForegroundColor Green
Write-Host ""
Write-Host "Estructura:"
Write-Host "$PROJECT_DIR/"
Write-Host "├── src/main/java/              (Base - Java 9+)"
Write-Host "├── src/main/java-11/           (Versión Java 11+)"
Write-Host "├── src/main/java-17/           (Versión Java 17+)"
Write-Host "├── src/main/java-21/           (Versión Java 21+)"
Write-Host "└── pom.xml"
Write-Host ""

Write-Host "✅ Proyecto preparado para agregar archivos" -ForegroundColor Green
