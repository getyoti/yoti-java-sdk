package com.yoti.api.client.qrcode.policy;

public final class SimpleWantedAttributeFactory implements WantedAttributeFactory {

    @Override
    public WantedAttribute create(String name, String derivation, boolean optional) {
        return new SimpleWantedAttribute(name, derivation, optional);
    }

}
