package com.yoti.api.client.spi.remote.call.factory;

import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SDK_VERSION;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_VERSION_HEADER;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

import java.util.Map;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

public class HeadersFactoryTest {

    private static final String SOME_KEY = "someKey";
    private static final String SOME_VALUE = "someValue";

    HeadersFactory testObj = new HeadersFactory();

    @Test
    public void shouldCreate() {
        Header header = new BasicHeader(SOME_KEY, SOME_VALUE);

        Map<String, String> result = testObj.create(header);

        assertThat(result, hasEntry(SOME_KEY, SOME_VALUE));
        assertThat(result, hasEntry(YOTI_SDK_HEADER, JAVA));
        assertThat(result, hasEntry(YOTI_SDK_VERSION_HEADER, SDK_VERSION));
    }

}
