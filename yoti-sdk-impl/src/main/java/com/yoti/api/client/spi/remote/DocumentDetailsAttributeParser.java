package com.yoti.api.client.spi.remote;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;

class DocumentDetailsAttributeParser {

    private static final String MINIMUM_ACCEPTABLE = "([A-Za-z_]*) ([A-Za-z]{3}) ([A-Za-z0-9]{1}).*";
    private static final int TYPE_INDEX = 0;
    private static final int COUNTRY_INDEX = 1;
    private static final int NUMBER_INDEX = 2;
    private static final int EXPIRATION_INDEX = 3;
    private static final int AUTHORITY_INDEX = 4;

    DocumentDetails parseFrom(String attributeValue) throws UnsupportedEncodingException, ParseException {
        if (attributeValue == null || !attributeValue.matches(MINIMUM_ACCEPTABLE)) {
            throw new IllegalArgumentException("Unable to parse attribute value to a DocumentDetails");
        }
        String[] attributes = attributeValue.split(" ");

        return DocumentDetailsAttributeValue.builder()
                .withType(attributes[TYPE_INDEX])
                .withIssuingCountry(attributes[COUNTRY_INDEX])
                .withNumber(attributes[NUMBER_INDEX])
                .withDate(getDateSafely(attributes, EXPIRATION_INDEX))
                .withAuthority(getSafely(attributes, AUTHORITY_INDEX))
                .build();
    }

    private static Date getDateSafely(String[] attributes, int index) throws UnsupportedEncodingException, ParseException {
        String expirationDate = getSafely(attributes, index);
        return expirationDate == null ? null : DateValue.parseFrom(expirationDate);
    }

    private static String getSafely(String[] attributes, int index) {
        String value = attributes.length > index ? attributes[index] : null;
        return "-".equals(value) ? null : value;
    }

}
