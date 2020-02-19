package com.yoti.api.client.spi.remote.call.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.net.URI;

public class PathFactoryTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String SOME_TOKEN = "someToken";
    private static final String SOME_SESSION_ID = "someSessionId";
    private static final String SOME_MEDIA_ID = "someMediaId";

    private static PathFactory testObj = new PathFactory();

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
        assertTrue(uri.getQuery().contains("appId=" + SOME_APP_ID));
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

    @Test
    public void shouldCreateDynamicSharingPath() {
        String result = testObj.createDynamicSharingPath(SOME_APP_ID);

        URI uri = URI.create(result);
        assertEquals("/qrcodes/apps/" + SOME_APP_ID, uri.getPath());
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

    @Test
    public void shouldCreateNewYotiDocsSessionPath() {
        String result = testObj.createNewYotiDocsSessionPath(SOME_APP_ID);

        URI uri = URI.create(result);
        assertEquals("/sessions", uri.getPath());
        assertTrue(uri.getQuery().contains("sdkId=" + SOME_APP_ID));
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

    @Test
    public void shouldCreateYotiDocsSessionPath() {
        String result = testObj.createGetYotiDocsSessionPath(SOME_APP_ID, SOME_SESSION_ID);

        URI uri = URI.create(result);
        assertEquals("/sessions/" + SOME_SESSION_ID, uri.getPath());
        assertTrue(uri.getQuery().contains("sdkId=" + SOME_APP_ID));
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

    @Test
    public void shouldCreateMediaContentPath() {
        String result = testObj.createMediaContentPath(SOME_APP_ID, SOME_SESSION_ID, SOME_MEDIA_ID);

        URI uri = URI.create(result);
        assertEquals("/sessions/" + SOME_SESSION_ID + "/media/" + SOME_MEDIA_ID + "/content", uri.getPath());
        assertTrue(uri.getQuery().contains("sdkId=" + SOME_APP_ID));
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

    @Test
    public void shouldCreateSupportedDocumentsPath() {
        String result = testObj.createGetSupportedDocumentsPath();

        URI uri = URI.create(result);
        assertEquals("/supported-documents", uri.getPath());
        assertTrue(uri.getQuery().matches("(.*)nonce=(?i)[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}(.*)"));
        assertTrue(uri.getQuery().contains("timestamp="));
    }

}
