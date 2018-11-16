package com.yoti.api.client.qrcode.extension;

import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.spi.remote.util.Validation;

public class SimpleExtensionBuilder extends ExtensionBuilder {

    private String type;
    private Map<String, ?> content;

    @Override
    protected ExtensionBuilder createExtensionBuilder() {
        return new SimpleExtensionBuilder();
    }

    public ExtensionBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public ExtensionBuilder withContent(Map<String, ?> content) {
        this.content = content;
        return this;
    }

    public ExtensionBuilder forLocationConstraint(double latitude, double longitude, double radius, double maxUncertainty) {
        Validation.withinRange(latitude, -90d, 90d, "latitude");
        Validation.withinRange(longitude, -180d, 180d, "longitude");
        Validation.notLessThan(radius, 0d, "radius");
        Validation.notLessThan(maxUncertainty, 0d, "maxUncertainty");

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
        Validation.notNull(content, "content");
        this.type = "TRANSACTIONAL_FLOW";
        this.content = content;
        return this;
    }

    public ExtensionBuilder forAgeSecret(String secretValue) {
        Validation.notNullOrEmpty(secretValue, "secretValue");
        Map<String, String> content = new HashMap<>();
        content.put("secret_type", "EMBEDDED");
        content.put("secret_value", secretValue);
        this.type = "AGE_VERIFICATION_SECRET";
        this.content = content;
        return this;
    }

    public Extension build() {
        return new SimpleExtension(type, content);
    }

}
