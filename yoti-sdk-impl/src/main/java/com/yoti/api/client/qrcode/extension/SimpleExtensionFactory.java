package com.yoti.api.client.qrcode.extension;

public final class SimpleExtensionFactory implements ExtensionFactory {

    @Override
    public Extension create(Extension.Type type, String content) {
        return new SimpleExtension(type, content);
    }

}
