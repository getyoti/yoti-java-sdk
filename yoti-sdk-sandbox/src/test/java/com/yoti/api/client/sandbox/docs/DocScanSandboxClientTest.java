package com.yoti.api.client.sandbox.docs;

import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_DOCS_URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.security.KeyPair;

import com.yoti.api.client.sandbox.SandboxException;
import com.yoti.api.client.sandbox.docs.request.ResponseConfig;
import com.yoti.api.client.sandbox.util.FieldSetter;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class DocScanSandboxClientTest {

    private static final byte[] SOME_BYTES = new byte[]{ 1, 2, 3, 4 };
    private static final String SOME_API_URL = System.getProperty(PROPERTY_YOTI_DOCS_URL, "https://api.yoti.com/sandbox/idverify/v1");
    private static final String SOME_SDK_ID = "someSdkId";
    private static final String SOME_SESSION_ID = "someSessionId";

    @Mock SignedRequestBuilderFactory signedRequestBuilderFactory;
    @Mock(answer = Answers.RETURNS_SELF) SignedRequestBuilder signedRequestBuilderMock;
    @Mock SignedRequest signedRequestMock;
    @Mock ResponseConfig responseConfigMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock KeyPair keyPairMock;

    @Spy @InjectMocks DocScanSandboxClient docScanSandboxClient;

    @Before
    public void setUp() {
        when(signedRequestBuilderFactory.create()).thenReturn(signedRequestBuilderMock);
    }

    @Test
    public void configureSessionResponse_shouldWrapIoException() throws Exception {
        IOException ioException = new IOException("Some error");

        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(signedRequestMock.execute()).thenThrow(ioException);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);

        try {
            docScanSandboxClient.configureSessionResponse(SOME_SESSION_ID, responseConfigMock);
        } catch (SandboxException ex) {
            assertThat(ex.getMessage(), containsString("Some error"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void configureSessionResponse_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Some error", "There was some error");

        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(signedRequestMock.execute()).thenThrow(resourceException);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);

        try {
            docScanSandboxClient.configureSessionResponse(SOME_SESSION_ID, responseConfigMock);
        } catch (SandboxException ex) {
            assertThat(ex.getMessage(), containsString("Some error"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void configureSessionResponse_shouldCallSignedRequestBuilderWithCorrectValues() throws Exception {
        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);

        docScanSandboxClient.configureSessionResponse(SOME_SESSION_ID, responseConfigMock);

        verify(signedRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(signedRequestBuilderMock).withKeyPair(keyPairMock);
        verify(signedRequestBuilderMock).withEndpoint("/sessions/" + SOME_SESSION_ID + "/response-config");
        verify(signedRequestBuilderMock).withPayload(SOME_BYTES);
    }

    @Test
    public void configureApplicationResponse_shouldCallSignedRequestBuilderWithCorrectValues() throws Exception {
        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);

        FieldSetter.setField(docScanSandboxClient, "sdkId", SOME_SDK_ID);

        docScanSandboxClient.configureApplicationResponse(responseConfigMock);

        verify(signedRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(signedRequestBuilderMock).withKeyPair(keyPairMock);
        verify(signedRequestBuilderMock).withEndpoint("/apps/" + SOME_SDK_ID + "/response-config");
        verify(signedRequestBuilderMock).withPayload(SOME_BYTES);
    }

}
