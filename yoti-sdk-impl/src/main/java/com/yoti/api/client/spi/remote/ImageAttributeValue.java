package com.yoti.api.client.spi.remote;

import com.yoti.api.client.Image;

/**
 * Image attribute values.
 *
 */
abstract class ImageAttributeValue implements Image {

    /**
     * Get mime type of the content.
     *
     * @return mime type
     */
    @Override
    public abstract String getMimeType();

    /**
     * Image content.
     *
     * @return image as byte[]
     */
    @Override
    public abstract byte[] getContent();

    protected byte[] newArray(byte[] source) {
        byte[] result = new byte[source.length];
        System.arraycopy(source, 0, result, 0, source.length);
        return result;
    }
}
