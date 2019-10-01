package com.yoti.api.client.spi.remote;

import static org.bouncycastle.util.encoders.Base64.toBase64String;

import com.yoti.api.client.Media;

public class MediaValue implements Media {

    private final String mimeType;
    private final byte[] content;

    public MediaValue(String mimeType, byte[] content) {
        this.mimeType = mimeType;
        this.content = content;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public byte[] getContent() {
        return content.clone();
    }

    @Override
    public String getBase64Content() {
        return String.format("data:%s;base64,%s", getMimeType(), toBase64String(getContent()));
    }

}
