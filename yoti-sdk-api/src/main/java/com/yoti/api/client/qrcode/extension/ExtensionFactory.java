package com.yoti.api.client.qrcode.extension;

import java.util.Map;

public interface ExtensionFactory {

    Extension create(String type, Map<String,?> content);

}
