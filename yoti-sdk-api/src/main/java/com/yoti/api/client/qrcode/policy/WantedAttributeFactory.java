package com.yoti.api.client.qrcode.policy;

import java.util.List;

public interface WantedAttributeFactory {

    WantedAttribute create(String name, List<String> anchors, String derivation, boolean optional);

}
