# Operaton Example Applications

This repository contains example applications demonstrating how to run the
[Operaton](https://github.com/operaton) engine using artifacts released to
Maven Central. Each example is organised by Operaton version and runtime
platform.

## Building the Examples

Java 17 or newer and Maven 3.9+ are required. You can use the Maven Wrapper
included in this repository:

```bash
# Unix/Mac
./mvnw clean install

# Windows
mvnw clean install
```

Alternatively a local Maven installation can be used.

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

A script `generate-examples.sh` is provided to create a new VERSION directory
from the templates.
