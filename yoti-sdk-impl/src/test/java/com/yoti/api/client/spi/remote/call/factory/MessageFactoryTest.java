package com.yoti.api.client.spi.remote.call.factory;

import org.junit.Test;

import static com.yoti.api.client.spi.remote.Base64.base64;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class MessageFactoryTest {

    private static final String SOME_METHOD = "someMethod";
    private static final String SOME_PATH = "somePath";

    MessageFactory testObj = new MessageFactory();

    @Test
    public void shouldCreateWithoutABody() throws Exception {
        byte[] result = testObj.create(SOME_METHOD, SOME_PATH, null);

        assertArrayEquals((SOME_METHOD + "&" + SOME_PATH).getBytes("UTF-8"), result);
    }

    @Test
    public void shouldCreateWhenBodyIsEmpty() throws Exception {
        byte[] result = testObj.create(SOME_METHOD, SOME_PATH, new byte[] {});

        assertArrayEquals((SOME_METHOD + "&" + SOME_PATH).getBytes("UTF-8"), result);
    }

    @Test
    public void shouldCreateWithBody() throws Exception {
        byte[] body = new byte[] { 100, 101, 102 };
        byte[] result = testObj.create(SOME_METHOD, SOME_PATH, body);

        assertThat(new String(result), startsWith(new String((SOME_METHOD + "&" + SOME_PATH).getBytes())));
        assertThat(new String(result), endsWith("&" + new String((base64(body)))));
    }

}
