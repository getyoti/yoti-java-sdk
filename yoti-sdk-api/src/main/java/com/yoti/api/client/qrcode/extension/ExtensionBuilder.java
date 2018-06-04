package com.yoti.api.client.qrcode.extension;

import java.util.ServiceLoader;

public class ExtensionBuilder {

    private Extension.Type type;

    private String content;

    public ExtensionBuilder type(Extension.Type type) {
        this.type = type;
        return this;
    }

    public ExtensionBuilder content(String content) {
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
