# Lab migracion-legacy — OpenRewrite helper
# Uso: .\migrate.ps1 [dry-run|run|verify]

param(
    [Parameter(Position = 0)]
    [ValidateSet("dry-run", "run", "verify")]
    [string]$Action
)

$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

function Invoke-Maven {
    param([string[]]$Arguments)
    & mvn @Arguments
    if ($LASTEXITCODE -ne 0) {
        exit $LASTEXITCODE
    }
}

function Show-Usage {
    Write-Host "Uso: .\migrate.ps1 {dry-run|run|verify}"
    Write-Host ""
    Write-Host "  dry-run  Vista previa OpenRewrite (no modifica archivos)"
    Write-Host "  run      Aplica la migracion OpenRewrite"
    Write-Host "  verify   Busca javax/jakarta en codigo y dependency tree"
}

if (-not $Action) {
    Show-Usage
    exit 0
}

switch ($Action) {
    "dry-run" {
        Invoke-Maven @("-U", "org.openrewrite.maven:rewrite-maven-plugin:dryRun")
    }
    "run" {
        Invoke-Maven @("-U", "org.openrewrite.maven:rewrite-maven-plugin:run")
        Write-Host ""
        Write-Host "Cambios aplicados. Verifica con: mvn clean compile"
    }
    "verify" {
        Write-Host "== javax en codigo =="
        $javaxMatches = Select-String -Path "src/main/java/**/*.java" -Pattern "javax\." -ErrorAction SilentlyContinue
        if ($javaxMatches) {
            $javaxMatches | ForEach-Object { $_.Line }
        } else {
            Write-Host "(ninguno)"
        }

        Write-Host ""
        Write-Host "== jakarta en codigo =="
        $jakartaMatches = Select-String -Path "src/main/java/**/*.java" -Pattern "jakarta\." -ErrorAction SilentlyContinue
        if ($jakartaMatches) {
            $jakartaMatches | ForEach-Object { $_.Line }
        } else {
            Write-Host "(ninguno)"
        }

        Write-Host ""
        Write-Host "== javax en dependency tree =="
        $tree = & mvn -q dependency:tree 2>&1
        if ($LASTEXITCODE -ne 0) {
            exit $LASTEXITCODE
        }
        $javaxDeps = $tree | Select-String -Pattern "javax"
        if ($javaxDeps) {
            $javaxDeps | ForEach-Object { $_.Line }
        } else {
            Write-Host "(ninguno EE)"
        }
    }
}
