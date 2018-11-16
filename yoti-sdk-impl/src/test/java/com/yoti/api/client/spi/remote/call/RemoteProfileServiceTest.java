package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_API_PATH_PREFIX;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    private static final String SOME_SIGNATURE = "someSignature";
    private static final Map<String, String> SOME_HEADERS = new HashMap<>();
    private static KeyPair KEY_PAIR;

    @InjectMocks RemoteProfileService testObj;

    @Mock ResourceFetcher resourceFetcherMock;
    @Mock PathFactory pathFactoryMock;
    @Mock HeadersFactory headersFactoryMock;
    @Mock SignedMessageFactory signedMessageFactoryMock;

    @Captor ArgumentCaptor<UrlConnector> urlConnectorCaptor;

    @BeforeClass
    public static void setUpClass() throws Exception {
        KEY_PAIR = generateKeyPairFrom(KEY_PAIR_PEM);
        SOME_HEADERS.put("someKey", "someValue");
    }

    @Before
    public void setUp() {
        when(pathFactoryMock.createProfilePath(APP_ID, TOKEN)).thenReturn(GENERATED_PROFILE_PATH);
        when(headersFactoryMock.create(SOME_SIGNATURE, base64(KEY_PAIR.getPublic().getEncoded()))).thenReturn(SOME_HEADERS);
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        when(signedMessageFactoryMock.create(KEY_PAIR.getPrivate(), HTTP_GET, GENERATED_PROFILE_PATH)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.fetchResource(any(UrlConnector.class), eq(SOME_HEADERS), eq(ProfileResponse.class))).thenReturn(PROFILE_RESPONSE);

        Receipt result = testObj.getReceipt(KEY_PAIR, APP_ID, TOKEN);

        verify(resourceFetcherMock).fetchResource(urlConnectorCaptor.capture(), eq(SOME_HEADERS), eq(ProfileResponse.class));
        assertUrl(urlConnectorCaptor.getValue());
        assertSame(RECEIPT, result);
    }

    private void assertUrl(UrlConnector urlConnector) throws MalformedURLException {
        URL url = new URL(urlConnector.getUrlString());
        assertEquals(YOTI_API_PATH_PREFIX + GENERATED_PROFILE_PATH, url.getPath());
    }

    @Test
    public void shouldWrapSecurityExceptionInProfileException() throws Exception {
        GeneralSecurityException securityException = new GeneralSecurityException();
        when(signedMessageFactoryMock.create(KEY_PAIR.getPrivate(), HTTP_GET, GENERATED_PROFILE_PATH)).thenThrow(securityException);

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
        when(signedMessageFactoryMock.create(KEY_PAIR.getPrivate(), HTTP_GET, GENERATED_PROFILE_PATH)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.fetchResource(any(UrlConnector.class), eq(SOME_HEADERS), eq(ProfileResponse.class))).thenThrow(ioException);

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
        when(signedMessageFactoryMock.create(KEY_PAIR.getPrivate(), HTTP_GET, GENERATED_PROFILE_PATH)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.fetchResource(any(UrlConnector.class), eq(SOME_HEADERS), eq(ProfileResponse.class))).thenThrow(resourceException);

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
