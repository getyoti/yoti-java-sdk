package com.yoti.api.client.qrcode.extension;

import java.util.Map;

public final class SimpleExtensionFactory implements ExtensionFactory {

    @Override
    public Extension create(String type, Map<String, ?> content) {
        return new SimpleExtension(type, content);
    }

}
