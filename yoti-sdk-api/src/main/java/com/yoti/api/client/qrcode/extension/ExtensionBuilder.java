package com.yoti.api.client.qrcode.extension;

import java.util.Map;
import java.util.ServiceLoader;

public abstract class ExtensionBuilder {

    public static final ExtensionBuilder newInstance() {
        ServiceLoader<ExtensionBuilder> extensionBuilderLoader = ServiceLoader.load(ExtensionBuilder.class);
        if (!extensionBuilderLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of ExtensionBuilder");
        }
        ExtensionBuilder extensionBuilder = extensionBuilderLoader.iterator().next();
        return extensionBuilder.createExtensionBuilder();
    }

    protected abstract ExtensionBuilder createExtensionBuilder();

    public abstract ExtensionBuilder withType(String type);

    public abstract ExtensionBuilder withContent(Map<String, ?> content);

    public abstract ExtensionBuilder forLocationConstraint(double latitude, double longitude, double radius, double maxUncertainty);

    public abstract ExtensionBuilder forTransactionalFlow(Map<String, ?> content);

    public abstract ExtensionBuilder forAgeSecret(String secretValue);

    public abstract Extension build();

}
