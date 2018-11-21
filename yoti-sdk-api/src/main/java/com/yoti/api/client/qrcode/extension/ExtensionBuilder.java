package com.yoti.api.client.qrcode.extension;

public interface ExtensionBuilder<T> {

    /**
     * @return An object, T, built with parameters of this builder
     */
    Extension<T> build();

}
