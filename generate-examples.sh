#!/usr/bin/env bash
# Generate example directories for a new Operaton version
# Usage: ./generate-examples.sh <version>
set -e
VERSION="$1"
if [[ -z "$VERSION" ]]; then
  echo "Usage: $0 <operaton-version>" >&2
  exit 1
fi

# Runtimes and databases for which templates may exist
EXAMPLES=(spring-boot quarkus tomcat wildfly weblogic websphere cdi serverless docker)
DBS=(h2 postgres mysql mariadb oracle db2 sqlserver)

for EX in "${EXAMPLES[@]}"; do
  for DB in "${DBS[@]}"; do
    TEMPLATE="template/$EX/$DB"
    TARGET="${VERSION}/${EX}/${DB}"
    if [[ -d "$TEMPLATE" ]]; then
      mkdir -p "$TARGET"
      cp -r "$TEMPLATE/." "$TARGET/"
      # update the example's Maven coordinates to the new version
      (cd "$TARGET" && mvn versions:set -DnewVersion="$VERSION")
    else
      echo "Skipping missing template $TEMPLATE" >&2
    fi
  done
done
