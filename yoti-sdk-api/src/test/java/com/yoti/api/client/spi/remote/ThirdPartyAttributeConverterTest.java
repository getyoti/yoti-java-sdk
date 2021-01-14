package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.ProtoUtil.buildDefinition;
import static com.yoti.api.client.spi.remote.util.ProtoUtil.buildIssuingAttributes;
import static com.yoti.api.client.spi.remote.util.ProtoUtil.buildThirdPartyAttribute;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Base64;
import java.util.List;

import com.yoti.api.client.AttributeDefinition;
import com.yoti.api.client.AttributeIssuanceDetails;
import com.yoti.api.client.DateTime;
import com.yoti.api.client.DateTimeTest;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.spi.remote.proto.IssuingAttributesProto;
import com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ThirdPartyAttributeConverterTest {

    private static final String SOME_ISSUANCE_TOKEN = "someIssuanceToken";
    private static final String SOME_ISSUANCE_TOKEN_B64 = "c29tZUlzc3VhbmNlVG9rZW4=";
    private static final String SOME_EXPIRY_DATE = "2019-10-15T22:04:05.123Z";
    private static final String SOME_INVALID_FORMAT_EXPIRY_DATE = "2019-10-15";
    private static final String SOME_DEFINITION_NAME = "com.thirdparty.id";

    private static final String ENCODED_THIRD_PARTY_ATTRIBUTE = "ChFzb21lSXNzdWFuY2VUb2tlbhIvChgyMDE5LTEwLTE1VDIyOjA0OjA1LjEyM1oSEwoRY29tLnRoaXJkcGFydHkuaWQ=";
    private static final byte[] ENCODED_THIRD_PARTY_ATTRIBUTE_BYTES = Base64.getDecoder().decode(ENCODED_THIRD_PARTY_ATTRIBUTE);

    @InjectMocks
    ThirdPartyAttributeConverter thirdPartyAttributeConverter;

    @Test
    public void shouldParseThirdPartyAttributeCorrectly() throws Exception {
        AttributeIssuanceDetails attributeIssuanceDetails = thirdPartyAttributeConverter.parseThirdPartyAttribute(ENCODED_THIRD_PARTY_ATTRIBUTE_BYTES);

        DateTime dateTimeValue = attributeIssuanceDetails.getExpiryDate();
        String issuanceToken = attributeIssuanceDetails.getToken();
        List<AttributeDefinition> issuingAttributes = attributeIssuanceDetails.getIssuingAttributes();

        assertThat(issuingAttributes, hasSize(1));
        assertThat(issuingAttributes.get(0).getName(), is(SOME_DEFINITION_NAME));
        assertThat(issuanceToken, is(SOME_ISSUANCE_TOKEN_B64));

        DateTimeTest.assertDateTime(
                dateTimeValue, 2019, 10, 15, 22, 4, 5, 123000
        );
    }

    @Test
    public void shouldParseThirdPartyAttributeWithMultipleIssuingAttributes() throws Exception {
        IssuingAttributesProto.IssuingAttributes issuingAttributes = buildIssuingAttributes(
                SOME_EXPIRY_DATE,
                buildDefinition("SOME_DEFINITION"),
                buildDefinition("SOME_OTHER_DEFINITION")
        );

        byte[] thirdPartyAttribute = buildThirdPartyAttribute(SOME_ISSUANCE_TOKEN, issuingAttributes).toByteArray();
        AttributeIssuanceDetails attributeIssuanceDetails = thirdPartyAttributeConverter.parseThirdPartyAttribute(thirdPartyAttribute);

        assertThat(attributeIssuanceDetails.getIssuingAttributes().size(), is(2));
        assertThat(attributeIssuanceDetails.getIssuingAttributes().get(0).getName(), is("SOME_DEFINITION"));
        assertThat(attributeIssuanceDetails.getIssuingAttributes().get(1).getName(), is("SOME_OTHER_DEFINITION"));
    }

    @Test(expected = ExtraDataException.class)
    public void shouldThrowExceptionForThirdPartyAttributeWithoutToken() throws Exception {
        byte[] thirdPartyAttribute = buildThirdPartyAttribute("", null).toByteArray();

        thirdPartyAttributeConverter.parseThirdPartyAttribute(thirdPartyAttribute);
    }

    @Test
    public void shouldReturnNullForIncorrectExpiryDateFormat() throws Exception {
        IssuingAttributesProto.IssuingAttributes issuingAttributes = buildIssuingAttributes(SOME_INVALID_FORMAT_EXPIRY_DATE);

        ThirdPartyAttributeProto.ThirdPartyAttribute thirdPartyAttribute = buildThirdPartyAttribute(SOME_ISSUANCE_TOKEN, issuingAttributes);

        ThirdPartyAttributeConverter thirdPartyAttributeConverter = ThirdPartyAttributeConverter.newInstance();
        AttributeIssuanceDetails attributeIssuanceDetails =
                thirdPartyAttributeConverter.parseThirdPartyAttribute(thirdPartyAttribute.toByteArray());

        assertThat(attributeIssuanceDetails.getToken(), is(SOME_ISSUANCE_TOKEN_B64));
        assertNull(attributeIssuanceDetails.getExpiryDate());
    }

    @Test
    public void shouldReturnNullForEmptyExpiryDate() throws Exception {
        IssuingAttributesProto.IssuingAttributes issuingAttributes = buildIssuingAttributes("");
        ThirdPartyAttributeProto.ThirdPartyAttribute thirdPartyAttribute = buildThirdPartyAttribute(SOME_ISSUANCE_TOKEN, issuingAttributes);

        ThirdPartyAttributeConverter thirdPartyAttributeConverter = ThirdPartyAttributeConverter.newInstance();
        AttributeIssuanceDetails attributeIssuanceDetails =
                thirdPartyAttributeConverter.parseThirdPartyAttribute(thirdPartyAttribute.toByteArray());

        assertThat(attributeIssuanceDetails.getToken(), is(SOME_ISSUANCE_TOKEN_B64));
        assertNull(attributeIssuanceDetails.getExpiryDate());
    }

    @Test
    public void shouldHandleNoIssuingAttributes() throws Exception {
        ThirdPartyAttributeProto.ThirdPartyAttribute thirdPartyAttribute = buildThirdPartyAttribute(SOME_ISSUANCE_TOKEN, null);

        ThirdPartyAttributeConverter thirdPartyAttributeConverter = ThirdPartyAttributeConverter.newInstance();
        AttributeIssuanceDetails attributeIssuanceDetails =
                thirdPartyAttributeConverter.parseThirdPartyAttribute(thirdPartyAttribute.toByteArray());

        assertNotNull(attributeIssuanceDetails);
        assertThat(attributeIssuanceDetails.getToken(), is(SOME_ISSUANCE_TOKEN_B64));
        assertNull(attributeIssuanceDetails.getExpiryDate());
        assertThat(attributeIssuanceDetails.getIssuingAttributes().size(), is(0));
    }

    @Test(expected = InvalidProtocolBufferException.class)
    public void shouldThrowInvalidProtocolBufferExceptionForInvalidProtobuf() throws Exception {
        byte[] invalidBytes = {1, 2, 3, 4};

        ThirdPartyAttributeConverter thirdPartyAttributeConverter = ThirdPartyAttributeConverter.newInstance();
        thirdPartyAttributeConverter.parseThirdPartyAttribute(invalidBytes);
    }

}
