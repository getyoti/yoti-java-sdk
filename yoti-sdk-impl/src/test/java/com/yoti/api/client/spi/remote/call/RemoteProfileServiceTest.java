package com.yoti.api.client.spi.remote.call;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.Base64;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.Security;
import java.util.Map;

import static com.yoti.api.client.spi.remote.call.RemoteProfileService.YOTI_API_PATH_PREFIX;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.verifyMessage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteProfileServiceTest {

    private static final String MESSAGE_PREFIX = "GET&";
    private static final String X_YOTI_AUTH_KEY = "X-Yoti-Auth-Key";
    private static final String X_YOTI_AUTH_DIGEST = "X-Yoti-Auth-Digest";
    private static final String YOTI_SDK_HEADER = "X-Yoti-SDK";
    private static final Receipt RECEIPT = new Receipt.Builder()
            .withProfile(new byte[] { 1, 2, 3, 4 })
            .build();
    private static final ProfileResponse PROFILE_RESPONSE = new ProfileResponse.ProfileResponseBuilder()
            .setReceipt(RECEIPT)
            .setSessionData("1234")
            .createProfileResonse();
    private static final String APP_ID = "test-app";
    private static final String TOKEN = "test-token";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @InjectMocks RemoteProfileService testObj;

    @Mock ResourceFetcher resourceFetcherMock;

    @Captor ArgumentCaptor<Map<String, String>> headersCaptor;
    @Captor ArgumentCaptor<UrlConnector> urlConnectorCaptor;
    KeyPair keyPair;

    @Before
    public void setUp() throws Exception {
        keyPair = generateKeyPairFrom(KEY_PAIR_PEM);
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        when(resourceFetcherMock.fetchResource(any(UrlConnector.class), any(Map.class), eq(ProfileResponse.class))).thenReturn(PROFILE_RESPONSE);

        Receipt result = testObj.getReceipt(keyPair, APP_ID, TOKEN);

        verify(resourceFetcherMock).fetchResource(urlConnectorCaptor.capture(), headersCaptor.capture(), eq(ProfileResponse.class));
        assertUrl(urlConnectorCaptor.getValue());
        assertHeaders(headersCaptor.getValue(), urlConnectorCaptor.getValue());
        assertSame(RECEIPT, result);
    }

    private void assertHeaders(Map<String, String> headers, UrlConnector urlConnector) throws Exception {
        assertAuthKey(headers.get(X_YOTI_AUTH_KEY));
        assertDigest(urlConnector.getUrlString(), headers.get(X_YOTI_AUTH_DIGEST));
        assertYotiSDK(headers.get(YOTI_SDK_HEADER));
    }

    private void assertDigest(String url, String digestValue) throws Exception {
        byte[] digestBytes = Base64.getDecoder().decode(digestValue);
        String servicePath = MESSAGE_PREFIX + url.substring(RemoteProfileService.DEFAULT_YOTI_API_URL.length());
        verifyMessage(servicePath.getBytes(), keyPair.getPublic(), digestBytes);
    }

    private void assertAuthKey(String authKeyValue) {
        assertEquals(base64(keyPair.getPublic().getEncoded()), authKeyValue);
    }

    private void assertYotiSDK(String sdkValue) {
        assertEquals("Java", sdkValue);
    }

    private void assertUrl(UrlConnector urlConnector) throws MalformedURLException {
        URL url = new URL(urlConnector.getUrlString());
        assertEquals(YOTI_API_PATH_PREFIX + "/profile/" + TOKEN, url.getPath());
        assertTrue(url.getQuery().contains("appId=" + APP_ID));
        assertTrue(url.getQuery().contains("nonce="));
        assertTrue(url.getQuery().contains("timestamp="));
    }

    @Test
    @Ignore("This test seems to add no value, and it's not clear what the desired behaviour is")
    public void shouldReturnNullForNoResource() throws IOException, GeneralSecurityException, ProfileException, ResourceException {
        when(resourceFetcherMock.fetchResource(any(UrlConnector.class), any(Map.class), eq(ProfileResponse.class))).thenReturn(PROFILE_RESPONSE);

        Receipt result = testObj.getReceipt(keyPair, APP_ID, TOKEN);

        assertEquals(RECEIPT, result);
    }

    @Test
    public void shouldThrowExceptionForIOError() throws IOException, GeneralSecurityException, ProfileException, ResourceException {
        IOException ioException = new IOException("Test exception");
        when(resourceFetcherMock.fetchResource(any(UrlConnector.class), any(Map.class), eq(ProfileResponse.class))).thenThrow(ioException);

        try {
            testObj.getReceipt(keyPair, APP_ID, TOKEN);
            fail("Expected a ProfileException");
        } catch (ProfileException e) {
            assertSame(ioException, e.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionWithResourceExceptionCause() throws Throwable {
        ResourceException resourceException = new ResourceException(404, "Test exception");
        when(resourceFetcherMock.fetchResource(any(UrlConnector.class), any(Map.class), eq(ProfileResponse.class))).thenThrow(resourceException);

        try {
            testObj.getReceipt(keyPair, APP_ID, TOKEN);
            fail("Expected a ProfileException");
        } catch (ProfileException e) {
            assertSame(resourceException, e.getCause());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPair() throws IOException, GeneralSecurityException, ProfileException {
        testObj.getReceipt(null, APP_ID, TOKEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAppId() throws IOException, GeneralSecurityException, ProfileException {
        testObj.getReceipt(keyPair, null, TOKEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullConnectToken() throws IOException, GeneralSecurityException, ProfileException {
        testObj.getReceipt(keyPair, APP_ID, null);
    }

}
