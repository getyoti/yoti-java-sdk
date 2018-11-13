package com.yoti.api.client.qrcode.policy;

public interface WantedAttributeFactory {

    WantedAttribute create(String name, String derivation, boolean optional);

}
