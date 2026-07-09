# Análisis automatizado de app-legacy (Windows PowerShell)
param(
    [ValidateSet("all", "jdeps", "jdeprscan", "rewrite-dry", "rewrite-run")]
    [string]$Command = "all"
)

$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
$Jar = "target\modulo-7-app-legacy-1.0.0-SNAPSHOT.jar"

function Build {
    Write-Host "==> mvn package"
    mvn -q package -DskipTests
}

function Run-Jdeps {
    Write-Host ""
    Write-Host "========== jdeps =========="
    jdeps $Jar
    Write-Host ""
    Write-Host "========== jdeps --jdk-internals =========="
    jdeps --jdk-internals --multi-release 21 $Jar
}

function Run-Jdeprscan {
    Write-Host ""
    Write-Host "========== jdeprscan (deprecated) =========="
    jdeprscan $Jar
    Write-Host ""
    Write-Host "========== jdeprscan --for-removal =========="
    jdeprscan --for-removal $Jar
}

switch ($Command) {
    "all" {
        Build
        Run-Jdeps
        Run-Jdeprscan
        Write-Host ""
        Write-Host "Tip: .\analyze.ps1 rewrite-dry  |  .\analyze.ps1 rewrite-run"
    }
    "jdeps" { Build; Run-Jdeps }
    "jdeprscan" { Build; Run-Jdeprscan }
    "rewrite-dry" {
        Write-Host "========== OpenRewrite dryRun =========="
        mvn -U org.openrewrite.maven:rewrite-maven-plugin:dryRun
    }
    "rewrite-run" {
        Write-Host "========== OpenRewrite run =========="
        mvn -U org.openrewrite.maven:rewrite-maven-plugin:run
    }
}
