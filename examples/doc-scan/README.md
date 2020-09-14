# Doc Scan Example

## Running the example

1. Move into the Doc Scan example directory i.e. `cd examples/doc-scan`
1. Place the PEM file from Yoti Hub into `src/main/resources`
1. Rename the [application.yml.example](src/main/resources/application.yml.example) file to `application.yml` and fill in the required values (`sdkId` and `pemFileLocation`)
1. Build the application with `mvn clean package`
1. Run the application from the root folder with `java -jar target/doc-scan-demo-1.0.0.jar`
1. Visit `https://localhost:8443`