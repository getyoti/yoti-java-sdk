package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.RemoteProfileService.YOTI_API_PATH_PREFIX;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.verifyMessage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.Security;
import java.util.Map;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.Base64;

public class RemoteProfileServiceTest {
    private static final String MESSAGE_PREFIX = "GET&";

    public static final Receipt RECEIPT = new Receipt.Builder().withProfile(new byte[] { 1, 2, 3, 4 }).build();
    public static final ProfileResponse PROFILE_RESPONSE = new ProfileResponse.ProfileResponseBuilder()
            .setReceipt(RECEIPT).setSessionData("1234").createProfileResonse();
    public static final String APP_ID = "test-app";
    public static final String TOKEN = "test-token";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReturnReceiptForCorrectRequest()
            throws IOException, GeneralSecurityException, ProfileException, ResourceException {
        ResourceFetcher resourceFetcher = mock(ResourceFetcher.class);
        when(resourceFetcher.fetchResource(Mockito.<UrlConnector> any(), Mockito.<Map<String, String>> any(),
                eq(ProfileResponse.class))).thenReturn(PROFILE_RESPONSE);
        RemoteProfileService profileService = new RemoteProfileService(resourceFetcher);

        KeyPair keyPair = generateKeyPairFrom(KEY_PAIR_PEM);
        Receipt receipt = profileService.getReceipt(keyPair, APP_ID, TOKEN);

        assertSame(RECEIPT, receipt);

        @SuppressWarnings("rawtypes")
        ArgumentCaptor<Map> headersCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<UrlConnector> urlCaptor = ArgumentCaptor.forClass(UrlConnector.class);
        verify(resourceFetcher).fetchResource(urlCaptor.capture(), headersCaptor.capture(), eq(ProfileResponse.class));

        URL url = assertUrl(urlCaptor);
        assertHeaders(keyPair, headersCaptor, url);
    }

    private void assertHeaders(KeyPair keyPair, @SuppressWarnings("rawtypes") ArgumentCaptor<Map> headersCaptor,
            URL url) throws GeneralSecurityException {
        @SuppressWarnings("unchecked")
        Map<String, String> headers = headersCaptor.getValue();
        assertAuthKey(keyPair, headers);
        assertDigest(keyPair, url, headers);
    }

    private void assertDigest(KeyPair keyPair, URL url, Map<String, String> headers) throws GeneralSecurityException {
        String digest = headers.get("X-Yoti-Auth-Digest");
        assertNotNull(digest);
        byte[] digestBytes = Base64.getDecoder().decode(digest);
        verifyMessage((MESSAGE_PREFIX + url.getFile().substring(YOTI_API_PATH_PREFIX.length())).getBytes(),
                keyPair.getPublic(), digestBytes);
    }

    private void assertAuthKey(KeyPair keyPair, Map<String, String> headers) {
        assertEquals(base64(keyPair.getPublic().getEncoded()), headers.get("X-Yoti-Auth-Key"));
    }

    private URL assertUrl(ArgumentCaptor<UrlConnector> urlCaptor) throws MalformedURLException {
        UrlConnector urlConnector = urlCaptor.getValue();
        assertNotNull(urlConnector);
        assertNotNull(urlConnector.getUrlString());
        URL url = new URL(urlConnector.getUrlString());
        assertEquals(YOTI_API_PATH_PREFIX + "/profile/" + TOKEN, url.getPath());
        assertTrue(url.getQuery().contains("appId=" + APP_ID));
        assertTrue(url.getQuery().contains("nonce="));
        assertTrue(url.getQuery().contains("timestamp="));
        return url;
    }

    @Test
    public void shouldReturnNullForNoResource()
            throws IOException, GeneralSecurityException, ProfileException, ResourceException {
        ResourceFetcher resourceFetcher = mock(ResourceFetcher.class);
        when(resourceFetcher.fetchResource(Mockito.<UrlConnector> any(), Mockito.<Map<String, String>> any(),
                eq(ProfileResponse.class))).thenReturn(PROFILE_RESPONSE);
        RemoteProfileService profileService = new RemoteProfileService(resourceFetcher);

        KeyPair keyPair = generateKeyPairFrom(KEY_PAIR_PEM);
        Receipt receipt = profileService.getReceipt(keyPair, APP_ID, TOKEN);

        assertEquals(RECEIPT, receipt);
    }

    @Test(expected = ProfileException.class)
    public void shouldThrowExceptionForIOError()
            throws IOException, GeneralSecurityException, ProfileException, ResourceException {
        ResourceFetcher resourceFetcher = mock(ResourceFetcher.class);
        when(resourceFetcher.fetchResource(Mockito.<UrlConnector> any(), Mockito.<Map<String, String>> any(),
                eq(ProfileResponse.class))).thenThrow(new IOException("Test exception"));
        RemoteProfileService profileService = new RemoteProfileService(resourceFetcher);

        KeyPair keyPair = generateKeyPairFrom(KEY_PAIR_PEM);
        Receipt receipt = profileService.getReceipt(keyPair, APP_ID, TOKEN);

        assertNull(null, receipt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPair() throws IOException, GeneralSecurityException, ProfileException {
        ResourceFetcher resourceFetcher = mock(ResourceFetcher.class);
        RemoteProfileService profileService = new RemoteProfileService(resourceFetcher);

        profileService.getReceipt(null, APP_ID, TOKEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAppId() throws IOException, GeneralSecurityException, ProfileException {
        ResourceFetcher resourceFetcher = mock(ResourceFetcher.class);
        RemoteProfileService profileService = new RemoteProfileService(resourceFetcher);

        profileService.getReceipt(generateKeyPairFrom(KEY_PAIR_PEM), null, TOKEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullConnectToken() throws IOException, GeneralSecurityException, ProfileException {
        ResourceFetcher resourceFetcher = mock(ResourceFetcher.class);
        RemoteProfileService profileService = new RemoteProfileService(resourceFetcher);

        profileService.getReceipt(generateKeyPairFrom(KEY_PAIR_PEM), APP_ID, null);
    }
}
