package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.RFC3339_PATTERN;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptAsymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateSymmetricKey;

import static org.bouncycastle.util.encoders.Base64.decode;
import static org.bouncycastle.util.encoders.Base64.toBase64String;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.yoti.api.client.*;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivityDetailsFactoryTest {

    private static final byte[] PROFILE_CONTENT = { 0, 1, 2, 3 };
    private static final byte[] OTHER_PROFILE_CONTENT = { 4, 5, 6, 7 };
    private static final byte[] EXTRA_DATA_CONTENT = { 8, 9, 10, 11 };

    private static final Date DATE = new GregorianCalendar(1980, Calendar.AUGUST, 5).getTime();
    private static final DateFormat RFC3339_FORMAT = new SimpleDateFormat(RFC3339_PATTERN);
    private static final String VALID_TIMESTAMP = RFC3339_FORMAT.format(DATE);

    private static final String SOME_REMEMBER_ME_ID_STRING = toBase64String("aBase64EncodedRememberMeId".getBytes());
    private static final byte[] SOME_REMEMBER_ME_ID_BYTES = decode(SOME_REMEMBER_ME_ID_STRING);
    private static final String SOME_PARENT_REMEMBER_ME_ID_STRING = toBase64String("aB64ParentRememberMeId".getBytes());
    private static final byte[] SOME_PARENT_REMEMBER_ME_ID_BYTES = decode(SOME_PARENT_REMEMBER_ME_ID_STRING);
    private static final String ENCODED_RECEIPT_STRING = "base64EncodedReceipt";
    private static final byte[] DECODED_RECEIPT_BYTES = decode(ENCODED_RECEIPT_STRING);

    @InjectMocks ActivityDetailsFactory testObj;

    @Mock ProfileReader profileReaderMock;

    @Mock
    ExtraDataConverter extraDataConverterMock;

    KeyPair keyPair;
    byte[] validReceiptKey;
    @Mock Profile profileMock;
    @Mock Profile otherProfileMock;
    @Mock ExtraData extraDataMock;

    @Before
    public void setUp() throws Exception {
        Key receiptKey = generateSymmetricKey();
        keyPair = generateKeyPairFrom(CryptoUtil.KEY_PAIR_PEM);
        validReceiptKey = encryptAsymmetric(receiptKey.getEncoded(), keyPair.getPublic());
    }

    @Test
    public void shouldFailWithNoReceiptKey() {
        Receipt receipt = new Receipt.Builder()
                .withWrappedReceiptKey(null)
                .build();

        try {
            testObj.create(receipt, keyPair.getPrivate());
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Base64 encoding error"));
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWithInvalidReceiptKey() {
        byte[] invalidReceiptKey = { 1, 2, 3 };
        Receipt receipt = new Receipt.Builder()
                .withWrappedReceiptKey(invalidReceiptKey)
                .build();

        try {
            testObj.create(receipt, keyPair.getPrivate());
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Error decrypting data"));
            assertTrue(e.getCause() instanceof GeneralSecurityException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWithInvalidTimestamp() {
        Receipt receipt = new Receipt.Builder()
                .withWrappedReceiptKey(validReceiptKey)
                .withTimestamp("someInvalidValue")
                .build();

        try {
            testObj.create(receipt, keyPair.getPrivate());
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("timestamp"));
            assertTrue(e.getCause() instanceof ParseException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldGetCorrectProfilesFromProfileReaderWithoutAnyRememberMeIds() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withWrappedReceiptKey(validReceiptKey)
                .withTimestamp(VALID_TIMESTAMP)
                .withRememberMeId(new byte[0])
                .withProfile(PROFILE_CONTENT)
                .withOtherPartyProfile(OTHER_PROFILE_CONTENT)
                .withReceiptId(DECODED_RECEIPT_BYTES)
                .withExtraData(EXTRA_DATA_CONTENT)
                .build();
        when(profileReaderMock.read(eq(PROFILE_CONTENT), any(Key.class))).thenReturn(profileMock);
        when(profileReaderMock.read(eq(OTHER_PROFILE_CONTENT), any(Key.class))).thenReturn(otherProfileMock);
        when(extraDataConverterMock.read(eq(EXTRA_DATA_CONTENT))).thenReturn(extraDataMock);


        ActivityDetails result = testObj.create(receipt, keyPair.getPrivate());

        assertSame(otherProfileMock, getWrappedProfile(result.getUserProfile()));
        assertSame(profileMock, getWrappedProfile(result.getApplicationProfile()));
        assertSame(extraDataMock, result.getExtraData());
        assertEquals("", result.getRememberMeId());
        assertEquals("", result.getUserId());
        assertEquals(null, result.getParentRememberMeId());
        assertEquals(ENCODED_RECEIPT_STRING, result.getReceiptId());
        assertEquals(DATE, result.getTimestamp());
    }

    @Test
    public void shouldGetCorrectProfilesFromProfileReader() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withWrappedReceiptKey(validReceiptKey)
                .withTimestamp(VALID_TIMESTAMP)
                .withRememberMeId(SOME_REMEMBER_ME_ID_BYTES)
                .withParentRememberMeId(SOME_PARENT_REMEMBER_ME_ID_BYTES)
                .withProfile(PROFILE_CONTENT)
                .withOtherPartyProfile(OTHER_PROFILE_CONTENT)
                .withReceiptId(DECODED_RECEIPT_BYTES)
                .withExtraData(EXTRA_DATA_CONTENT)
                .build();
        when(profileReaderMock.read(eq(PROFILE_CONTENT), any(Key.class))).thenReturn(profileMock);
        when(profileReaderMock.read(eq(OTHER_PROFILE_CONTENT), any(Key.class))).thenReturn(otherProfileMock);
        when(extraDataConverterMock.read(eq(EXTRA_DATA_CONTENT))).thenReturn(extraDataMock);

        ActivityDetails result = testObj.create(receipt, keyPair.getPrivate());

        assertSame(otherProfileMock, getWrappedProfile(result.getUserProfile()));
        assertSame(profileMock, getWrappedProfile(result.getApplicationProfile()));
        assertSame(extraDataMock, result.getExtraData());
        assertEquals(SOME_REMEMBER_ME_ID_STRING, result.getRememberMeId());
        assertEquals(SOME_REMEMBER_ME_ID_STRING, result.getUserId());
        assertEquals(SOME_PARENT_REMEMBER_ME_ID_STRING, result.getParentRememberMeId());
        assertEquals(ENCODED_RECEIPT_STRING, result.getReceiptId());
        assertEquals(DATE, result.getTimestamp());
    }

    @Test
    public void shouldGetCorrectExtraDataFromExtraDataConverter() throws Exception {
        Receipt receipt = new Receipt.Builder()
                .withWrappedReceiptKey(validReceiptKey)
                .withTimestamp(VALID_TIMESTAMP)
                .withRememberMeId(SOME_REMEMBER_ME_ID_BYTES)
                .withParentRememberMeId(SOME_PARENT_REMEMBER_ME_ID_BYTES)
                .withProfile(PROFILE_CONTENT)
                .withOtherPartyProfile(OTHER_PROFILE_CONTENT)
                .withReceiptId(DECODED_RECEIPT_BYTES)
                .withExtraData(EXTRA_DATA_CONTENT)
                .build();
        when(profileReaderMock.read(eq(PROFILE_CONTENT), any(Key.class))).thenReturn(profileMock);
        when(profileReaderMock.read(eq(OTHER_PROFILE_CONTENT), any(Key.class))).thenReturn(otherProfileMock);
        when(extraDataConverterMock.read(EXTRA_DATA_CONTENT)).thenReturn(extraDataMock);

        ActivityDetails result = testObj.create(receipt, keyPair.getPrivate());

        assertSame(otherProfileMock, getWrappedProfile(result.getUserProfile()));
        assertSame(profileMock, getWrappedProfile(result.getApplicationProfile()));
        assertSame(extraDataMock, result.getExtraData());
        assertEquals(SOME_REMEMBER_ME_ID_STRING, result.getRememberMeId());
        assertEquals(SOME_REMEMBER_ME_ID_STRING, result.getUserId());
        assertEquals(SOME_PARENT_REMEMBER_ME_ID_STRING, result.getParentRememberMeId());
        assertEquals(ENCODED_RECEIPT_STRING, result.getReceiptId());
        assertEquals(DATE, result.getTimestamp());
    }

    private Profile getWrappedProfile(HumanProfile humanProfile) throws Exception {
        return (Profile) FieldUtils.getField(HumanProfileAdapter.class, "wrapped", true).get(humanProfile);
    }

    private Profile getWrappedProfile(ApplicationProfile applicationProfile) throws Exception {
        return (Profile) FieldUtils.getField(ApplicationProfileAdapter.class, "wrapped", true).get(applicationProfile);
    }

}
