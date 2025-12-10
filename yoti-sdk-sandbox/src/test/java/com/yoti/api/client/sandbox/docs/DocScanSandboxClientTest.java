package com.yoti.api.client.sandbox.docs;

import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_DOCS_URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.KeyPair;

import com.yoti.api.client.sandbox.SandboxException;
import com.yoti.api.client.sandbox.docs.request.ResponseConfig;
import com.yoti.api.client.sandbox.util.FieldSetter;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.YotiHttpRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilder;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.factory.DocsSignedRequestStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocScanSandboxClientTest {

    private static final byte[] SOME_BYTES = new byte[]{ 1, 2, 3, 4 };
    private static final String SOME_API_URL = System.getProperty(PROPERTY_YOTI_DOCS_URL, "https://api.yoti.com/sandbox/idverify/v1");
    private static final String SOME_SDK_ID = "someSdkId";
    private static final String SOME_SESSION_ID = "someSessionId";

    @InjectMocks DocScanSandboxClient testObj;

    @Mock YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory;
    @Mock ObjectMapper objectMapperMock;
    @Mock DocsSignedRequestStrategy authStrategyMock;

    @Mock(answer = Answers.RETURNS_SELF) YotiHttpRequestBuilder yotiHttpRequestBuilderMock;
    @Mock YotiHttpRequest yotiHttpRequestMock;
    @Mock ResponseConfig responseConfigMock;
    @Mock KeyPair keyPairMock;

    @Before
    public void setUp() {
        when(yotiHttpRequestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilderMock);
    }

    @Test
    public void configureSessionResponse_shouldWrapIoException() throws Exception {
        IOException ioException = new IOException("Some error");
        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        SandboxException thrown = assertThrows(SandboxException.class, () -> testObj.configureSessionResponse(SOME_SESSION_ID, responseConfigMock));

        assertThat(thrown.getMessage(), containsString("Some error"));
    }

    @Test
    public void configureSessionResponse_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Some error", "There was some error");
        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        SandboxException thrown = assertThrows(SandboxException.class, () -> testObj.configureSessionResponse(SOME_SESSION_ID, responseConfigMock));

        assertThat(thrown.getMessage(), containsString("Some error"));
    }

    @Test
    public void configureSessionResponse_shouldCallSignedRequestBuilderWithCorrectValues() throws Exception {
        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);

        testObj.configureSessionResponse(SOME_SESSION_ID, responseConfigMock);

        verify(yotiHttpRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(yotiHttpRequestBuilderMock).withAuthStrategy(authStrategyMock);
        verify(yotiHttpRequestBuilderMock).withEndpoint("/sessions/" + SOME_SESSION_ID + "/response-config");
        verify(yotiHttpRequestBuilderMock).withPayload(SOME_BYTES);
    }

    @Test
    public void configureApplicationResponse_shouldCallSignedRequestBuilderWithCorrectValues() throws Exception {
        when(objectMapperMock.writeValueAsBytes(responseConfigMock)).thenReturn(SOME_BYTES);
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);

        FieldSetter.setField(testObj, "sdkId", SOME_SDK_ID);

        testObj.configureApplicationResponse(responseConfigMock);

        verify(yotiHttpRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(yotiHttpRequestBuilderMock).withAuthStrategy(authStrategyMock);
        verify(yotiHttpRequestBuilderMock).withEndpoint("/apps/" + SOME_SDK_ID + "/response-config");
        verify(yotiHttpRequestBuilderMock).withPayload(SOME_BYTES);
    }

}
