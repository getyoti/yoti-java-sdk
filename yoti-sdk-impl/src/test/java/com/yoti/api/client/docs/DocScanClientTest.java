package com.yoti.api.client.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.common.StaticKeyPairSource;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.spi.remote.util.CryptoUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.security.KeyPair;

@RunWith(MockitoJUnitRunner.class)
public class DocScanClientTest {

    private static final String TOKEN = "test-token-test-test-test";
    private static final String APP_ID = "appId";
    private static final String SOME_SESSION_ID = "someSessionId";
    private static final String SOME_MEDIA_ID = "someMediaId";

    @Mock DocScanService docScanServiceMock;

    @Mock SessionSpec sessionSpecMock;
    private KeyPairSource validKeyPairSource;

    @Before
    public void setUp() {
        validKeyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);
    }

    @Test
    public void constructor_shouldFailWhenStreamExceptionLoadingKeys() {
        KeyPairSource badKeyPairSource = new StaticKeyPairSource(true);

        try {
            new DocScanClient(APP_ID, badKeyPairSource, docScanServiceMock);
        } catch (InitialisationException e) {
            assertThat(e.getCause(), is(instanceOf(IOException.class)));
            assertThat(e.getCause().getMessage(), containsString("Test stream exception"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void createDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");

        when(docScanServiceMock.createSession(eq(APP_ID), any(KeyPair.class), eq(sessionSpecMock))).thenThrow(original);

        try {
            DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);
            testObj.createSession(sessionSpecMock);
        } catch (DocScanException thrown) {
            assertThat(thrown, is(original));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");

        when(docScanServiceMock.retrieveSession(eq(APP_ID), any(KeyPair.class), eq(SOME_SESSION_ID))).thenThrow(original);

        try {
            DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);
            testObj.getSession(SOME_SESSION_ID);
        } catch (DocScanException thrown) {
            assertThat(thrown, is(original));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getDocScanMedia_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");

        when(docScanServiceMock.getMediaContent(eq(APP_ID), any(KeyPair.class), eq(SOME_SESSION_ID), eq(SOME_MEDIA_ID))).thenThrow(original);

        try {
            DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);
            testObj.getMediaContent(SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException thrown) {
            assertThat(thrown, is(original));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteDocScanMedia_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");

        doThrow(original).when(docScanServiceMock).deleteMediaContent(eq(APP_ID), any(KeyPair.class), eq(SOME_SESSION_ID), eq(SOME_MEDIA_ID));

        try {
            DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);
            testObj.deleteMediaContent(SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException thrown) {
            assertThat(thrown, is(original));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");

        doThrow(original).when(docScanServiceMock).deleteSession(eq(APP_ID), any(KeyPair.class), eq(SOME_SESSION_ID));

        try {
            DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);
            testObj.deleteSession(SOME_SESSION_ID);
        } catch (DocScanException thrown) {
            assertThat(thrown, is(original));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getSupportedDocuments_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");

        doThrow(original).when(docScanServiceMock).getSupportedDocuments(any(KeyPair.class));

        try {
            DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);
            testObj.getSupportedDocuments();
        } catch (DocScanException thrown) {
            assertThat(thrown, is(original));
            return;
        }
        fail("Expected an exception");
    }

}
