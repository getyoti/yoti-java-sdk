package com.yoti.api.client.spi.remote;

/**
 * Attribute value holding a PNG image.
 *
 */
public final class PngAttributeValue extends ImageAttributeValue {

    private static final String MIME_TYPE = "image/png";

    private final byte[] content;

    public PngAttributeValue(byte[] content) {
        this.content = content;
    }

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public byte[] getContent() {
        return content.clone();
    }

}
