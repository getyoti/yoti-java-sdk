package com.yoti.api.client.spi.remote;

import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.Base64;
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
import com.yoti.api.client.spi.remote.proto.AttributeProto;
import com.yoti.api.client.spi.remote.util.AnchorType;

import com.google.protobuf.ByteString;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class AttributeConverterTest {

    private static final String SOME_ATTRIBUTE_NAME = "someAttributeName";
    private static final String SOME_STRING_VALUE = "someStringValue";
    private static final String SOME_DATE_VALUE = "1980-08-05";
    private static final byte[] SOME_IMAGE_BYTES = "someImageBytes".getBytes();
    private static final String GENDER_MALE = "MALE";
    private static final String EMPTY_STRING = "";
    public static final Integer SOME_INT_VALUE = 123;
    public static final byte[] TEST_THIRD_PARTY_ASSIGNED_ATTRIBUTE_BYTES = Base64.getDecoder().decode(TestAttributes.THIRD_PARTY_ASSIGNED_ATTRIBUTE);

    @InjectMocks
    AttributeConverter testObj;

    @Mock
    DocumentDetailsAttributeParser documentDetailsAttributeParserMock;
    @Mock
    AnchorConverter anchorConverterMock;

    @Mock
    DocumentDetails documentDetailsMock;
    @Mock
    AttributeProto.Anchor verifierProtoMock;
    @Mock
    AttributeProto.Anchor sourceProtoMock;
    @Mock
    AttributeProto.Anchor unknownProtoMock;
    @Mock
    Anchor verifierAnchorMock;
    @Mock
    Anchor sourceAnchorMock;
    @Mock
    Anchor unknownAnchorMock;

    @Before
    public void setUp() {
        when(verifierAnchorMock.getType()).thenReturn(AnchorType.VERIFIER.name());
        when(sourceAnchorMock.getType()).thenReturn(AnchorType.SOURCE.name());
        when(unknownAnchorMock.getType()).thenReturn(AnchorType.UNKNOWN.name());
    }

    @Test
    public void shouldParseStringAttribute() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.STRING)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .build();

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(SOME_STRING_VALUE, result.getValue());
    }

    @Test
    public void shouldParseDateAttribute() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.DATE)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_DATE_VALUE))
                .build();

        Attribute<Date> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(Date.parseFrom(SOME_DATE_VALUE).toString(), result.getValue().toString());
    }

    @Test
    public void shouldParseJpegAttribute() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.JPEG)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFrom(SOME_IMAGE_BYTES))
                .build();

        Attribute<JpegAttributeValue> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertArrayEquals(SOME_IMAGE_BYTES, result.getValue().getContent());
    }

    @Test
    public void shouldParsePngAttribute() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.PNG)
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
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.JSON)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(json))
                .build();

        Attribute<Map<String, Object>> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        Map<String, Object> value = result.getValue();
        assertEquals(2, value.size());
        assertThat(value, hasEntry("simpleKey", "simpleValue"));
        assertThat(value, hasEntry("objectKey", Collections.singletonMap("nestedKey", "nestedValue")));
    }

    @Test
    public void shouldFallbackToStringForUnknownType() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.UNDEFINED)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .build();

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(SOME_STRING_VALUE, result.getValue());
    }

    @Test
    public void shouldParseDocumentDetailsAttribute() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.STRING)
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
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.STRING)
                .setName(AttributeConstants.HumanProfileAttributes.GENDER)
                .setValue(ByteString.copyFromUtf8(GENDER_MALE))
                .build();

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertEquals(AttributeConstants.HumanProfileAttributes.GENDER, result.getName());
        assertEquals(HumanProfile.GENDER_MALE, result.getValue());
    }

    @Test
    public void shouldParseAllAnchorsSuccessfully() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .addAllAnchors(asList(verifierProtoMock, sourceProtoMock, unknownProtoMock))
                .build();
        when(anchorConverterMock.convert(verifierProtoMock)).thenReturn(verifierAnchorMock);
        when(anchorConverterMock.convert(sourceProtoMock)).thenReturn(sourceAnchorMock);
        when(anchorConverterMock.convert(unknownProtoMock)).thenReturn(unknownAnchorMock);

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertListContains(result.getAnchors(), asList(verifierAnchorMock, sourceAnchorMock, unknownAnchorMock));
        assertListContains(result.getVerifiers(), Collections.singletonList(verifierAnchorMock));
        assertListContains(result.getSources(), Collections.singletonList(sourceAnchorMock));
    }

    @Test
    public void shouldTolerateFailureToParseAnAnchor() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(SOME_STRING_VALUE))
                .addAllAnchors(asList(verifierProtoMock, sourceProtoMock, unknownProtoMock))
                .build();
        when(anchorConverterMock.convert(verifierProtoMock)).thenReturn(verifierAnchorMock);
        when(anchorConverterMock.convert(sourceProtoMock)).thenReturn(sourceAnchorMock);
        when(anchorConverterMock.convert(unknownProtoMock)).thenThrow(new CertificateException());

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertListContains(result.getAnchors(), asList(verifierAnchorMock, sourceAnchorMock));
        assertListContains(result.getVerifiers(), Collections.singletonList(verifierAnchorMock));
        assertListContains(result.getSources(), Collections.singletonList(sourceAnchorMock));
    }

    @Test
    public void shouldTolerateFailureToParseAnyAnchors() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
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
        AttributeProto.MultiValue outer = createMultiValueTestData();
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setName(SOME_ATTRIBUTE_NAME)
                .setContentType(AttributeProto.ContentType.MULTI_VALUE)
                .setValue(outer.toByteString())
                .build();

        Attribute<List<Object>> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        List<Object> outerList = result.getValue();
        assertThat(outerList.size(), is(2));
        assertImageValue(outerList.get(0), "image/png");
        List<Object> innerList = (List<Object>) outerList.get(1);
        assertImageValue(innerList.get(0), "image/jpeg");
    }

    @Test
    public void shouldParseDocumentImages() throws Exception {
        AttributeProto.MultiValue outer = createMultiValueTestData();
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.MULTI_VALUE)
                .setName(AttributeConstants.HumanProfileAttributes.DOCUMENT_IMAGES)
                .setValue(outer.toByteString())
                .build();

        Attribute<List<Object>> result = testObj.convertAttribute(attribute);

        assertEquals(AttributeConstants.HumanProfileAttributes.DOCUMENT_IMAGES, result.getName());
        List<Object> list = result.getValue();
        assertThat(list.size(), is(1));
        assertImageValue(list.get(0), "image/png");
    }

    private static void assertImageValue(Object result, String mimeType) {
        Image image = (Image) result;
        assertThat(image.getMimeType(), is(mimeType));
        assertThat(image.getContent(), is(AttributeConverterTest.SOME_IMAGE_BYTES));
    }

    @Test
    public void shouldParseStringAttributeWithEmptyValue() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.STRING)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(""))
                .build();

        Attribute<String> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(EMPTY_STRING, result.getValue());
    }

    @Test(expected = ParseException.class)
    public void shouldNotParseDateWithEmptyValue() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.DATE)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(EMPTY_STRING))
                .build();

        testObj.convertAttribute(attribute);
    }

    @Test(expected = ParseException.class)
    public void shouldNotParseJpegAttributeWithEmptyValue() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.JPEG)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(EMPTY_STRING))
                .build();

        testObj.convertAttribute(attribute);
    }

    @Test(expected = ParseException.class)
    public void shouldNotParsePngAttributeWithEmptyValue() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.PNG)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(EMPTY_STRING))
                .build();

        testObj.convertAttribute(attribute);
    }

    @Test(expected = ParseException.class)
    public void shouldNotParseJsonAttributeWithEmptyValue() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.JSON)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(EMPTY_STRING))
                .build();

        testObj.convertAttribute(attribute);
    }

    @Test(expected = ParseException.class)
    public void shouldNotParseMultiValueAttributeWithEmptyValue() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.MULTI_VALUE)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(EMPTY_STRING))
                .build();

        testObj.convertAttribute(attribute);
    }

    @Test(expected = ParseException.class)
    public void shouldNotParseIntAttributeWithEmptyValue() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.INT)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(EMPTY_STRING))
                .build();

        testObj.convertAttribute(attribute);
    }

    @Test
    public void shouldParseActualThirdPartyAssignedAttribute() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.parseFrom(TEST_THIRD_PARTY_ASSIGNED_ATTRIBUTE_BYTES);
        AttributeConverter attributeConverter = AttributeConverter.newInstance();

        Attribute<String> result = attributeConverter.convertAttribute(attribute);

        assertThat(result.getName(), is("com.thirdparty.id"));
        assertThat(result.getValue(), is("test-third-party-attribute-0"));

        assertThat(result.getSources().get(0).getValue(), is("THIRD_PARTY"));
        assertThat(result.getSources().get(0).getSubType(), is("orgName"));

        assertThat(result.getVerifiers().get(0).getValue(), is("THIRD_PARTY"));
        assertThat(result.getVerifiers().get(0).getSubType(), is("orgName"));
    }

    private static AttributeProto.MultiValue createMultiValueTestData() {
        AttributeProto.MultiValue.Value jpgImageValue = createMultiValueValue(AttributeProto.ContentType.JPEG, ByteString.copyFrom(SOME_IMAGE_BYTES));
        AttributeProto.MultiValue inner = createMultiValue(jpgImageValue);
        AttributeProto.MultiValue.Value innerValue = createMultiValueValue(AttributeProto.ContentType.MULTI_VALUE, inner.toByteString());
        AttributeProto.MultiValue.Value pngImageValue = createMultiValueValue(AttributeProto.ContentType.PNG, ByteString.copyFrom(SOME_IMAGE_BYTES));
        return createMultiValue(pngImageValue, innerValue);
    }

    private static AttributeProto.MultiValue.Value createMultiValueValue(AttributeProto.ContentType contentType, ByteString byteString) {
        return AttributeProto.MultiValue.Value.newBuilder()
                .setContentType(contentType)
                .setData(byteString)
                .build();
    }

    private static AttributeProto.MultiValue createMultiValue(AttributeProto.MultiValue.Value... values) {
        return AttributeProto.MultiValue.newBuilder()
                .addAllValues(asList(values))
                .build();
    }

    @Test
    public void shouldParseAnIntValue() throws Exception {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.INT)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(String.valueOf(SOME_INT_VALUE)))
                .build();

        Attribute<Integer> result = testObj.convertAttribute(attribute);

        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(SOME_INT_VALUE, result.getValue());
    }

    @Test
    public void shouldReturnNullIdWhenNotSet() throws ParseException, IOException {
        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setContentType(AttributeProto.ContentType.INT)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(String.valueOf(SOME_INT_VALUE)))
                .build();

        Attribute<Integer> result = testObj.convertAttribute(attribute);

        assertNull(result.getId());
        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(SOME_INT_VALUE, result.getValue());
    }

    @Test
    public void shouldReturnTheIdWhenSet() throws ParseException, IOException {
        String anId = "anId";

        AttributeProto.Attribute attribute = AttributeProto.Attribute.newBuilder()
                .setEphemeralId(anId)
                .setContentType(AttributeProto.ContentType.INT)
                .setName(SOME_ATTRIBUTE_NAME)
                .setValue(ByteString.copyFromUtf8(String.valueOf(SOME_INT_VALUE)))
                .build();

        Attribute<Integer> result = testObj.convertAttribute(attribute);

        assertEquals(anId, result.getId());
        assertEquals(SOME_ATTRIBUTE_NAME, result.getName());
        assertEquals(SOME_INT_VALUE, result.getValue());
    }

    private static <T> void assertListContains(List<T> result, List<T> expected) {
        assertThat(result, hasSize(expected.size()));
        if (expected.size() > 0) {
            assertThat(result, hasItems((T[]) expected.toArray()));
        }
    }

}
