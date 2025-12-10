package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.base64Url;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptAsymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.bouncycastle.util.encoders.Base64.decode;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.ErrorDetails;
import com.yoti.api.client.spi.remote.call.ProfileResponse;
import com.yoti.api.client.spi.remote.call.ProfileService;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final ObjectMapper MAPPER = new ObjectMapper();

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
            testObj.fetch(null, keyPair);
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
            testObj.fetch(TOKEN, keyPair);
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
            testObj.fetch(Base64.getEncoder().encodeToString(TOKEN.getBytes()), keyPair);
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
        when(profileService.getProfile(any(KeyPair.class), anyString())).thenThrow(profileException);

        try {
            testObj.fetch(encryptedToken, keyPair);
        } catch (ProfileException ex) {
            assertSame(profileException, ex);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWhenNoProfileReturned() throws Exception {
        when(profileService.getProfile(any(KeyPair.class), anyString())).thenReturn(null);

        try {
            testObj.fetch(encryptedToken, keyPair);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("No profile"));
            assertThat(e.getMessage(), containsString(TOKEN));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWhenNoReceiptReturned() throws Exception {
        when(profileService.getProfile(any(KeyPair.class), anyString()))
                .thenReturn(new ProfileResponse.ProfileResponseBuilder().build());

        try {
            testObj.fetch(encryptedToken, keyPair);
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
        when(profileService.getProfile(keyPair, TOKEN)).thenReturn(profileResponse);

        try {
            testObj.fetch(encryptedToken, keyPair);
        } catch (ActivityFailureException ex) {
            String exMsg = ex.getMessage();
            assertThat(exMsg, containsString(ENCODED_RECEIPT_STRING));
            assertThat(exMsg, containsString(errorCode));
            assertThat(exMsg, containsString(errorDescription));

            ErrorDetails exError = ex.errorDetails();
            assertThat(exError.getCode(), is(equalTo(errorCode)));
            assertThat(exError.getDescription(), is(equalTo(errorDescription)));

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
        when(profileService.getProfile(keyPair, TOKEN)).thenReturn(profileResponse);

        try {
            testObj.fetch(encryptedToken, keyPair);
        } catch (ActivityFailureException ex) {
            assertThat(ex.getMessage(), containsString(ENCODED_RECEIPT_STRING));
            assertThat(ex.getMessage(), not(containsString("error")));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailForFailureReceiptWithErrorReason() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withReceiptId(DECODED_RECEIPT_BYTES)
                .withOutcome(Receipt.Outcome.FAILURE)
                .build();

        String errorCode = "anErrorCode";

        Map<String, Object> errorReason = new HashMap<>();
        errorReason.put("aReasonKey", "aReasonValue");

        ErrorDetails error = ErrorDetails.builder()
                .code(errorCode)
                .reason(errorReason)
                .build();

        ProfileResponse profileResponse = new ProfileResponse.ProfileResponseBuilder()
                .setReceipt(receipt)
                .setError(error)
                .build();
        when(profileService.getProfile(keyPair, TOKEN)).thenReturn(profileResponse);

        try {
            testObj.fetch(encryptedToken, keyPair);
        } catch (ActivityFailureException ex) {
            String exMsg = ex.getMessage();
            assertThat(exMsg, containsString(ENCODED_RECEIPT_STRING));
            assertThat(exMsg, containsString(errorCode));
            assertThat(exMsg, containsString("aReasonValue"));

            ErrorDetails exError = ex.errorDetails();
            assertThat(exError.getCode(), is(equalTo(errorCode)));
            assertThat(exError.getReason(), is(equalTo(MAPPER.writeValueAsString(errorReason))));

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
        when(profileService.getProfile(keyPair, TOKEN)).thenReturn(profileResponse);

        Receipt result = testObj.fetch(encryptedToken, keyPair);

        assertSame(result, receipt);
    }

}
