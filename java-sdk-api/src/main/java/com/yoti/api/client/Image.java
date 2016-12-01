package com.yoti.api.client;

/**
 * Attribute value that holds an image such as a selfie or an application logo.
 *
 */
public interface Image {
    /**
     * Get mime type of the content.
     *
     * @return mime type
     */
    String getMimeType();

    /**
     * Image content.
     *
     * @return image as byte[]
     */
    byte[] getContent();
}
