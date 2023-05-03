package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptSymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateSymmetricKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.GeneralSecurityException;
import java.security.Key;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.proto.AttributeProto;
import com.yoti.api.client.spi.remote.proto.AttributeListProto;
import com.yoti.api.client.spi.remote.proto.EncryptedDataProto;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EncryptedDataReaderTest {

    private static final String STRING_ATTRIBUTE_NAME = "testStringAttr";
    private static final AttributeProto.Attribute STRING_ATTRIBUTE_PROTO = AttributeProto.Attribute.newBuilder()
            .setName(STRING_ATTRIBUTE_NAME)
            .build();
    private static final byte[] PROFILE_DATA_BYTES = AttributeListProto.AttributeList.newBuilder()
            .addAttributes(STRING_ATTRIBUTE_PROTO)
            .build()
            .toByteArray();

    @InjectMocks
    EncryptedDataReader testObj;

    CryptoUtil.EncryptionResult validProfileEncryptionResult;
    Key receiptKey;

    @Before
    public void setUp() throws Exception {
        receiptKey = generateSymmetricKey();
        validProfileEncryptionResult = encryptSymmetric(PROFILE_DATA_BYTES, receiptKey);
    }

    @Test
    public void shouldWrapInvalidProtocolBufferException() {
        byte[] invalidProfileContent = new byte[]{1, 2, 3};

        try {
            testObj.decryptBytes(invalidProfileContent, receiptKey);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Cannot decode profile"));
            assertTrue(e.getCause() instanceof InvalidProtocolBufferException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWithNoIV() {
        byte[] profileContent = EncryptedDataProto.EncryptedData.newBuilder()
                .setCipherText(ByteString.copyFrom(validProfileEncryptionResult.data))
                .build()
                .toByteArray();

        try {
            testObj.decryptBytes(profileContent, receiptKey);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Receipt key IV must not be null."));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldWrapExceptionFromInvalidIV() {
        byte[] profileContent = EncryptedDataProto.EncryptedData.newBuilder()
                .setCipherText(ByteString.copyFrom(validProfileEncryptionResult.data))
                .setIv(ByteString.copyFrom(new byte[]{1, 2}))
                .build()
                .toByteArray();

        try {
            testObj.decryptBytes(profileContent, receiptKey);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Error decrypting data"));
            assertTrue(e.getCause() instanceof GeneralSecurityException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldWrapExceptionFromHavingNoEncryptedData() {
        byte[] profileContent = EncryptedDataProto.EncryptedData.newBuilder()
                .setIv(ByteString.copyFrom(validProfileEncryptionResult.iv))
                .build()
                .toByteArray();

        try {
            testObj.decryptBytes(profileContent, receiptKey);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Error decrypting data"));
            assertTrue(e.getCause() instanceof GeneralSecurityException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldWrapExceptionFromInvalidEncryptedData() {
        byte[] profileContent = EncryptedDataProto.EncryptedData.newBuilder()
                .setCipherText(ByteString.copyFrom(new byte[]{1, 2}))
                .setIv(ByteString.copyFrom(validProfileEncryptionResult.iv))
                .build()
                .toByteArray();

        try {
            testObj.decryptBytes(profileContent, receiptKey);
        } catch (ProfileException e) {
            assertThat(e.getMessage(), containsString("Error decrypting data"));
            assertTrue(e.getCause() instanceof GeneralSecurityException);
            return;
        }
        fail("Expected an Exception");
    }

}
