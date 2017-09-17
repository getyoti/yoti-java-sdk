package com.yoti.api.client.spi.remote;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;

final class DocumentDetailsAttributeValue implements DocumentDetails {
    private static final String DETAILS_REGEX = "([A-Za-z]*) ([A-Za-z]{3}) ([A-Za-z0-9]*) (.*)";
    private static final Pattern DETAILS_PATTERN = Pattern.compile(DETAILS_REGEX);
    private static final int TYPE_GROUP = 1;
    private static final int COUNTRY_GROUP = 2;
    private static final int NUMBER_GROUP = 3;
    private static final int EXPIRATION_GROUP = 4;
    private final DocumentType type;
    private final String issuingCountry;
    private final Date expirationDate;
    private final String number;

    public DocumentDetailsAttributeValue(DocumentType type, String issuingCountry, Date expirationDate, String number) {
        this.type = type;
        this.issuingCountry = issuingCountry;
        this.expirationDate = expirationDate;
        this.number = number;
    }

    public static DocumentDetails parseFrom(String attribute) throws UnsupportedEncodingException, ParseException {
        DocumentDetailsAttributeValue result = null;
        if (attribute != null) {
            Matcher matcher = DETAILS_PATTERN.matcher(attribute);
            if (matcher.matches()) {
                DocumentType type = DocumentType.valueOf(matcher.group(TYPE_GROUP));
                String issuingCountry = matcher.group(COUNTRY_GROUP);
                Date expirationDate = DateAttributeValue.parseFrom(matcher.group(EXPIRATION_GROUP));
                String number = matcher.group(NUMBER_GROUP);
                result = new DocumentDetailsAttributeValue(type, issuingCountry, expirationDate, number);
            }
        }
        return result;
    }

    @Override
    public DocumentType getType() {
        return type;
    }

    @Override
    public String getIssuingCountry() {
        return issuingCountry;
    }

    @Override
    public String getDocumentNumber() {
        return number;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }
}
