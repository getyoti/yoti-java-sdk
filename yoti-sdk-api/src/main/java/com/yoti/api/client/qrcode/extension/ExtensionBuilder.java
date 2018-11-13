package com.yoti.api.client.qrcode.extension;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class ExtensionBuilder {

    private String type;
    private Map<String, ?> content;

    public ExtensionBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public ExtensionBuilder withContent(Map<String, ?> content) {
        this.content = content;
        return this;
    }

    public ExtensionBuilder forLocationConstraint(double latitude, double longitude, double radius, double maxUncertainty) {

        // FIXME: Validation

        Map<String, Double> expectedDeviceLocation = new HashMap<>();
        expectedDeviceLocation.put("latitude", latitude);
        expectedDeviceLocation.put("longitude", longitude);
        expectedDeviceLocation.put("radius", radius);
        expectedDeviceLocation.put("max_uncertainty_radius", maxUncertainty);

        Map<String, Map<String, Double>> content = new HashMap<>();
        content.put("expected_device_location", expectedDeviceLocation);

        this.type = "LOCATION_CONSTRAINT";
        this.content = content;
        return this;
    }

    public ExtensionBuilder forTransactionalFlow(Map<String, ?> content) {
        this.type = "TRANSACTIONAL_FLOW";
        this.content = content;
        return this;
    }

    public ExtensionBuilder forAgeSecret(String secretValue) {
        Map<String, String> content = new HashMap<>();
        content.put("secret_type", "EMBEDDED");
        content.put("secret_value", secretValue);
        this.type = "AGE_VERIFICATION_SECRET";
        this.content = content;
        return this;
    }

    public Extension build() {
        ServiceLoader<ExtensionFactory> factoryLoader = ServiceLoader.load(ExtensionFactory.class);
        if (!factoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of ExtensionFactory");
        }
        ExtensionFactory extensionFactory = factoryLoader.iterator().next();
        return extensionFactory.create(type, content);
    }

}
