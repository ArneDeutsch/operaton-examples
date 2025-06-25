#!/usr/bin/env bash
set -e
VERSION="$1"
if [[ -z "$VERSION" ]]; then
  echo "Usage: $0 <operaton-version>" >&2
  exit 1
fi

EXAMPLES=(spring-boot quarkus tomcat wildfly weblogic websphere cdi serverless docker)
DBS=(h2 postgres mysql mariadb oracle db2 sqlserver)

for EX in "${EXAMPLES[@]}"; do
  for DB in "${DBS[@]}"; do
    TEMPLATE="template/$EX/$DB"
    TARGET="${VERSION}/${EX}/${DB}"
    if [[ -d "$TEMPLATE" ]]; then
      mkdir -p "$TARGET"
      cp -r "$TEMPLATE/." "$TARGET/"
      (cd "$TARGET" && mvn versions:set -DnewVersion="$VERSION")
    fi
  done
done
