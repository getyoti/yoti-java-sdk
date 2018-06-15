package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64Url;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptAsymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.bouncycastle.util.encoders.Base64.decode;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.security.KeyPair;
import java.security.PublicKey;

import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.ProfileService;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptFetcherTest {

    private static final String TOKEN = "test-token-test-test-test";
    private static final String APP_ID = "appId";
    private static final String ENCODED_RECEIPT_STRING = "base64EncodedReceipt";
    private static final byte[] DECODED_RECEIPT_BYTES = decode(ENCODED_RECEIPT_STRING);

    @InjectMocks ReceiptFetcher testObj;

    @Mock ProfileService profileServiceMock;

    KeyPair keyPair;
    String encryptedToken;

    @Before
    public void setUp() throws Exception {
        keyPair = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM);
        PublicKey publicKey = keyPair.getPublic();
        encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));
    }

    @Test
    public void shouldFailForNullToken() throws Exception {
        try {
            testObj.fetch(null, keyPair, APP_ID);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Cannot decrypt connect token"));
            assertTrue(e.getCause() instanceof NullPointerException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailForNonBase64Token() throws Exception {
        try {
            testObj.fetch(TOKEN, keyPair, APP_ID);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Cannot decrypt connect token"));
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailForBadlyEncryptedToken() throws Exception {
        try {
            testObj.fetch(Base64.getEncoder().encodeToString(TOKEN.getBytes()), keyPair, APP_ID);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Cannot decrypt connect token"));
            assertTrue(e.getCause() instanceof ProfileException);
            assertThat(e.getCause().getMessage(), containsString("Error decrypting data"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWithExceptionFromProfileService() throws Exception {
        ProfileException profileException = new ProfileException("Test exception");
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenThrow(profileException);

        try {
            testObj.fetch(encryptedToken, keyPair, APP_ID);
        } catch (ProfileException e) {
            assertSame(profileException, e);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWhenNoReceiptReturned() throws Exception {
        when(profileServiceMock.getReceipt(any(KeyPair.class), anyString(), anyString())).thenReturn(null);

        try {
            testObj.fetch(encryptedToken, keyPair, APP_ID);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("No receipt"));
            assertThat(e.getMessage(), containsString(TOKEN));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailForFailureReceipt() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withReceiptId(DECODED_RECEIPT_BYTES)
                .withOutcome(Receipt.Outcome.FAILURE)
                .build();
        when(profileServiceMock.getReceipt(keyPair, APP_ID, TOKEN)).thenReturn(receipt);

        try {
            testObj.fetch(encryptedToken, keyPair, APP_ID);
        } catch (ActivityFailureException e) {
            assertThat(e.getMessage(), containsString(ENCODED_RECEIPT_STRING));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldReturnSuccessReceipt() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();
        when(profileServiceMock.getReceipt(keyPair, APP_ID, TOKEN)).thenReturn(receipt);

        Receipt result = testObj.fetch(encryptedToken, keyPair, APP_ID);

        assertSame(result, receipt);
    }

}
