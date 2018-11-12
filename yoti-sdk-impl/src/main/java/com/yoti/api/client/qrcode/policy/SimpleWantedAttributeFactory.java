package com.yoti.api.client.qrcode.policy;

import java.util.List;

public final class SimpleWantedAttributeFactory implements WantedAttributeFactory {

    @Override
    public WantedAttribute create(String name, List<String> anchors, String derivation, boolean optional) {
        return new SimpleWantedAttribute(name, anchors, derivation, optional);
    }

}
