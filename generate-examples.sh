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
EXAMPLES=(spring-boot quarkus tomcat wildfly weblogic websphere)
DBS=(h2 postgres mysql mariadb oracle db2 sqlserver)

COMMON_TEMPLATE="template/common"
SPRING_COMMON_TEMPLATE="template/common-spring"

for EX in "${EXAMPLES[@]}"; do
  for DB in "${DBS[@]}"; do
    TEMPLATE="template/$EX/$DB"
    TARGET="${VERSION}/${EX}/${DB}"
    if [[ "$EX" == "spring-boot" ]]; then
      COMMON_SRC="$SPRING_COMMON_TEMPLATE"
    else
      COMMON_SRC="$COMMON_TEMPLATE"
    fi
    if [[ -d "$TEMPLATE" ]]; then
      mkdir -p "$TARGET"
      # copy common sources first, then runtime specific and finally the pom
      if [[ -d "$COMMON_SRC/src" ]]; then
        mkdir -p "$TARGET/src"
        cp -r "$COMMON_SRC/src/." "$TARGET/src/"
      fi
      # mix in database specific overrides
      if [[ -d "$COMMON_SRC/$DB" ]]; then
        cp -r "$COMMON_SRC/$DB/." "$TARGET/"
      fi
      if [[ -d "template/$EX/common" ]]; then
        cp -r "template/$EX/common/." "$TARGET/"
      fi
      cp -r "$TEMPLATE/." "$TARGET/"
      # update the example's Maven coordinates to the new version if a pom exists
      if [[ -f "$TARGET/pom.xml" ]]; then
        if command -v mvn >/dev/null 2>&1; then
          (cd "$TARGET" && mvn -q versions:set -DnewVersion="$VERSION")
        else
          echo "mvn not available; skipping version update for $TARGET" >&2
        fi
      fi
    else
      echo "Skipping missing template $TEMPLATE" >&2
    fi
  done
done
