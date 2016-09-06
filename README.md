Spring Boot Yoti SDK example
=============================

This project shows an example implementation of a server-app with an endpoint which will be called by Yoti with a "token".
You will need to pass this token to Yoti-SDK in order to retrieve user data (profile) of a user which has been logged in by Yoti.

**Before you start, you'll need to create an application in Dashboard and verify the domain (https://www.yoti.com/dashboard).**

**NOTE: While creating application in Dashboard, some of the attributes (except: phone number, selfie) require users to have a Yoti with a verified passport. If your application, for instance, requires the user's date of birth and your user has not added her/his passport to her/his Yoti account, this will lead to a failed login.**

# Project Structure
* Logic for retrieving the profile is in ```com.yoti.api.examples.springboot.YotiLoginController#doLogin```
* ```resources/app-keypair.pem``` keypair you can get from Dashboard
* ```resource/application.yml``` this configuration that enforces SSL usage by your server-app (no need for nginx ssl proxy). Make sure that update the SDK Application ID and the configuration has the right path to the java keystore with an SSL key (example one included in the project ``` server.keystore.jks ```).
* ```resource/application.yml.plain``` HTTP server-app configuration. To use it, just rename it to 'application.yml', this requires a Nginx proxy
* Project is a Spring-boot server application. In maven we used the current SDK version:
```xml
    <dependency>
      <groupId>com.yoti</groupId>
      <artifactId>java-sdk-impl</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
```

# Building and running your example server-app
1. In the Dashboard, edit "Callback URL" with the URL pointing to your /login endpoint. Note that this requires an endpoint visible on the Internet (see http://yoti.com/developers for more details).
2. Edit the **resources/application.yml** and replace the "yoti-client-sdk-id-from-dashboard" value with the Yoti client SDK ID you can find in the Dashboard
3. Download your application key from Yoti-Dashboard and copy it to ** resources/app-keypair.pem **
4. Run ```mvn clean package``` to build the project.

# Running
* You can run your server-app by executing ```java -jar target/java-sdk-springboot-example-1.0-SNAPSHOT.jar```
* Your endpoint is listening under **https://localhost/login**.

In order to receive calls on your /login endpoint, you need to expose your server-app to the outside world. We require that you use the domain from the Callback URL and **HTTPS**.

# Requirements for running the application
* Minimum Java 6
* If you're using oracle JDK/JRE (optional for OpenJDK) please install JCE extension in your server's Java to allow strong encryption (http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)
* In case of using JRE/JDK older than Java 8_101, please import the LetsEncrypt certificate in your Java keystore by using Java key-tool
```
wget https://letsencrypt.org/certs/lets-encrypt-x3-cross-signed.pem
keytool -trustcacerts -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt -importcert -alias letsencryptauthorityx3 -file lets-encrypt-x3-cross-signed.pem
```



