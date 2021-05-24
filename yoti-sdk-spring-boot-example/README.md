# Spring Boot Yoti SDK Example

This project shows an example implementation of a server-app with an endpoint which will be called by Yoti with a `token`.
You will need to pass this token to Yoti-SDK in order to retrieve the profile of a user which has been logged in by Yoti.

Before you start, you'll need to create an Application in [Yoti Hub](https://hub.yoti.com) and verify the domain.

**NOTE: While creating Application in Yoti Hub, some of the attributes (except phone number and selfie) require users to have a Yoti with a verified passport. If your application, for instance, requires the user's date of birth and she/he has not added their passport to their Yoti account, this will lead to a failed login.**

## Project Structure
* The logic for retrieving the profile can be found in `com.yoti.api.examples.springboot.YotiLoginController#doLogin`.
* `resources/app-keypair.pem` is the keypair you can get from Yoti Hub.
* `resource/application.yml` contains the configuration that enforces SSL usage by your server-app (in case you are not using a proxy server like NGINX). Make sure that you update the SDK Application ID and the configuration points to the right path to the java keystore with an SSL key (there is an already one included in the project ``` server.keystore.jks ```).
* This project contains a Spring-boot server application. In this example we used the current SDK version by including the specific Maven dependency with its repository:
```xml
    <dependency>
      <groupId>com.yoti</groupId>
      <artifactId>yoti-sdk-api</artifactId>
      <version>3.2.1-SNAPSHOT</version>
    </dependency>
```

## Building your example server-app
1. In the [Yoti Hub](https://hub.yoti.com) set the application domain of your app to `https://localhost:8443/`. Note that your endpoint must be accessible from the machine that is displaying the QR code.
1. Still in the Hub, set the scenario callback URL to `/login`. 
1. Copy the [resources/application.yml.example](src/main/resources/application.yml.example) and create a new file `resources/application.yml`
1. Edit the `resources/application.yml` and replace the `yoti-client-sdk-id-from-hub` value with the `Yoti client SDK ID` you can find in Yoti Hub.
1. Download your Application's key pair from Yoti Hub and copy it to `resources/app-keypair.pem`.
1. Run `mvn clean package` to build the project.

## Running
* You can run your server-app by executing `java -jar target/yoti-sdk-spring-boot-example-3.2.1-SNAPSHOT.jar`
  * If you are using Java 9, you can run the server-app as follows `java -jar target/yoti-sdk-spring-boot-example-3.2.1-SNAPSHOT.jar --add-exports java.base/jdk.internal.ref=ALL-UNNAMED`
* Navigate to `https://localhost:8443`
* You can then initiate a login using Yoti.  The Spring demo is listening for the response on `https://localhost:8443/login`.

In order to receive calls on your /login endpoint, you need to expose your server-app to the outside world. We require that you use the domain from the Callback URL and HTTPS.

## Requirements for running the application
* Java 8 or above
* If you are using Oracle JDK/JRE you need to install JCE extension in your server's Java to allow strong encryption (http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html). This is not a requirement if you are using OpenJDK.


