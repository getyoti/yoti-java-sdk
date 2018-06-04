package com.yoti.api.client.qrcode.extension;

public interface ExtensionFactory {

    Extension create(Extension.Type type, String content);

}
