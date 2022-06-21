package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64Url;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptAsymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.bouncycastle.util.encoders.Base64.decode;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.ErrorDetails;
import com.yoti.api.client.spi.remote.call.ProfileResponse;
import com.yoti.api.client.spi.remote.call.ProfileService;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptFetcherTest {

    private static final String TOKEN = "test-token-test-test-test";
    private static final String APP_ID = "appId";
    private static final String ENCODED_RECEIPT_STRING = "base64EncodedReceipt";
    private static final byte[] DECODED_RECEIPT_BYTES = decode(ENCODED_RECEIPT_STRING);

    @InjectMocks ReceiptFetcher testObj;

    @Mock ProfileService profileService;

    KeyPair keyPair;
    String encryptedToken;

    @Before
    public void setUp() throws Exception {
        keyPair = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM);
        PublicKey publicKey = keyPair.getPublic();
        encryptedToken = base64Url(encryptAsymmetric(TOKEN.getBytes(), publicKey));
    }

    @Test
    public void shouldFailForNullToken() {
        try {
            testObj.fetch(null, keyPair, APP_ID);
        } catch (ProfileException ex) {
            assertThat(ex.getMessage(), containsString("Cannot decrypt connect token"));
            assertTrue(ex.getCause() instanceof NullPointerException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailForNonBase64Token() {
        try {
            testObj.fetch(TOKEN, keyPair, APP_ID);
        } catch (ProfileException ex) {
            assertThat(ex.getMessage(), containsString("Cannot decrypt connect token"));
            assertTrue(ex.getCause() instanceof IllegalArgumentException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailForBadlyEncryptedToken() {
        try {
            testObj.fetch(Base64.getEncoder().encodeToString(TOKEN.getBytes()), keyPair, APP_ID);
        } catch (ProfileException ex) {
            assertThat(ex.getMessage(), containsString("Cannot decrypt connect token"));
            assertTrue(ex.getCause() instanceof ProfileException);
            assertThat(ex.getCause().getMessage(), containsString("Error decrypting data"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWithExceptionFromProfileService() throws Exception {
        ProfileException profileException = new ProfileException("Test exception");
        when(profileService.getProfile(any(KeyPair.class), anyString(), anyString())).thenThrow(profileException);

        try {
            testObj.fetch(encryptedToken, keyPair, APP_ID);
        } catch (ProfileException ex) {
            assertSame(profileException, ex);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWhenNoProfileReturned() throws Exception {
        when(profileService.getProfile(any(KeyPair.class), anyString(), anyString())).thenReturn(null);

        try {
            testObj.fetch(encryptedToken, keyPair, APP_ID);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("No profile"));
            assertThat(e.getMessage(), containsString(TOKEN));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWhenNoReceiptReturned() throws Exception {
        when(profileService.getProfile(any(KeyPair.class), anyString(), anyString()))
                .thenReturn(new ProfileResponse.ProfileResponseBuilder().build());

        try {
            testObj.fetch(encryptedToken, keyPair, APP_ID);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("No profile"));
            assertThat(e.getMessage(), containsString(TOKEN));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailForFailureReceiptWithErrorCode() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withReceiptId(DECODED_RECEIPT_BYTES)
                .withOutcome(Receipt.Outcome.FAILURE)
                .build();

        String errorCode = "anErrorCode";
        String errorDescription = "anErrorDescription";
        ErrorDetails error = ErrorDetails.builder().code(errorCode).description(errorDescription).build();

        ProfileResponse profileResponse = new ProfileResponse.ProfileResponseBuilder()
                .setReceipt(receipt)
                .setError(error)
                .build();
        when(profileService.getProfile(keyPair, APP_ID, TOKEN)).thenReturn(profileResponse);

        try {
            testObj.fetch(encryptedToken, keyPair, APP_ID);
        } catch (ActivityFailureException ex) {
            assertThat(ex.getMessage(), containsString(ENCODED_RECEIPT_STRING));
            assertThat(ex.getMessage(), containsString(errorCode));
            assertThat(ex.getMessage(), containsString(errorDescription));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailForFailureReceiptWithNoErrorCode() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withReceiptId(DECODED_RECEIPT_BYTES)
                .withOutcome(Receipt.Outcome.FAILURE)
                .build();

        ProfileResponse profileResponse = new ProfileResponse.ProfileResponseBuilder().setReceipt(receipt).build();
        when(profileService.getProfile(keyPair, APP_ID, TOKEN)).thenReturn(profileResponse);

        try {
            testObj.fetch(encryptedToken, keyPair, APP_ID);
        } catch (ActivityFailureException ex) {
            assertThat(ex.getMessage(), containsString(ENCODED_RECEIPT_STRING));
            assertThat(ex.getMessage(), not(containsString("error")));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldReturnSuccessReceipt() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();

        ProfileResponse profileResponse = new ProfileResponse.ProfileResponseBuilder().setReceipt(receipt).build();
        when(profileService.getProfile(keyPair, APP_ID, TOKEN)).thenReturn(profileResponse);

        Receipt result = testObj.fetch(encryptedToken, keyPair, APP_ID);

        assertSame(result, receipt);
    }

}
