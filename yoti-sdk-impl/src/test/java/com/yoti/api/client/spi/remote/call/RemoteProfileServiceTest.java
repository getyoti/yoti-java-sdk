package com.yoti.api.client.spi.remote.call;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;
import org.junit.Before;
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

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.AUTH_KEY_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_API_PATH_PREFIX;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
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

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @InjectMocks RemoteProfileService testObj;

    @Mock ResourceFetcher resourceFetcherMock;
    @Mock PathFactory pathFactoryMock;
    @Mock SignedMessageFactory signedMessageFactoryMock;

    @Captor ArgumentCaptor<Map<String, String>> headersCaptor;
    @Captor ArgumentCaptor<UrlConnector> urlConnectorCaptor;
    KeyPair keyPair;

    @Before
    public void setUp() throws Exception {
        keyPair = generateKeyPairFrom(KEY_PAIR_PEM);

        when(pathFactoryMock.createProfilePath(APP_ID, TOKEN)).thenReturn(GENERATED_PROFILE_PATH);
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        when(signedMessageFactoryMock.create(keyPair.getPrivate(), HTTP_GET, GENERATED_PROFILE_PATH)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.fetchResource(any(UrlConnector.class), any(Map.class), eq(ProfileResponse.class))).thenReturn(PROFILE_RESPONSE);

        Receipt result = testObj.getReceipt(keyPair, APP_ID, TOKEN);

        verify(resourceFetcherMock).fetchResource(urlConnectorCaptor.capture(), headersCaptor.capture(), eq(ProfileResponse.class));
        assertUrl(urlConnectorCaptor.getValue());
        assertHeaders(headersCaptor.getValue());
        assertSame(RECEIPT, result);
    }

    private void assertHeaders(Map<String, String> headers) throws Exception {
        assertAuthKey(headers.get(AUTH_KEY_HEADER));
        assertDigest(headers.get(DIGEST_HEADER));
        assertYotiSDK(headers.get(YOTI_SDK_HEADER));
    }

    private void assertDigest(String digestValue) throws Exception {
        assertEquals(SOME_SIGNATURE, digestValue);
    }

    private void assertAuthKey(String authKeyValue) {
        assertEquals(base64(keyPair.getPublic().getEncoded()), authKeyValue);
    }

    private void assertYotiSDK(String sdkValue) {
        assertEquals(JAVA, sdkValue);
    }

    private void assertUrl(UrlConnector urlConnector) throws MalformedURLException {
        URL url = new URL(urlConnector.getUrlString());
        assertEquals(YOTI_API_PATH_PREFIX + GENERATED_PROFILE_PATH, url.getPath());
    }

    @Test
    public void shouldWrapSecurityExceptionInProfileException() throws Exception {
        GeneralSecurityException securityException = new GeneralSecurityException();
        when(signedMessageFactoryMock.create(keyPair.getPrivate(), HTTP_GET, GENERATED_PROFILE_PATH)).thenThrow(securityException);

        try {
            testObj.getReceipt(keyPair, APP_ID, TOKEN);
            fail("Expected a ProfileException");
        } catch (ProfileException e) {
            assertSame(securityException, e.getCause());
        }
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
