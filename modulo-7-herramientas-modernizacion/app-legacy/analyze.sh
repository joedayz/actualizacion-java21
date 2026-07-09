#!/usr/bin/env bash
# Análisis automatizado de app-legacy (jdeps + jdeprscan + OpenRewrite dry-run)
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
cd "$ROOT"

JAR="target/modulo-7-app-legacy-1.0.0-SNAPSHOT.jar"
CMD="${1:-all}"

build() {
  echo "==> mvn package"
  mvn -q package -DskipTests
}

run_jdeps() {
  echo ""
  echo "========== jdeps =========="
  jdeps "$JAR" || true
  echo ""
  echo "========== jdeps --jdk-internals =========="
  jdeps --jdk-internals --multi-release 21 "$JAR" || true
}

run_jdeprscan() {
  echo ""
  echo "========== jdeprscan (deprecated) =========="
  jdeprscan "$JAR" || true
  echo ""
  echo "========== jdeprscan --for-removal =========="
  jdeprscan --for-removal "$JAR" || true
}

run_rewrite_dry() {
  echo ""
  echo "========== OpenRewrite dryRun =========="
  mvn -U org.openrewrite.maven:rewrite-maven-plugin:dryRun
}

run_rewrite() {
  echo ""
  echo "========== OpenRewrite run =========="
  mvn -U org.openrewrite.maven:rewrite-maven-plugin:run
}

case "$CMD" in
  all)
    build
    run_jdeps
    run_jdeprscan
    echo ""
    echo "Tip: ./analyze.sh rewrite-dry  |  ./analyze.sh rewrite-run"
    ;;
  jdeps)
    build
    run_jdeps
    ;;
  jdeprscan)
    build
    run_jdeprscan
    ;;
  rewrite-dry)
    run_rewrite_dry
    ;;
  rewrite-run)
    run_rewrite
    ;;
  *)
    echo "Uso: $0 [all|jdeps|jdeprscan|rewrite-dry|rewrite-run]"
    exit 1
    ;;
esac
