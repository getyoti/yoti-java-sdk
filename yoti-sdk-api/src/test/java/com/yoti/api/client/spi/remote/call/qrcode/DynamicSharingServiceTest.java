package com.yoti.api.client.spi.remote.call.qrcode;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.shareurl.DynamicScenario;
import com.yoti.api.client.shareurl.DynamicShareException;
import com.yoti.api.client.shareurl.ShareUrlResult;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.YotiHttpRequest;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DynamicSharingServiceTest {

    private static final String APP_ID = "appId";
    private static final String DYNAMIC_QRCODE_PATH = "dynamicQRCodePath";
    private static final String SOME_BODY = "someBody";
    private static final byte[] SOME_BODY_BYTES = SOME_BODY.getBytes();
    private static final Map<String, String> SOME_HEADERS = new HashMap<>();

    @Spy @InjectMocks DynamicSharingService testObj;

    @Mock UnsignedPathFactory unsignedPathFactoryMock;
    @Mock ObjectMapper objectMapperMock;

    @Mock DynamicScenario simpleDynamicScenarioMock;
    @Mock YotiHttpRequest yotiHttpRequestMock;
    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPairMock;
    @Mock ShareUrlResult shareUrlResultMock;

    @BeforeClass
    public static void setUpClass() {
        SOME_HEADERS.put("someKey", "someValue");
    }

    @Before
    public void setUp() {
        when(unsignedPathFactoryMock.createDynamicSharingPath(APP_ID)).thenReturn(DYNAMIC_QRCODE_PATH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAppId() throws Exception {
        testObj.createShareUrl(null, simpleDynamicScenarioMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullDynamicScenario() throws Exception {
        testObj.createShareUrl(APP_ID, null);
    }

    @Test
    public void shouldThrowDynamicShareExceptionWhenParsingFails() throws Exception {
        JsonProcessingException jsonProcessingException = new JsonProcessingException("") {};
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenThrow(jsonProcessingException);

        try {
            testObj.createShareUrl(APP_ID, simpleDynamicScenarioMock);
            fail("Expected a DynamicShareException");
        } catch (DynamicShareException ex) {
            assertSame(jsonProcessingException, ex.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionForIOError() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        IOException ioException = new IOException();
        doReturn(yotiHttpRequestMock).when(testObj).createSignedRequest(DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES);
        when(yotiHttpRequestMock.execute(ShareUrlResult.class)).thenThrow(ioException);

        try {
            testObj.createShareUrl(APP_ID, simpleDynamicScenarioMock);
            fail("Expected a DynamicShareException");
        } catch (DynamicShareException ex) {
            assertSame(ioException, ex.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionWithResourceExceptionCause() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        ResourceException resourceException = new ResourceException(404, "Not Found", "Test exception");
        doReturn(yotiHttpRequestMock).when(testObj).createSignedRequest(DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES);
        when(yotiHttpRequestMock.execute(ShareUrlResult.class)).thenThrow(resourceException);

        try {
            testObj.createShareUrl(APP_ID, simpleDynamicScenarioMock);
            fail("Expected a DynamicShareException");
        } catch (DynamicShareException ex) {
            assertSame(resourceException, ex.getCause());
        }
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        doReturn(yotiHttpRequestMock).when(testObj).createSignedRequest(DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES);
        when(yotiHttpRequestMock.execute(ShareUrlResult.class)).thenReturn(shareUrlResultMock);

        ShareUrlResult result = testObj.createShareUrl(APP_ID, simpleDynamicScenarioMock);
        assertSame(shareUrlResultMock, result);
    }

}
