package com.yoti.api.client.spi.remote;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.Date;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.proto.AttributeListProto;
import com.yoti.api.client.spi.remote.proto.AttributeProto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class AttributeListConverterTest {

    private static final String STRING_ATTRIBUTE_NAME = "testStringAttr";
    private static final String DATE_ATTRIBUTE_NAME = "testDateAttr";
    private static final String JSON_ATTRIBUTE_NAME = "testJsonAttr";

    private static final AttributeProto.Attribute STRING_ATTRIBUTE_PROTO = AttributeProto.Attribute.newBuilder()
            .setName(STRING_ATTRIBUTE_NAME)
            .build();

    private static final AttributeProto.Attribute DATE_ATTRIBUTE_PROTO = AttributeProto.Attribute.newBuilder()
            .setName(DATE_ATTRIBUTE_NAME)
            .build();

    private static final AttributeProto.Attribute JSON_ATTRIBUTE_PROTO = AttributeProto.Attribute.newBuilder()
            .setName(JSON_ATTRIBUTE_NAME)
            .build();

    private static final AttributeProto.Attribute POSTAL_ADDRESS_PROTO = AttributeProto.Attribute.newBuilder()
            .setName(AttributeConstants.HumanProfileAttributes.POSTAL_ADDRESS)
            .build();

    private static final AttributeProto.Attribute STRUCTURED_ADDRESS_PROTO = AttributeProto.Attribute.newBuilder()
            .setName(AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS)
            .build();

    private static final byte[] PROFILE_DATA = createProfileData(
            STRING_ATTRIBUTE_PROTO,
            DATE_ATTRIBUTE_PROTO,
            JSON_ATTRIBUTE_PROTO
    );

    @InjectMocks AttributeListConverter testObj;

    @Mock AttributeConverter attributeConverter;
    @Mock AddressTransformer addressTransformer;

    @Mock Attribute<String> stringAttribute;
    @Mock Attribute<Date> dateAttribute;
    @Mock Attribute<Map<String, Object>> jsonAttribute;
    @Mock Attribute<String> postalAddress;
    @Mock Attribute<Map<String, Object>> structuredAddress;

    @Before
    public void setUp() throws Exception {
        when(structuredAddress.getName())
                .thenReturn(AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS);
    }

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
    public void shouldWrapInvalidProtocolBufferException() {
        try {
            testObj.parseAttributeList(new byte[] { 1, 2, 3 });
        } catch (ProfileException e) {
            assertTrue(e.getCause() instanceof InvalidProtocolBufferException);
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void shouldSuccessfullyConvertAttributes() throws Exception {
        when(attributeConverter.<String>convertAttribute(STRING_ATTRIBUTE_PROTO)).thenReturn(stringAttribute);
        when(attributeConverter.<Date>convertAttribute(DATE_ATTRIBUTE_PROTO)).thenReturn(dateAttribute);
        when(attributeConverter.<Map<String, Object>>convertAttribute(JSON_ATTRIBUTE_PROTO)).thenReturn(jsonAttribute);

        List<Attribute<?>> result = testObj.parseAttributeList(PROFILE_DATA);

        assertThat(result, hasSize(3));
        assertThat(result, hasItems(stringAttribute, dateAttribute, jsonAttribute));
    }

    @Test
    public void shouldTolerateFailureToParseSomeAttributes() throws Exception {
        when(attributeConverter.<String>convertAttribute(STRING_ATTRIBUTE_PROTO)).thenReturn(stringAttribute);
        when(attributeConverter.<Date>convertAttribute(DATE_ATTRIBUTE_PROTO)).thenThrow(new IOException());
        when(attributeConverter.<Map<String, Object>>convertAttribute(JSON_ATTRIBUTE_PROTO))
                .thenThrow(new ParseException("some message", 1));

        List<Attribute<?>> result = testObj.parseAttributeList(PROFILE_DATA);

        assertThat(result, hasSize(1));
        assertThat(result, hasItem(stringAttribute));
    }

    @Test
    public void shouldNotSubstituteStructuredAddressWhenPostalAddressIsPresent() throws Exception {
        when(attributeConverter.<String>convertAttribute(POSTAL_ADDRESS_PROTO)).thenReturn(postalAddress);

        List<Attribute<?>> result = testObj.parseAttributeList(createProfileData(POSTAL_ADDRESS_PROTO));

        assertThat(result, hasSize(1));
        assertThat(result, hasItem(postalAddress));
        verifyNoInteractions(addressTransformer);
    }

    @Test
    public void shouldNotSubstituteStructuredAddressWhenItsNotPresent() throws Exception {
        when(attributeConverter.<String>convertAttribute(STRING_ATTRIBUTE_PROTO)).thenReturn(stringAttribute);
        when(attributeConverter.<Date>convertAttribute(DATE_ATTRIBUTE_PROTO)).thenReturn(dateAttribute);
        when(attributeConverter.<Map<String, Object>>convertAttribute(JSON_ATTRIBUTE_PROTO)).thenReturn(jsonAttribute);

        List<Attribute<?>> result = testObj.parseAttributeList(PROFILE_DATA);

        assertThat(result, hasSize(3));
        assertThat(result, hasItems(stringAttribute, dateAttribute, jsonAttribute));
        verifyNoInteractions(addressTransformer);
    }

    @Test
    public void shouldSubstituteStructuredAddressForMissingPostalAddress() throws Exception {
        when(attributeConverter.<Map<String, Object>>convertAttribute(STRUCTURED_ADDRESS_PROTO))
                .thenReturn(structuredAddress);
        when(addressTransformer.transform(structuredAddress)).thenReturn(postalAddress);

        List<Attribute<?>> result = testObj.parseAttributeList(createProfileData(STRUCTURED_ADDRESS_PROTO));

        assertThat(result, hasSize(2));
        assertThat(result, hasItems(structuredAddress, postalAddress));
    }

    @Test
    public void shouldNotAddNullTransformedAddress() throws Exception {
        when(attributeConverter.<Map<String, Object>>convertAttribute(STRUCTURED_ADDRESS_PROTO))
                .thenReturn(structuredAddress);
        when(addressTransformer.transform(structuredAddress)).thenReturn(null);

        List<Attribute<?>> result = testObj.parseAttributeList(createProfileData(STRUCTURED_ADDRESS_PROTO));

        assertThat(result, hasSize(1));
        assertThat(result, hasItem(structuredAddress));
    }

    private static byte[] createProfileData(AttributeProto.Attribute... attributes) {
        return AttributeListProto.AttributeList.newBuilder()
                .addAllAttributes(Arrays.asList(attributes))
                .build()
                .toByteArray();
    }

}
