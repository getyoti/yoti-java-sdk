package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.encryptSymmetric;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateSymmetricKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

import java.security.Key;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.spi.remote.proto.AttributeProto;
import com.yoti.api.client.spi.remote.proto.AttributeListProto;
import com.yoti.api.client.spi.remote.proto.EncryptedDataProto;
import com.yoti.api.client.spi.remote.util.CryptoUtil;

import com.google.protobuf.ByteString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AttributeListReaderTest {

    private static final String STRING_ATTRIBUTE_NAME = "testStringAttr";
    private static final AttributeProto.Attribute STRING_ATTRIBUTE_PROTO = AttributeProto.Attribute.newBuilder()
            .setName(STRING_ATTRIBUTE_NAME)
            .build();
    private static final byte[] PROFILE_DATA_BYTES = AttributeListProto.AttributeList.newBuilder()
            .addAttributes(STRING_ATTRIBUTE_PROTO)
            .build()
            .toByteArray();

    @InjectMocks AttributeListReader testObj;

    @Mock EncryptedDataReader encryptedDataReader;
    @Mock AttributeListConverter attributeListConverter;

    CryptoUtil.EncryptionResult validProfileEncryptionResult;
    Key receiptKey;

    @Before
    public void setUp() throws Exception {
        receiptKey = generateSymmetricKey();
        validProfileEncryptionResult = encryptSymmetric(PROFILE_DATA_BYTES, receiptKey);
    }

    @Test
    public void shouldGetEmptyProfileWhenGivenNullContent() throws Exception {
        List<Attribute<?>> result = testObj.read(null, receiptKey);

        assertThat(result, hasSize(0));
    }

    @Test
    public void shouldDecodeProfileCorrectly() throws Exception {
        Attribute<String> stringAttribute = new Attribute<>("someName", "someValue");
        byte[] profileContent = EncryptedDataProto.EncryptedData.newBuilder()
                .setCipherText(ByteString.copyFrom(validProfileEncryptionResult.data))
                .setIv(ByteString.copyFrom(validProfileEncryptionResult.iv))
                .build()
                .toByteArray();
        when(attributeListConverter.parseAttributeList(PROFILE_DATA_BYTES))
                .thenReturn(Collections.singletonList(stringAttribute));
        when(encryptedDataReader.decryptBytes(profileContent, receiptKey)).thenReturn(PROFILE_DATA_BYTES);

        List<Attribute<?>> result = testObj.read(profileContent, receiptKey);

        assertThat(result, hasSize(1));
        assertThat(result, hasItem(stringAttribute));
    }

}
