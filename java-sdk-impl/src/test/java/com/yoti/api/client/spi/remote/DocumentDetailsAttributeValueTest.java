package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Test;

import com.yoti.api.client.DocumentDetails;

public class DocumentDetailsAttributeValueTest {
    private static final String VALID_ATTRIBUTE = "PASSPORT GBR 1234abc 2016-05-01";
    private static final String INVALID_ATTRIBUTE__TYPE = "XXXPORT GBR 1234abc 2016-05-01";
    private static final String INVALID_ATTRIBUTE__NUMBER = "PASSPORT GBR $%^$%^£ 2016-05-01";
    private static final String INVALID_ATTRIBUTE__COUNTRY = "PASSPORT 13 1234abc 2016-05-01";
    private static final String INVALID_ATTRIBUTE__DATE = "PASSPORT GBR 1234abc X016-05-01";

    @Test
    public void shouldParseValidAttribute() throws UnsupportedEncodingException, ParseException {
        DocumentDetails details = DocumentDetailsAttributeValue.parseFrom(VALID_ATTRIBUTE);
        assertNotNull(details);
        assertEquals(DocumentDetails.DocumentType.PASSPORT, details.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailOnInvalidType() throws UnsupportedEncodingException, ParseException {
        DocumentDetailsAttributeValue.parseFrom(INVALID_ATTRIBUTE__TYPE);
    }

    @Test
    public void shouldFailOnInvalidNumber() throws UnsupportedEncodingException, ParseException {
        DocumentDetails details = DocumentDetailsAttributeValue.parseFrom(INVALID_ATTRIBUTE__NUMBER);
        assertNull(details);
    }

    @Test(expected = ParseException.class)
    public void shouldFailOnInvalidDate() throws UnsupportedEncodingException, ParseException {
        DocumentDetailsAttributeValue.parseFrom(INVALID_ATTRIBUTE__DATE);
    }

    @Test
    public void shouldFailOnInvalidCountry() throws UnsupportedEncodingException, ParseException {
        DocumentDetails details = DocumentDetailsAttributeValue.parseFrom(INVALID_ATTRIBUTE__COUNTRY);
        assertNull(details);
    }
}
