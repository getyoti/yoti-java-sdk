package com.yoti.api.client.spi.remote.call.factory;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProfilePathFactoryTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String SOME_TOKEN = "someToken";

    ProfilePathFactory testObj = new ProfilePathFactory();

    @Test
    public void shouldCreate() {
        String result = testObj.create(SOME_APP_ID, SOME_TOKEN);

        URI uri = URI.create(result);
        assertEquals("/profile/" + SOME_TOKEN, uri.getPath());
        assertTrue(uri.getQuery().contains("appId=" + SOME_APP_ID));
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

}
