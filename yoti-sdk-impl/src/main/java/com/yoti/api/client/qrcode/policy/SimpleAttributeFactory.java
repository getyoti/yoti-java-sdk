package com.yoti.api.client.qrcode.policy;

import java.util.List;

public final class SimpleAttributeFactory implements AttributeFactory {

    @Override
    public Attribute create(String name, List<String> anchors, String derivation, boolean optional) {
        return new SimpleAttribute(name, anchors, derivation, optional);
    }

}
