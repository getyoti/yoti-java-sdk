package com.yoti.api.client.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.instructions.Instructions;
import com.yoti.api.client.spi.remote.call.factory.AuthStrategy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocScanClientTest {

    private static final String SOME_SESSION_ID = "someSessionId";
    private static final String SOME_MEDIA_ID = "someMediaId";

    @InjectMocks DocScanClient testObj;

    @Mock DocScanService docScanServiceMock;
    @Mock AuthStrategy authStrategyMock;

    @Mock SessionSpec sessionSpecMock;
    @Mock Instructions instructionsMock;

    @Test
    public void createDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        when(docScanServiceMock.createSession(authStrategyMock, sessionSpecMock)).thenThrow(original);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.createSession(sessionSpecMock));

        assertThat(thrown, is(original));
    }

    @Test
    public void getDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        when(docScanServiceMock.retrieveSession(authStrategyMock, SOME_SESSION_ID)).thenThrow(original);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.getSession(SOME_SESSION_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void getDocScanMedia_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        when(docScanServiceMock.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID)).thenThrow(original);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.getMediaContent(SOME_SESSION_ID, SOME_MEDIA_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void deleteDocScanMedia_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).deleteMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.deleteMediaContent(SOME_SESSION_ID, SOME_MEDIA_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void deleteDocScanSession_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).deleteSession(authStrategyMock, SOME_SESSION_ID);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.deleteSession(SOME_SESSION_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void putIbvInstructions_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).putIbvInstructions(authStrategyMock, SOME_SESSION_ID, instructionsMock);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.putIbvInstructions(SOME_SESSION_ID, instructionsMock));

        assertThat(exception, is(original));
    }

    @Test
    public void getIbvInstructions_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).getIbvInstructions(authStrategyMock, SOME_SESSION_ID);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.getIbvInstructions(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void getIbvInstructionsPdf_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).getIbvInstructionsPdf(authStrategyMock, SOME_SESSION_ID);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.getIbvInstructionsPdf(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).fetchInstructionsContactProfile(authStrategyMock, SOME_SESSION_ID);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.fetchInstructionsContactProfile(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void triggerIbvEmailNotification_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).triggerIbvEmailNotification(authStrategyMock, SOME_SESSION_ID);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.triggerIbvEmailNotification(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void getSessionConfiguration_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).fetchSessionConfiguration(authStrategyMock, SOME_SESSION_ID);

        DocScanException exception = assertThrows(DocScanException.class, () -> testObj.getSessionConfiguration(SOME_SESSION_ID));

        assertThat(exception, is(original));
    }

    @Test
    public void getSupportedDocuments_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).getSupportedDocuments(authStrategyMock, false);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.getSupportedDocuments());

        assertThat(thrown, is(original));
    }

    @Test
    public void getTrackedDevices_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).getTrackedDevices(authStrategyMock, SOME_SESSION_ID);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.getTrackedDevices(SOME_SESSION_ID));

        assertThat(thrown, is(original));
    }

    @Test
    public void deleteTrackedDevices_shouldFailWithExceptionFromYotiDocsService() throws Exception {
        DocScanException original = new DocScanException("Test exception");
        doThrow(original).when(docScanServiceMock).deleteTrackedDevices(authStrategyMock, SOME_SESSION_ID);

        DocScanException thrown = assertThrows(DocScanException.class, () -> testObj.deleteTrackedDevices(SOME_SESSION_ID));

        assertThat(thrown, is(original));
    }

}
