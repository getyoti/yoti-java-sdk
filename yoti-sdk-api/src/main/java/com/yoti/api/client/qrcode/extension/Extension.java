package com.yoti.api.client.qrcode.extension;

/**
 * Type and content of a feature for an application
 *
 */
public interface Extension<T> {

    /**
     * Get the feature's type
     *
     * @return the type of the operation
     */
    String getType();

    /**
     * Get the feature's details
     *
     * @return the payload of the operation
     */
    T getContent();

}
