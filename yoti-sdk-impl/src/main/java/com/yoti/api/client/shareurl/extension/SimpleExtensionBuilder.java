package com.yoti.api.client.shareurl.extension;

public class SimpleExtensionBuilder implements BasicExtensionBuilder {

    private String type;
    private Object content;

    public BasicExtensionBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public BasicExtensionBuilder withContent(Object content) {
        this.content = content;
        return this;
    }

    @Override
    public Extension<Object> build() {
        return new SimpleExtension(type, content);
    }

}
