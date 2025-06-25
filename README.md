# Operaton Example Applications

This repository contains runnable example applications using the
[Operaton](https://github.com/operaton) engine.  Examples are organised by
Operaton version and runtime platform. Each module can be built individually
with Maven.

## Building the Examples

Java 17 or newer and Maven 3.9+ are required. Make sure a recent Maven
installation is available on your `PATH`.

```bash
# build all modules
mvn clean test
```

## Repository Layout

```
VERSION/
  spring-boot/
    h2/
    postgres/
    ...
  quarkus/
  tomcat/
  wildfly/
  weblogic/
  websphere/
  ...
```

Templates live under the `template/` directory. A sub folder exists for each
runtime (for example `spring-boot` or `tomcat`) and inside that for every
supported database. The actual source and test code is shared in
`template/common` and is copied to every generated example. Spring Boot modules
instead use `template/common-spring` so their tests extend
`SpringProcessEngineTestCase`. Each runtime folder
only needs to provide its specific `pom.xml` files. When new runtimes or
databases should be demonstrated simply add the corresponding template
directory with a `pom.xml` and re-use the common sources.

## Generating a New Version

Use the script `generate-examples.sh` to create a directory for a new Operaton
version. The script iterates over all runtime/database combinations and copies
any matching template directory, updating the `pom.xml` to the requested
version.

```bash
./generate-examples.sh 1.1.0
```

This creates a `1.1.0/` folder containing copies of the templates with their
`pom.xml` files updated to `1.1.0`.

## Running Tests

Each example contains JUnit tests. To execute them for a specific module run

```bash
mvn -f 1.0.0/spring-boot/h2/pom.xml test
```

or simply run `mvn clean test` to build all modules.

## Continuous Integration

A basic GitHub Actions workflow is included at `.github/workflows/ci.yml`.
It builds every example module on pushes and pull requests. Ensure that the
repository has GitHub Actions enabled.
