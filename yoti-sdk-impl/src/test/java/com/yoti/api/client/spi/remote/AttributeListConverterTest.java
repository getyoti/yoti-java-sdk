package com.yoti.api.client.spi.remote;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Date;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.AttributeListProto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AttributeListConverterTest {

    private static final String STRING_ATTRIBUTE_NAME = "testStringAttr";
    private static final String DATE_ATTRIBUTE_NAME = "testDateAttr";
    private static final String JSON_ATTRIBUTE_NAME = "testJsonAttr";

    private static final AttrProto.Attribute STRING_ATTRIBUTE_PROTO = AttrProto.Attribute.newBuilder()
            .setName(STRING_ATTRIBUTE_NAME)
            .build();

    private static final AttrProto.Attribute DATE_ATTRIBUTE_PROTO = AttrProto.Attribute.newBuilder()
            .setName(DATE_ATTRIBUTE_NAME)
            .build();

    private static final AttrProto.Attribute JSON_ATTRIBUTE_PROTO = AttrProto.Attribute.newBuilder()
            .setName(JSON_ATTRIBUTE_NAME)
            .build();

    private static final byte[] PROFILE_DATA = AttributeListProto.AttributeList.newBuilder()
            .addAttributes(STRING_ATTRIBUTE_PROTO)
            .addAttributes(DATE_ATTRIBUTE_PROTO)
            .addAttributes(JSON_ATTRIBUTE_PROTO)
            .build()
            .toByteArray();

    @InjectMocks AttributeListConverter testObj;

    @Mock AttributeConverter attributeConverterMock;

    @Mock Attribute<String> stringAttributeMock;
    @Mock Attribute<Date> dateAttributeMock;
    @Mock Attribute<Map> jsonAttributeMock;

    @Test
    public void shouldReturnEmptyListForNullProfileData() throws Exception {
        List<Attribute<?>> result = testObj.parseAttributeList(null);

        assertThat(result, hasSize(0));
    }

    @Test
    public void shouldReturnEmptyListForEmptyProfileData() throws Exception {
        List<Attribute<?>> result = testObj.parseAttributeList(new byte[0]);

        assertThat(result, hasSize(0));
    }

    @Test
    public void shouldWrapInvalidProtocolBufferException() throws Exception {
        try {
            testObj.parseAttributeList(new byte[] { 1, 2, 3 });
        } catch (ProfileException e) {
            assertTrue(e.getCause() instanceof InvalidProtocolBufferException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldSuccesfullyConvertAttributes() throws Exception {
        when(attributeConverterMock.<String>convertAttribute(STRING_ATTRIBUTE_PROTO)).thenReturn(stringAttributeMock);
        when(attributeConverterMock.<Date>convertAttribute(DATE_ATTRIBUTE_PROTO)).thenReturn(dateAttributeMock);
        when(attributeConverterMock.<Map>convertAttribute(JSON_ATTRIBUTE_PROTO)).thenReturn(jsonAttributeMock);

        List<Attribute<?>> result = testObj.parseAttributeList(PROFILE_DATA);

        assertThat(result, hasSize(3));
        assertThat(result, hasItems(stringAttributeMock, dateAttributeMock, jsonAttributeMock));
    }

    @Test
    public void shouldTolerateFailureToParseSomeAttributes() throws Exception {
        when(attributeConverterMock.<String>convertAttribute(STRING_ATTRIBUTE_PROTO)).thenReturn(stringAttributeMock);
        when(attributeConverterMock.<Date>convertAttribute(DATE_ATTRIBUTE_PROTO)).thenThrow(new IOException());
        when(attributeConverterMock.<Map>convertAttribute(JSON_ATTRIBUTE_PROTO)).thenThrow(new ParseException("some message", 1));

        List<Attribute<?>> result = testObj.parseAttributeList(PROFILE_DATA);

        assertThat(result, hasSize(1));
        assertThat(result, hasItem(stringAttributeMock));
    }

}
