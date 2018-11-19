package com.yoti.api.client.qrcode.extension;

public interface BasicExtensionBuilder extends ExtensionBuilder<Object> {

    BasicExtensionBuilder withType(String type);

    BasicExtensionBuilder withContent(Object content);

}
