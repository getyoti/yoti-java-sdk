package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_PNG;

import static org.bouncycastle.util.encoders.Base64.toBase64String;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class MediaValueTest {

    private static final String SOME_MIME_TYPE = CONTENT_TYPE_PNG;
    private static final byte[] SOME_CONTENT = new byte[]{1, 2, 3, 4};

    private static final String BASE_64_CONTENT_FORMAT = "data:%s;base64,%s";
    private static final String SOME_BASE_64_CONTENT_STRING = String.format(BASE_64_CONTENT_FORMAT, SOME_MIME_TYPE, toBase64String(SOME_CONTENT));

    @Test
    public void shouldStoreDataCorrectly() {
        MediaValue result = new MediaValue(SOME_MIME_TYPE, SOME_CONTENT);

        assertThat(result.getMimeType(), is(SOME_MIME_TYPE));
        assertThat(result.getContent(), equalTo(SOME_CONTENT));
    }

    @Test
    public void shouldReturnCorrectlyFormattedBase64ContentString() {
        MediaValue result = new MediaValue(SOME_MIME_TYPE, SOME_CONTENT);

        assertThat(result.getBase64Content(), is(SOME_BASE_64_CONTENT_STRING));
    }

}
