package com.yoti.api.client.spi.remote.call.factory;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PathFactoryTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String SOME_TOKEN = "someToken";

    PathFactory testObj = new PathFactory();

    @Test
    public void shouldCreateProfilePath() {
        String result = testObj.createProfilePath(SOME_APP_ID, SOME_TOKEN);

        URI uri = URI.create(result);
        assertEquals("/profile/" + SOME_TOKEN, uri.getPath());
        assertTrue(uri.getQuery().contains("appId=" + SOME_APP_ID));
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

    @Test
    public void shouldCreateAmlPath() {
        String result = testObj.createAmlPath(SOME_APP_ID);

        URI uri = URI.create(result);
        assertEquals("/aml-check", uri.getPath());
        assertTrue(uri.getQuery().contains("id=" + SOME_APP_ID));
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

}
