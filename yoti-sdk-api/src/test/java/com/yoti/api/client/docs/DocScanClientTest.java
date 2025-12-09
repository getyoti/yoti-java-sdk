package com.yoti.api.client.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.common.StaticKeyPairSource;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.instructions.Instructions;
import com.yoti.api.client.spi.remote.call.factory.AuthStrategy;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocScanClientTest {

    private static final String APP_ID = "appId";
    private static final String SOME_SESSION_ID = "someSessionId";
    private static final String SOME_MEDIA_ID = "someMediaId";

    @Mock DocScanService docScanServiceMock;

    @Mock SessionSpec sessionSpecMock;
    @Mock Instructions instructionsMock;
    private KeyPairSource validKeyPairSource;

    @Before
    public void setUp() {
        validKeyPairSource = new StaticKeyPairSource(CryptoUtil.KEY_PAIR_PEM);
    }

    @Test
    public void constructor_shouldFailWhenStreamExceptionLoadingKeys() {
        KeyPairSource badKeyPairSource = new StaticKeyPairSource(true);

        InitialisationException ex = assertThrows(InitialisationException.class, () -> new DocScanClient(APP_ID, badKeyPairSource, docScanServiceMock));

        assertThat(ex.getCause(), is(instanceOf(IOException.class)));
        assertThat(ex.getCause().getMessage(), containsString("Test stream exception"));
    }

    @Test
    public void createDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        when(docScanServiceMock.createSession(any(AuthStrategy.class), eq(sessionSpecMock))).thenThrow(original);
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.createSession(sessionSpecMock));

        assertThat(thrown, is(original));
    }

    @Test
    public void getDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        when(docScanServiceMock.retrieveSession(any(AuthStrategy.class), eq(SOME_SESSION_ID))).thenThrow(original);
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.getSession(SOME_SESSION_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void getDocScanMedia_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        when(docScanServiceMock.getMediaContent(any(AuthStrategy.class), eq(SOME_SESSION_ID), eq(SOME_MEDIA_ID))).thenThrow(original);
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.getMediaContent(SOME_SESSION_ID, SOME_MEDIA_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void deleteDocScanMedia_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).deleteMediaContent(any(AuthStrategy.class), eq(SOME_SESSION_ID), eq(SOME_MEDIA_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.deleteMediaContent(SOME_SESSION_ID, SOME_MEDIA_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void deleteDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).deleteSession(any(AuthStrategy.class), eq(SOME_SESSION_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.deleteSession(SOME_SESSION_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void putIbvInstructions_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).putIbvInstructions(any(AuthStrategy.class), eq(SOME_SESSION_ID), eq(instructionsMock));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.putIbvInstructions(SOME_SESSION_ID, instructionsMock));

        assertThat(exception, is(original));
    }

    @Test
    public void getIbvInstructions_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).getIbvInstructions(any(AuthStrategy.class), eq(SOME_SESSION_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.getIbvInstructions(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void getIbvInstructionsPdf_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).getIbvInstructionsPdf(any(AuthStrategy.class), eq(SOME_SESSION_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.getIbvInstructionsPdf(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).fetchInstructionsContactProfile(any(AuthStrategy.class), eq(SOME_SESSION_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.fetchInstructionsContactProfile(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void triggerIbvEmailNotification_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).triggerIbvEmailNotification(any(AuthStrategy.class), eq(SOME_SESSION_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.triggerIbvEmailNotification(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void getSessionConfiguration_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).fetchSessionConfiguration(any(AuthStrategy.class), eq(SOME_SESSION_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.getSessionConfiguration(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void getSupportedDocuments_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).getSupportedDocuments(any(AuthStrategy.class), any(Boolean.class));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.getSupportedDocuments());

        assertThat(thrown, is(original));
    }

    @Test
    public void getTrackedDevices_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).getTrackedDevices(any(AuthStrategy.class), eq(SOME_SESSION_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.getTrackedDevices(SOME_SESSION_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void deleteTrackedDevices_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).deleteTrackedDevices(any(AuthStrategy.class), eq(SOME_SESSION_ID));
        DocScanClient testObj = new DocScanClient(APP_ID, validKeyPairSource, docScanServiceMock);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.deleteTrackedDevices(SOME_SESSION_ID));

        assertThat(thrown, is(original));
    }

}
