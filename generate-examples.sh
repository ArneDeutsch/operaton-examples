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

COMMON_TEMPLATE="template/common"

for EX in "${EXAMPLES[@]}"; do
  for DB in "${DBS[@]}"; do
    TEMPLATE="template/$EX/$DB"
    TARGET="${VERSION}/${EX}/${DB}"
    if [[ -d "$TEMPLATE" ]]; then
      mkdir -p "$TARGET"
      # copy common sources first, then runtime specific and finally the pom
      if [[ -d "$COMMON_TEMPLATE" ]]; then
        cp -r "$COMMON_TEMPLATE/." "$TARGET/"
      fi
      if [[ -d "template/$EX/common" ]]; then
        cp -r "template/$EX/common/." "$TARGET/"
      fi
      cp -r "$TEMPLATE/." "$TARGET/"
      # update the example's version numbers without requiring Maven
      if [[ -f "$TARGET/pom.xml" ]]; then
        sed -i \
          -e "s|<version>1.0.0</version>|<version>${VERSION}</version>|" \
          -e "s|<version>1.0.0-beta-4</version>|<version>${VERSION}</version>|g" \
          "$TARGET/pom.xml"
      fi
    else
      echo "Skipping missing template $TEMPLATE" >&2
    fi
  done
done
