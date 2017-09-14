package com.yoti.api.client.spi.remote;

/**
 * Attribute value holding a JPEG image.
 *
 */
final class JpegAttributeValue extends ImageAttributeValue {

    private static final String MIME_TYPE = "image/jpeg";

    private final byte[] content;

    public JpegAttributeValue(byte[] content) {
        this.content = newArray(content);
    }

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public byte[] getContent() {
        return newArray(content);
    }
}
