package com.yoti.api.client;

/**
 * Represents media with some mimeType
 *
 */
public interface Media {

    /**
     * Get mime type of the content
     *
     * @return mime type
     */
    String getMimeType();

    /**
     * Media content
     *
     * @return media as byte[]
     */
    byte[] getContent();

    /**
     * Base64 encoded media content
     *
     * @return media as Base64 encoded String
     */
    String getBase64Content();

}
