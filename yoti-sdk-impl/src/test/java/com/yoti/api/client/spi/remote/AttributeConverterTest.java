package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.HumanProfileAdapter.ATTRIBUTE_DOCUMENT_DETAILS;
import static com.yoti.api.client.spi.remote.HumanProfileAdapter.ATTRIBUTE_GENDER;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Map;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.ContentTypeProto;

import com.google.protobuf.ByteString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AttributeConverterTest {

    private static final String SOME_ATTRIBUTE_NAME = "someAttributeName";
    private static final String SOME_STRING_VALUE = "someStringValue";
    private static final String SOME_DATE_VALUE = "1980-08-05";
    private static final byte[] SOME_IMAGE_BYTES = "someImageBytes".getBytes();
    private static final String DRIVING_LICENCE_SOURCE_TYPE = "DRIVING_LICENCE";
    private static final String PASSPORT_SOURCE_TYPE = "PASSPORT";
    private static final String YOTI_ADMIN_VERIFIER_TYPE = "YOTI_ADMIN";
    private static final String GENDER_MALE = "MALE";

    @InjectMocks AttributeConverter testObj;

    @Mock DocumentDetailsAttributeParser documentDetailsAttributeParserMock;

    @Mock DocumentDetails documentDetailsMock;

    @Test
    public void shouldParseStringAttribute() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.STRING)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .build();

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(SOME_STRING_VALUE, result.getValue());
    }

    @Test
    public void shouldParseDateAttribute() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.DATE)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_DATE_VALUE))
                .build();

        Attribute<Date> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(DateValue.parseFrom(SOME_DATE_VALUE).toString(), result.getValue().toString());
    }

    @Test
    public void shouldParseJpegAttribute() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.JPEG)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFrom(SOME_IMAGE_BYTES))
                .build();

        Attribute<JpegAttributeValue> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertArrayEquals(SOME_IMAGE_BYTES, result.getValue().getContent());
    }

    @Test
    public void shouldParsePngAttribute() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.PNG)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFrom(SOME_IMAGE_BYTES))
                .build();

        Attribute<PngAttributeValue> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertArrayEquals(SOME_IMAGE_BYTES, result.getValue().getContent());
    }

    @Test
    public void shouldParseJsonAttribute() throws Exception {
        String json = "{ \"someKey\": \"someValue\" }";
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.JSON)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(json))
                .build();

        Attribute<Map> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        Map<String, String> value = result.getValue();
        assertEquals(1, value.size());
        assertThat(value, hasEntry("someKey", "someValue"));
    }

    @Test
    public void shouldFallbackToStringForUnknownType() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.UNDEFINED)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .build();

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(SOME_STRING_VALUE, result.getValue());
    }

    @Test
    public void shouldParseDocumentDetailsAttribute() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.STRING)
                .setName(ATTRIBUTE_DOCUMENT_DETAILS)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .build();
        when(documentDetailsAttributeParserMock.parseFrom(SOME_STRING_VALUE)).thenReturn(documentDetailsMock);

        Attribute<DocumentDetails> result = testObj.convertAttribute(attribute);

        assertEquals(ATTRIBUTE_DOCUMENT_DETAILS, result.getName());
        assertEquals(documentDetailsMock, result.getValue());
    }

    @Test
    public void shouldParseGenderAttribute() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.STRING)
                .setName(ATTRIBUTE_GENDER)
                .setValue(ByteString.copyFromUtf8(GENDER_MALE))
                .build();

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertEquals(ATTRIBUTE_GENDER, result.getName());
        assertEquals(HumanProfile.GENDER_MALE, result.getValue());
    }

    @Test
    public void getAttributeSourcesShouldIncludeDrivingLicence() throws Exception {
        AttrProto.Anchor anchor = AttrProto.Anchor.parseFrom(Base64.getDecoder().decode(TestAnchors.DL_ANCHOR));
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .addAnchors(anchor)
                .build();

        Attribute result = testObj.convertAttribute(attribute);

        assertTrue(result.getSources().contains(DRIVING_LICENCE_SOURCE_TYPE));
    }

    @Test
    public void getAttributeSourcesShouldIncludePassport() throws Exception {
        AttrProto.Anchor anchor = AttrProto.Anchor.parseFrom(Base64.getDecoder().decode(TestAnchors.PP_ANCHOR));
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .addAnchors(anchor)
                .build();

        Attribute result = testObj.convertAttribute(attribute);

        assertTrue(result.getSources().contains(PASSPORT_SOURCE_TYPE));
    }

    @Test
    public void getAttributeVerifiersShouldIncludeYotiAdmin() throws Exception {
        AttrProto.Anchor anchor = AttrProto.Anchor.parseFrom(Base64.getDecoder().decode(TestAnchors.YOTI_ADMIN_ANCHOR));
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .addAnchors(anchor)
                .build();

        Attribute result = testObj.convertAttribute(attribute);

        assertTrue(result.getVerifiers().contains(YOTI_ADMIN_VERIFIER_TYPE));
    }

}
