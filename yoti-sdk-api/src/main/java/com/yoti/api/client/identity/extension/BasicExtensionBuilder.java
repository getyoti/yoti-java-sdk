package com.yoti.api.client.identity.extension;

public class BasicExtensionBuilder implements ExtensionBuilder<Object> {

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

    public String getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public Extension<Object> build() {
        return new Extension<>(type, content);
    }

}
