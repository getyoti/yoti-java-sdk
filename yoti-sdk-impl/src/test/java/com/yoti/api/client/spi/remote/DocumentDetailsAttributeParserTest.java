package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;

import com.yoti.api.client.DocumentDetails;

import org.junit.Test;

public class DocumentDetailsAttributeParserTest {

    DocumentDetailsAttributeParser testObj = new DocumentDetailsAttributeParser();

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullAttribute() throws Exception {
        testObj.parseFrom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAttributesAreMissing() throws Exception {
        testObj.parseFrom("PASSPORT GBR");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidNumber() throws Exception {
        testObj.parseFrom("PASSPORT GBR $%^$%^£ 2016-05-01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidCountry() throws Exception {
        testObj.parseFrom("PASSPORT 13 1234abc 2016-05-01");
    }

    @Test
    public void shouldParseMandatoryAttributes() throws Exception {
        DocumentDetails result = testObj.parseFrom("PASSPORT GBR 1234abc");

        assertNotNull(result);
        assertEquals(DocumentDetails.DOCUMENT_TYPE_PASSPORT, result.getType());
        assertEquals("GBR", result.getIssuingCountry());
        assertEquals("1234abc", result.getDocumentNumber());
        assertNull(result.getExpirationDate());
        assertNull(result.getIssuingAuthority());
    }

    @Test
    public void shouldParseOneOptionalAttribute() throws Exception {
        DocumentDetails result = testObj.parseFrom("AADHAAR IND 1234abc 2016-05-01");

        assertNotNull(result);
        assertEquals(DocumentDetails.DOCUMENT_TYPE_AADHAAR, result.getType());
        assertEquals("IND", result.getIssuingCountry());
        assertEquals("1234abc", result.getDocumentNumber());
        assertEquals("2016-05-01", result.getExpirationDate().toString());
        assertNull(result.getIssuingAuthority());
    }

    @Test
    public void shouldParseTwoOptionalAttributes() throws Exception {
        DocumentDetails result = testObj.parseFrom("DRIVING_LICENCE GBR 1234abc 2016-05-01 DVLA");

        assertNotNull(result);
        assertEquals(DocumentDetails.DOCUMENT_TYPE_DRIVING_LICENCE, result.getType());
        assertEquals("GBR", result.getIssuingCountry());
        assertEquals("1234abc", result.getDocumentNumber());
        assertEquals("2016-05-01", result.getExpirationDate().toString());
        assertEquals("DVLA", result.getIssuingAuthority());
    }

    @Test
    public void shouldParseWhenOneOptionalAttributeIsMissing() throws Exception {
        DocumentDetails result = testObj.parseFrom("PASS_CARD GBR 1234abc - DVLA");

        assertNotNull(result);
        assertEquals(DocumentDetails.DOCUMENT_TYPE_PASS_CARD, result.getType());
        assertEquals("GBR", result.getIssuingCountry());
        assertEquals("1234abc", result.getDocumentNumber());
        assertNull(result.getExpirationDate());
        assertEquals("DVLA", result.getIssuingAuthority());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionForInvalidDate() throws Exception {
        testObj.parseFrom("PASSPORT GBR 1234abc" + " X016-05-01");
    }

}
