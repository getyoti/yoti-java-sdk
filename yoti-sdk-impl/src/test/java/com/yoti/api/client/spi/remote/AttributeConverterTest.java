package com.yoti.api.client.spi.remote;

import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.Anchor;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Image;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.ContentTypeProto;
import com.yoti.api.client.spi.remote.util.AnchorType;

import com.google.protobuf.ByteString;
import org.junit.Before;
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
    private static final String GENDER_MALE = "MALE";

    @InjectMocks AttributeConverter testObj;

    @Mock DocumentDetailsAttributeParser documentDetailsAttributeParserMock;
    @Mock AnchorConverter anchorConverterMock;

    @Mock DocumentDetails documentDetailsMock;
    @Mock AttrProto.Anchor verifierProtoMock;
    @Mock AttrProto.Anchor sourceProtoMock;
    @Mock AttrProto.Anchor unknownProtoMock;
    @Mock Anchor verifierAnchorMock;
    @Mock Anchor sourceAnchorMock;
    @Mock Anchor unknownAnchorMock;

    @Before
    public void setUp() throws Exception {
        when(verifierAnchorMock.getType()).thenReturn(AnchorType.VERIFIER.name());
        when(sourceAnchorMock.getType()).thenReturn(AnchorType.SOURCE.name());
        when(unknownAnchorMock.getType()).thenReturn(AnchorType.UNKNOWN.name());
    }

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
        String json = "{ \"simpleKey\": \"simpleValue\", \"objectKey\": { \"nestedKey\" : \"nestedValue\" } }";
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.JSON)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(json))
                .build();

        Attribute<Map> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        Map<String, Object> value = result.getValue();
        assertEquals(2, value.size());
        assertThat(value, hasEntry("simpleKey", (Object) "simpleValue"));
        assertThat(value, hasEntry("objectKey", (Object) Collections.singletonMap("nestedKey", "nestedValue")));
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
                .setName(AttributeConstants.HumanProfileAttributes.DOCUMENT_DETAILS)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .build();
        when(documentDetailsAttributeParserMock.parseFrom(SOME_STRING_VALUE)).thenReturn(documentDetailsMock);

        Attribute<DocumentDetails> result = testObj.convertAttribute(attribute);

        assertEquals(AttributeConstants.HumanProfileAttributes.DOCUMENT_DETAILS, result.getName());
        assertEquals(documentDetailsMock, result.getValue());
    }

    @Test
    public void shouldParseGenderAttribute() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.STRING)
                .setName(AttributeConstants.HumanProfileAttributes.GENDER)
                .setValue(ByteString.copyFromUtf8(GENDER_MALE))
                .build();

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertEquals(AttributeConstants.HumanProfileAttributes.GENDER, result.getName());
        assertEquals(HumanProfile.GENDER_MALE, result.getValue());
    }

    @Test
    public void shouldParseAllAnchorsSuccessfully() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .addAllAnchors(asList(verifierProtoMock, sourceProtoMock, unknownProtoMock))
                .build();
        when(anchorConverterMock.convert(verifierProtoMock)).thenReturn(verifierAnchorMock);
        when(anchorConverterMock.convert(sourceProtoMock)).thenReturn(sourceAnchorMock);
        when(anchorConverterMock.convert(unknownProtoMock)).thenReturn(unknownAnchorMock);

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertListContains(result.getAnchors(), asList(verifierAnchorMock, sourceAnchorMock, unknownAnchorMock));
        assertListContains(result.getVerifiers(), asList(verifierAnchorMock));
        assertListContains(result.getSources(), asList(sourceAnchorMock));
    }

    @Test
    public void shouldTolerateFailureToParseAnAnchor() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .addAllAnchors(asList(verifierProtoMock, sourceProtoMock, unknownProtoMock))
                .build();
        when(anchorConverterMock.convert(verifierProtoMock)).thenReturn(verifierAnchorMock);
        when(anchorConverterMock.convert(sourceProtoMock)).thenReturn(sourceAnchorMock);
        when(anchorConverterMock.convert(unknownProtoMock)).thenThrow(new CertificateException());

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertListContains(result.getAnchors(), asList(verifierAnchorMock, sourceAnchorMock));
        assertListContains(result.getVerifiers(), asList(verifierAnchorMock));
        assertListContains(result.getSources(), asList(sourceAnchorMock));
    }

    @Test
    public void shouldTolerateFailureToParseAnyAnchors() throws Exception {
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .addAllAnchors(asList(verifierProtoMock, sourceProtoMock, unknownProtoMock))
                .build();
        when(anchorConverterMock.convert(verifierProtoMock)).thenThrow(new RuntimeException());
        when(anchorConverterMock.convert(sourceProtoMock)).thenThrow(new IOException());
        when(anchorConverterMock.convert(unknownProtoMock)).thenThrow(new CertificateException());

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertThat(result.getAnchors(), hasSize(0));
        assertThat(result.getVerifiers(), hasSize(0));
        assertThat(result.getSources(), hasSize(0));
    }

    @Test
    public void shouldParseMultiValueAttributeWithAnyContent() throws Exception {
        AttrProto.MultiValue outer = createMultiValueTestData();
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setContentType(ContentTypeProto.ContentType.MULTI_VALUE)
                .setValue(outer.toByteString())
                .build();

        Attribute<List<Object>> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        List<Object> outerList = result.getValue();
        assertThat(outerList.size(), is(2));
        assertImageValue(outerList.get(0), "image/png", SOME_IMAGE_BYTES);
        List<Object> innerList = (List<Object>) outerList.get(1);
        assertImageValue(innerList.get(0), "image/jpeg", SOME_IMAGE_BYTES);
    }

    @Test
    public void shouldParseDocumentImages() throws Exception {
        AttrProto.MultiValue outer = createMultiValueTestData();
        AttrProto.Attribute attribute = AttrProto.Attribute.newBuilder()
                .setContentType(ContentTypeProto.ContentType.MULTI_VALUE)
                .setName(AttributeConstants.HumanProfileAttributes.DOCUMENT_IMAGES)
                .setValue(outer.toByteString())
                .build();

        Attribute<List<Object>> result = testObj.convertAttribute(attribute);

        assertEquals(AttributeConstants.HumanProfileAttributes.DOCUMENT_IMAGES, result.getName());
        List<Object> list = result.getValue();
        assertThat(list.size(), is(1));
        assertImageValue(list.get(0), "image/png", SOME_IMAGE_BYTES);
    }

    private static void assertImageValue(Object result, String mimeType, byte[] content) {
        Image image = (Image) result;
        assertThat(image.getMimeType(), is(mimeType));
        assertThat(image.getContent(), is(content));
    }

    private static AttrProto.MultiValue createMultiValueTestData() {
        AttrProto.MultiValue.Value jpgImageValue = createMultiValueValue(ContentTypeProto.ContentType.JPEG, ByteString.copyFrom(SOME_IMAGE_BYTES));
        AttrProto.MultiValue inner = createMultiValue(jpgImageValue);
        AttrProto.MultiValue.Value innerValue = createMultiValueValue(ContentTypeProto.ContentType.MULTI_VALUE, inner.toByteString());
        AttrProto.MultiValue.Value pngImageValue = createMultiValueValue(ContentTypeProto.ContentType.PNG, ByteString.copyFrom(SOME_IMAGE_BYTES));
        AttrProto.MultiValue outer = createMultiValue(pngImageValue, innerValue);
        return outer;
    }

    private static AttrProto.MultiValue.Value createMultiValueValue(ContentTypeProto.ContentType contentType, ByteString byteString) {
        return AttrProto.MultiValue.Value.newBuilder()
                .setContentType(contentType)
                .setData(byteString)
                .build();
    }

    private static AttrProto.MultiValue createMultiValue(AttrProto.MultiValue.Value... values) {
        return AttrProto.MultiValue.newBuilder()
                .addAllValues(asList(values))
                .build();
    }

    private static <T> void assertListContains(List<T> result, List<T> expected) {
        assertThat(result, hasSize(expected.size()));
        if (expected.size() > 0) {
            assertThat(result, hasItems((T[]) expected.toArray()));
        }
    }

}
