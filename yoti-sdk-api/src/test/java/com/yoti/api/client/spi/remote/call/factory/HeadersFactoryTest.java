package com.yoti.api.client.spi.remote.call.factory;

import static com.yoti.api.client.spi.remote.call.YotiConstants.AUTH_KEY_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SDK_VERSION;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_VERSION_HEADER;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

import java.util.Map;

import org.junit.*;

public class HeadersFactoryTest {

    private static final String SOME_DIGEST = "someDigest";
    private static final String SOME_KEY = "someKey";

    HeadersFactory testObj = new HeadersFactory();

    @Test
    public void shouldCreateWithDigest() {
        Map<String, String> result = testObj.create(SOME_DIGEST);

        assertThat(result, hasEntry(DIGEST_HEADER, SOME_DIGEST));
        assertThat(result, hasEntry(YOTI_SDK_HEADER, JAVA));
        assertThat(result, hasEntry(YOTI_SDK_VERSION_HEADER, SDK_VERSION));
    }

    @Test
    public void shouldCreateWithDigestAndKey() {
        Map<String, String> result = testObj.create(SOME_DIGEST, SOME_KEY);

        assertThat(result, hasEntry(DIGEST_HEADER, SOME_DIGEST));
        assertThat(result, hasEntry(YOTI_SDK_HEADER, JAVA));
        assertThat(result, hasEntry(YOTI_SDK_VERSION_HEADER, SDK_VERSION));
        assertThat(result, hasEntry(AUTH_KEY_HEADER, SOME_KEY));
    }

}
