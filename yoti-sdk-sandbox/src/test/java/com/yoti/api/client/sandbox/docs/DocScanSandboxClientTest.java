package com.yoti.api.client.sandbox.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.KeyPair;

import com.yoti.api.client.sandbox.SandboxException;
import com.yoti.api.client.sandbox.docs.request.ResponseConfig;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocScanSandboxClientTest {

    private static final byte[] SOME_BYTES = new byte[] { 1, 2, 3, 4 };

    @Mock(answer = Answers.RETURNS_SELF) SignedRequestBuilder signedRequestBuilderMock;
    @Mock SignedRequest signedRequestMock;
    @Mock ResponseConfig responseConfigMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock KeyPair keyPairMock;

    @Spy @InjectMocks DocScanSandboxClient docScanSandboxClient;

    @Test
    public void configureSessionResponse_shouldWrapIoException() throws Exception {
        IOException ioException = new IOException("Some error");

        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(signedRequestMock.execute()).thenThrow(ioException);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        doReturn(signedRequestBuilderMock).when(docScanSandboxClient).getSignedRequestBuilder();

        try {
            docScanSandboxClient.configureSessionResponse("someSessionId", responseConfigMock);
        } catch (SandboxException ex) {
            assertThat(ex.getMessage(), containsString("Some error"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void configureSessionResponse_shouldWrapUriSyntaxException() throws Exception {
//        URISyntaxException uriSyntaxException = new URISyntaxException("input", "Some error");
//
//        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
//        when(signedRequestMock.execute()).thenThrow(uriSyntaxException);
//        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
//        doReturn(signedRequestBuilderMock).when(docScanSandboxClient).getSignedRequestBuilder();
//
//        try {
//            docScanSandboxClient.configureSessionResponse("someSessionId", responseConfigMock);
//        } catch (SandboxException ex) {
//            assertThat(ex.getMessage(), containsString("Some error"));
//            return;
//        }
//        fail("Expected an exception");
    }

}
