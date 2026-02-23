# Spring Boot Yoti SDK Examples
This project shows example implementations of a server-app with an endpoint which will be called by Yoti

# Prerequisites
Before you start, you'll need to create an Application in [Yoti Hub](https://hub.yoti.com) and set the domain to `https://localhost:8443/`

Note that:
- Your endpoint must be accessible from the machine that is displaying the QR code.**
- In order to receive calls on endpoint, you need to expose your server-app to the outside world. We require that you use the domain from the Callback URL and HTTPS
- While creating Application in Yoti Hub, some of the attributes (except phone number and selfie) require users to have a Yoti with a verified passport. If your application, for instance, requires the user's date of birth and she/he has not added their passport to their Yoti account, this will lead to a failed login

## Project Structure
* `resources/app-keypair.pem` is the keypair you can get from Yoti Hub.
* `resources/application.yml` contains the configuration that enforces SSL usage by your server-app (in case you are not using a proxy server like NGINX). Make sure that you update the SDK Application ID and the configuration points to the right path to the java keystore with an SSL key (there is an already one included in the project ``` server.keystore.jks ```).
* This project contains a Spring-boot server application. In this example we used the current SDK version by including the specific Maven dependency with its repository:
```xml
    <dependency>
      <groupId>com.yoti</groupId>
      <artifactId>yoti-sdk-api</artifactId>
      <version>4.0.0</version>
    </dependency>
```

## Building your server-app and run the example
* Copy the [application.yml.example](src/main/resources/application.yml.example) and rename it to `application.yml`
* Edit the newly renamed file and replace `yoti-client-sdk-id-from-hub` value with `Yoti client SDK ID` you can find in Yoti Hub
* Download your Application's key pair from Yoti Hub and save it to `resources/app-keypair.pem`
* Run `mvn clean package` to build the project

### Share v1
* In the Hub, set the scenario callback URL to `/login`. 
  * In order to receive calls on your /login endpoint, you need to expose your server-app to the outside world. 
  * You **must** use the domain from the Callback URL and HTTPS
* You can run your server-app by executing `java -jar target/yoti-sdk-spring-boot-example.jar`
* Navigate to:
    * [https://localhost:8443](https://localhost:8443) to initiate a login using Yoti. The Spring demo is listening for the response on `https://localhost:8443/login`
    * [https://localhost:8443/dynamic-share](https://localhost:8443/dynamic-share) to initiate a dynamic share with location with result displayed in profile page.
    * [https://localhost:8443/dbs-check](https://localhost:8443/dbs-check) to initiate a BDS standard check with location with result displayed in profile page.
    * [https://localhost:8443/advanced-identity-profile-check](https://localhost:8443/advanced-identity-profile-check) to initiate a multi scheme identity profile check (**UK_TFIDA**[DBS, RTW], **YOTI_GLOBAL**[IDENTITY]) with result displayed in profile page.

The logic for all the v1 share examples can be found in the `YotiLoginController`

### Share v2
* You can run your server-app by executing `java -jar -Dyoti.api.url="https://api.yoti.com/share" target/yoti-sdk-spring-boot-example.jar`. The JVM argument is required to override the default `https://api.yoti.com/api/v1`
* Navigate to:
  * [https://localhost:8443/v2/digital-identity-share](https://localhost:8443/v2/digital-identity-share) to initiate a login using the Yoti share v2

### Digital Identity Match
* You can run your server-app by executing `java -jar -Dyoti.api.url="https://api.yoti.com/did" target/yoti-sdk-spring-boot-example.jar`. The JVM argument is required to override the default `https://api.yoti.com/api/v1`
* Navigate to:
  * [https://localhost:8443/did/](https://localhost:8443/did/) to display the form to initiate a digital ID match

The logic for the v2 share example session creation and receipt can be found respectively in the `IdentitySessionController` and `IdentityLoginController`

## Requirements for running the application
* Java 8 or above
* If you are using Oracle JDK/JRE you need to install JCE extension in your server's Java to allow strong encryption (http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html). This is not a requirement if you are using OpenJDK
