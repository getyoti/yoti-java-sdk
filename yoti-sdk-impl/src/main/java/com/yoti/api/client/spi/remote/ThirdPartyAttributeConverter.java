package com.yoti.api.client.spi.remote;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yoti.api.client.DateTime;
import com.yoti.api.client.AttributeIssuanceDetails;
import com.yoti.api.client.AttributeDefinition;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.spi.remote.proto.IssuingAttributesProto;
import com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.yoti.api.client.spi.remote.call.YotiConstants.RFC3339_PATTERN_MILLIS;

public class ThirdPartyAttributeConverter {

    private static Logger LOG = LoggerFactory.getLogger(ThirdPartyAttributeConverter.class);

    public static ThirdPartyAttributeConverter newInstance() {
        return new ThirdPartyAttributeConverter();
    }

    public AttributeIssuanceDetails parseThirdPartyAttribute(byte[] value) throws InvalidProtocolBufferException, ExtraDataException {
        ThirdPartyAttributeProto.ThirdPartyAttribute thirdPartyAttribute = ThirdPartyAttributeProto.ThirdPartyAttribute.parseFrom(value);

        IssuingAttributes issuingAttributes = parseIssuingAttributes(thirdPartyAttribute.getIssuingAttributes());

        ByteString issuanceToken = thirdPartyAttribute.getIssuanceToken();
        String token = Base64.getEncoder().encodeToString(issuanceToken.toByteArray());

        DateTime expiryDate = issuingAttributes.getExpiryDate();
        List<AttributeDefinition> attributeDefinitions = issuingAttributes.getAttributeDefinitions();

        if (token.isEmpty()) {
            throw new ExtraDataException("ThirdPartyAttribute missing token");
        }

        return new SimpleAttributeIssuanceDetails(token, expiryDate, attributeDefinitions);
    }

    private IssuingAttributes parseIssuingAttributes(IssuingAttributesProto.IssuingAttributes issuingAttributes) {
        String expiryDateString = issuingAttributes.getExpiryDate();
        DateTimeValue expiryDate = parseExpiryDateTime(expiryDateString);

        List<IssuingAttributesProto.Definition> definitionProtoList = issuingAttributes.getDefinitionsList();
        List<AttributeDefinition> attributeDefinitions = parseDefinitions(definitionProtoList);

        return new IssuingAttributes(expiryDate, attributeDefinitions);
    }

    private DateTimeValue parseExpiryDateTime(String dateTimeStringValue) {
        // Return null if datetime string is null or empty
        if (dateTimeStringValue == null || dateTimeStringValue.isEmpty()) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RFC3339_PATTERN_MILLIS);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateTimeValue dateTimeValue = null;
        try {
            Date date = simpleDateFormat.parse(dateTimeStringValue);
            dateTimeValue = DateTimeValue.from(date.getTime() * 1000);
        } catch (ParseException e) {
            LOG.error("Failed to parse date: '{}', {}", dateTimeStringValue, e.getMessage());
        }

        return dateTimeValue;
    }

    private List<AttributeDefinition> parseDefinitions(List<IssuingAttributesProto.Definition> definitions) {
        List<AttributeDefinition> attributes = new ArrayList<>();
        for (IssuingAttributesProto.Definition definition : definitions) {
            AttributeDefinition newDef = new SimpleAttributeDefinition(definition.getName());
            attributes.add(newDef);
        }
        return attributes;
    }

    static class IssuingAttributes {

        private DateTime expiryDate;

        private List<AttributeDefinition> attributeDefinitions;

        public IssuingAttributes(final DateTime expiryDate, final List<AttributeDefinition> attributeDefinitions) {
            this.expiryDate = expiryDate;
            this.attributeDefinitions = attributeDefinitions;
        }

        public DateTime getExpiryDate() {
            return expiryDate;
        }

        public List<AttributeDefinition> getAttributeDefinitions() {
            return attributeDefinitions;
        }

    }

}
