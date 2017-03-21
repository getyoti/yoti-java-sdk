# Yoti Spring Boot Auto Configuration Java SDK Module

This module aims to make integration of the Yoti Java SDK client into Spring Boot projects more simple by reducing the
 amount of boilerplate code required by the client application and allows the developer to simply provide two properties.
 
You will still need to provide an appropriate implementation of the Yoti Java SDK as one is not included in this module. 
See the example dependencies below for more information on how to do this.

## Requirements

- Spring Boot
- Java 6 or newer

## Usage

### Add Dependencies

If you are using Maven, you need to add the following dependencies:

```xml
<dependency>
  <groupId>com.yoti</groupId>
  <artifactId>java-sdk-spring-boot-auto-config</artifactId>
  <version>1.0</version>
</dependency>
<dependency>
  <groupId>com.yoti</groupId>
  <artifactId>java-sdk-impl</artifactId>
  <version>1.0</version>
</dependency>
```


If you are using Gradle, here is the dependency to add:

```
compile group: 'com.yoti', name: 'java-sdk-spring-boot-auto-config', version: '1.0'
compile group: 'com.yoti', name: 'java-sdk-impl', version: '1.0'
```


### Configure The Client

The client supports configuration via Spring property sources such as YAML or properties files.

The `accessSecurityKey` property supports any of the default Spring resource loader strings. See [here](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/resources.html) for more details.

Examples include:

* com.yoti.client.accessSecurityKey=classpath:/my-key.pem
* com.yoti.client.accessSecurityKey=file:/my-key.pem

**Remember it is your responsibility to keep your secret key safe!** You may wish to think carefully about where you 
place the PEM file and whether it should be included in your distribution or committed into SCM. If storing it on a filesystem you should be mindful 
of the file ownership and permissions.


#### Application Properties
```
com.yoti.client.clientSdkId=my-app-id
com.yoti.client.accessSecurityKey=file:~/.yoti/my-key.pem
```

#### YAML
```
com:
    yoti:
        client:
            clientSdkId: "my-app-id"
            accessSecurityKey: "file:/my-key.pem"
```

## That's It - You're Done!
Expecting more? That's all that you need to integrate Yoti into your Spring Boot application.

# Authors
* [David Goaté](https://github.com/davidgoate)




