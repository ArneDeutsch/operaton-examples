# Operaton Example Applications

This repository contains runnable example applications using the
[Operaton](https://github.com/operaton) engine.  Examples are organised by
Operaton version and runtime platform. Each module can be built individually
with Maven.

## Building the Examples

Java 17 or newer and Maven 3.9+ are required. You may use a local Maven
installation or the Maven wrapper shipped with the project.

```bash
# build all modules
./mvnw clean test
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
  ...
```

Only a Spring Boot + H2 template is provided initially. Additional templates can
be added under `template/` following the same structure.

## Generating a New Version

Use the script `generate-examples.sh` to create a directory for a new Operaton
version. The script copies all templates and updates the Maven versions.

```bash
./generate-examples.sh 1.1.0
```

This creates a `1.1.0/` folder containing copies of the templates with their
`pom.xml` files updated to `1.1.0`.

## Running Tests

Each example contains JUnit tests. To execute them for a specific module run

```bash
./mvnw -f 1.0.0/spring-boot/h2/pom.xml test
```

or simply run `./mvnw clean test` to build all modules.

## Continuous Integration

A basic GitHub Actions workflow is included at `.github/workflows/ci.yml`.
It builds every example module on pushes and pull requests. Ensure that the
repository has GitHub Actions enabled.
