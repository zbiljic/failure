# Failure

*Failure* is a library for machine-readable details of errors in a HTTP response.
It comes with an extensible set of implementations as well as convenient functions for every day use.

It's decoupled from any JSON library, but contains a separate module for Jackson.

## Dependency

```xml
<dependency>
    <groupId>com.zbiljic</groupId>
    <artifactId>failure</artifactId>
    <version>${failure.version}</version>
</dependency>

<dependency>
    <groupId>com.zbiljic</groupId>
    <artifactId>jackson-datatype-failure</artifactId>
    <version>${failure.version}</version>
</dependency>
```

## Usage

In case you're using Jackson, make sure you register the module.

```java
ObjectMapper mapper = new ObjectMapper();

mapper.registerModule(new Jdk8Module());
mapper.registerModule(new FailureModule());
// or
mapper.findAndRegisterModules();
```

**IMPORTANT**: The `FailureModule` requires the [`Jdk8Module`](https://github.com/FasterXML/jackson-datatype-jdk8) to work.



---

Copyright © 2016 Nemanja Zbiljić
