# Yoti Spring Security Module

This module aims to make integration with Spring Security easier by providing some classes that fit into Spring Security's existing authentication model.

Notably:

* An `AuthenticationToken` to hold the encrypted Yoti token.
* A filter which intercepts the token and hands off authentication to an appropriate provider.
* A provider which internally uses a `YotiClient` to decode the token and then call some custom service that you will provide to return the authenticated principal.

This will allow you to rely on built in Spring semantics and mechanisms such as `@Secured`, `@AuthenticationPrincipal` and role based access control. 

## Requirements

- Spring Security
- Java 7 or newer

## Usage

### Add Dependencies

If you are using Maven, you need to add the following dependencies:

```xml
<dependency>
  <groupId>com.yoti</groupId>
  <artifactId>yoti-sdk-spring-security</artifactId>
  <version>3.2.1-SNAPSHOT</version>
</dependency>
```

If you are using Gradle, here is the dependency to add:

```
compile group: 'com.yoti', name: 'yoti-sdk-spring-security', version: '3.2.1-SNAPSHOT'
```

### Provide a `YotiClient` instance

This can be achieved as outlined in the `yoti-sdk-spring-boot-auto-config` module.

### Provide a `YotiUserService` instance

This class is responsible for inspecting the `ActivityDetails` and returning an Authentication principal.

## Authors

* [David Goaté](https://github.com/davidgoate)
