#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")"

case "${1:-}" in
  dry-run)
    mvn -U org.openrewrite.maven:rewrite-maven-plugin:dryRun
    ;;
  run)
    mvn -U org.openrewrite.maven:rewrite-maven-plugin:run
    echo ""
    echo "Cambios aplicados. Verifica con: mvn clean compile"
    ;;
  verify)
    echo "== javax en código =="
    grep -R "javax\." src/main/java || echo "(ninguno)"
    echo ""
    echo "== jakarta en código =="
    grep -R "jakarta\." src/main/java || echo "(ninguno)"
    echo ""
    echo "== javax en dependency tree =="
    mvn -q dependency:tree | grep javax || echo "(ninguno EE)"
    ;;
  *)
    echo "Uso: $0 {dry-run|run|verify}"
    exit 1
    ;;
esac
