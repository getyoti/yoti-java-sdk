package com.yoti.api.client.spi.remote;

import static org.bouncycastle.util.encoders.Base64.toBase64String;

import com.yoti.api.client.Image;

/**
 * Image attribute values.
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

    @Override
    public String getBase64Content() {
        return String.format("data:%s;base64,%s", getMimeType(), toBase64String(getContent()));
    }

}
