package com.yoti.api.client.qrcode.policy;

import java.util.List;

public interface AttributeFactory {

    Attribute create(String name, List<String> anchors, String derivation, boolean optional);

}
