package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RemoteProfileServiceTest {

    private static final Receipt RECEIPT = new Receipt.Builder()
            .withProfile(new byte[] { 1, 2, 3, 4 })
            .build();
    private static final ProfileResponse PROFILE_RESPONSE = new ProfileResponse.ProfileResponseBuilder()
            .setReceipt(RECEIPT)
            .setSessionData("1234")
            .createProfileResonse();
    private static final String APP_ID = "test-app";
    private static final String TOKEN = "test-token";
    private static final String GENERATED_PROFILE_PATH = "generatedProfilePath";
    private static final Map<String, String> SOME_HEADERS = new HashMap<>();
    private static KeyPair KEY_PAIR;

    @InjectMocks RemoteProfileService testObj;

    @Mock UnsignedPathFactory unsignedPathFactory;
    @Mock(answer = Answers.RETURNS_SELF) SignedRequestBuilder signedRequestBuilderMock;

    @Mock SignedRequest signedRequestMock;

    @BeforeClass
    public static void setUpClass() throws Exception {
        KEY_PAIR = generateKeyPairFrom(KEY_PAIR_PEM);
        SOME_HEADERS.put("someKey", "someValue");
    }

    @Before
    public void setUp() {
        when(unsignedPathFactory.createProfilePath(APP_ID, TOKEN)).thenReturn(GENERATED_PROFILE_PATH);
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(ProfileResponse.class)).thenReturn(PROFILE_RESPONSE);

        Receipt result = testObj.getReceipt(KEY_PAIR, APP_ID, TOKEN);

        verify(signedRequestBuilderMock).withKeyPair(KEY_PAIR);
        verify(signedRequestBuilderMock).withBaseUrl(DEFAULT_YOTI_API_URL);
        verify(signedRequestBuilderMock).withEndpoint(GENERATED_PROFILE_PATH);
        verify(signedRequestBuilderMock).withHttpMethod(HTTP_GET);
        assertSame(RECEIPT, result);
    }

    @Test
    public void shouldWrapSecurityExceptionInProfileException() throws Exception {
        GeneralSecurityException securityException = new GeneralSecurityException();
        when(signedRequestBuilderMock.build()).thenThrow(securityException);

        try {
            testObj.getReceipt(KEY_PAIR, APP_ID, TOKEN);
            fail("Expected a ProfileException");
        } catch (ProfileException e) {
            assertSame(securityException, e.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionForIOError() throws Exception {
        IOException ioException = new IOException("Test exception");
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(ProfileResponse.class)).thenThrow(ioException);

        try {
            testObj.getReceipt(KEY_PAIR, APP_ID, TOKEN);
            fail("Expected a ProfileException");
        } catch (ProfileException e) {
            assertSame(ioException, e.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionWithResourceExceptionCause() throws Throwable {
        ResourceException resourceException = new ResourceException(404, "Not Found", "Test exception");
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(ProfileResponse.class)).thenThrow(resourceException);

        try {
            testObj.getReceipt(KEY_PAIR, APP_ID, TOKEN);
            fail("Expected a ProfileException");
        } catch (ProfileException e) {
            assertSame(resourceException, e.getCause());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPair() throws Exception {
        testObj.getReceipt(null, APP_ID, TOKEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAppId() throws Exception {
        testObj.getReceipt(KEY_PAIR, null, TOKEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullConnectToken() throws Exception {
        testObj.getReceipt(KEY_PAIR, APP_ID, null);
    }

}
