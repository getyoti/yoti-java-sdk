Spring Boot Yoti SDK example
=============================

This project shows an example implementation of a server-app with an endpoint which will be called by Yoti with a "token".
You will need to pass this token to Yoti-SDK in order to retrieve user data (profile) of a user which has been logged in by Yoti.

**Before you start, you'll need to create an application in Dashboard and verify the domain (https://www.yoti.com/dashboard).**

# Project Structure
* Logic for retrieving the profile is in ```com.yoti.api.examples.springboot.YotiLoginController#doLogin```
* ```resources/app-keypair.pem``` keypair you can get from Dashboard
* ```resource/application.yml``` serve-app configuration. You can change the port and SDK Application Id
* ```resource/application.yml.ssl``` alternative configuration that enforces SSL usage by your server-app (no need for nginx ssl proxy). To use it, just rename it to 'application.yml', make sure that the configuration has the right path to the java keystore with an SSL key (example one included in the project ``` server.keystore.jks ```.
* Project is a Spring-boot server application. In maven we used the current SDK version:
```
    <dependency>
      <groupId>com.yoti</groupId>
      <artifactId>java-sdk-impl</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
```

# Building and running your example server-app
1. In the Dashboard, edit "Callback URL" with the URL pointing to your /login endpoint
2. Edit the **resources/application.yml** and replace the "yoti-client-sdk-id-from-dashboard" value with the Yoti client SDK ID you can find in the Dashboard
3. Download your application key from Yoti-Dashboard and copy it to **/resources/app-keypair.pem**
4. Run ```mvn clean package``` to build the project.

# Running
* You can run your server-app by executing ```java -jar target/java-sdk-springboot-example-1.0-SNAPSHOT.jar```
* Your endpoint is listening under **http://localhost:8080/login**.

In order to receive calls on your /login endpoint, you need to expose your server-app to the outside world. We require that you use the domain from the Callback URL and **HTTPS**. 
In order to add HTTPS to your plain HTTP server-app, we suggest to use HTTPS termination with Nginx. You can follow the guide under http://xxx.
Alternatively you can use HTTPS directly in the server-app by using ``` application.yml.ssl ``` configuration.

# Requirements for running the application
* Java6 and newer
* If you're using oracle JDK/JRE (optional for OpenJDK) please install JCE extension in your server's Java to allow strong encryption (http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)
* In case of using JRE/JDK older than Java 8_101, please import the LetsEncrypt certificate in your Java keystore by using Java key-tool
```
wget https://letsencrypt.org/certs/lets-encrypt-x3-cross-signed.pem
keytool -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt -importcert -alias letsencryptauthorityx3 -file lets-encrypt-x3-cross-signed.pem
```



