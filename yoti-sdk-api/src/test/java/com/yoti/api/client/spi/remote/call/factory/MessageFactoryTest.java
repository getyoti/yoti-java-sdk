package com.yoti.api.client.spi.remote.call.factory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertArrayEquals;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.yoti.api.client.spi.remote.call.HttpMethod;

import org.junit.Test;

public class MessageFactoryTest {

    private static final HttpMethod SOME_METHOD = HttpMethod.GET;
    private static final String SOME_PATH = "somePath";

    MessageFactory testObj = new MessageFactory();

    @Test
    public void shouldCreateWithoutABody() throws Exception {
        byte[] result = testObj.create(SOME_METHOD, SOME_PATH, null);

        assertArrayEquals((SOME_METHOD + "&" + SOME_PATH).getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    public void shouldCreateWhenBodyIsEmpty() throws Exception {
        byte[] result = testObj.create(SOME_METHOD, SOME_PATH, new byte[] {});

        assertArrayEquals((SOME_METHOD + "&" + SOME_PATH).getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    public void shouldCreateWithBody() {
        byte[] body = new byte[] { 100, 101, 102 };
        byte[] result = testObj.create(SOME_METHOD, SOME_PATH, body);

        assertThat(new String(result), startsWith(new String((SOME_METHOD + "&" + SOME_PATH).getBytes())));
        assertThat(new String(result), endsWith("&" + (Base64.getEncoder()
                .encodeToString(body))));
    }

}
