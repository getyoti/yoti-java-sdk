package com.yoti.api.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class DocumentDetailsTest {

    private static final String SOME_TYPE = "someType";
    private static final String SOME_COUNTRY = "someCountry";
    private static final String SOME_NUMBER = "someNumber";
    private static final String SOME_DATE_STRING = "2011-11-30";
    private static final String SOME_AUTHORITY = "someAuthority";

    private DocumentDetails testObj;
    private Date testDate;

    @Before
    public void setUp() throws Exception {
        testDate = Date.parseFrom(SOME_DATE_STRING);
    }

    @Test
    public void shouldBuildCorrectStringForMandatoryAttributes() {
        testObj = new DocumentDetails(SOME_TYPE, SOME_COUNTRY, SOME_NUMBER, null, null);

        String result = testObj.toString();

        assertEquals(join(SOME_TYPE, SOME_COUNTRY, SOME_NUMBER), result);
    }

    @Test
    public void shouldBuildCorrectStringWithDocumentDate() {
        testObj = new DocumentDetails(SOME_TYPE, SOME_COUNTRY, SOME_NUMBER, testDate, null);

        String result = testObj.toString();

        assertEquals(join(SOME_TYPE, SOME_COUNTRY, SOME_NUMBER, SOME_DATE_STRING), result);
    }

    @Test
    public void shouldBuildCorrectStringWithDocumentDateAndAuthority() {
        testObj = new DocumentDetails(SOME_TYPE, SOME_COUNTRY, SOME_NUMBER, testDate, SOME_AUTHORITY);

        String result = testObj.toString();

        assertEquals(join(SOME_TYPE, SOME_COUNTRY, SOME_NUMBER, SOME_DATE_STRING, SOME_AUTHORITY), result);
    }

    @Test
    public void shouldBuildCorrectStringWhenDateIsAbsent() {
        testObj = new DocumentDetails(SOME_TYPE, SOME_COUNTRY, SOME_NUMBER, null, SOME_AUTHORITY);

        String result = testObj.toString();

        assertEquals(join(SOME_TYPE, SOME_COUNTRY, SOME_NUMBER, "-", SOME_AUTHORITY), result);
    }

    private static String join(String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        String delim = "";
        for (String string : args) {
            stringBuilder.append(delim);
            stringBuilder.append(string);
            delim = " ";
        }
        return stringBuilder.toString();
    }

    @Test
    public void shouldFailWhenTypeIsMissing() {
        try {
            DocumentDetails.builder()
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("type"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWhenCountryIsMissing() {
        try {
            DocumentDetails.builder()
                    .withType(SOME_TYPE)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("issuingCountry"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void shouldFailWhenNumberIsMissing() {
        try {
            DocumentDetails.builder()
                    .withType(SOME_TYPE)
                    .withIssuingCountry(SOME_COUNTRY)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("documentNumber"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void shouldCreateDocumentDetailsWithMandatoryAttributes() {
        DocumentDetails result = DocumentDetails.builder()
                .withType(SOME_TYPE)
                .withIssuingCountry(SOME_COUNTRY)
                .withNumber(SOME_NUMBER)
                .build();

        assertEquals(SOME_TYPE, result.getType());
        assertEquals(SOME_COUNTRY, result.getIssuingCountry());
        assertEquals(SOME_NUMBER, result.getDocumentNumber());
    }

    @Test
    public void shouldCreateDocumentDetailsWithAllAttributes() throws Exception {
        DocumentDetails result = DocumentDetails.builder()
                .withType(SOME_TYPE)
                .withIssuingCountry(SOME_COUNTRY)
                .withNumber(SOME_NUMBER)
                .withDate(Date.parseFrom(SOME_DATE_STRING))
                .withAuthority(SOME_AUTHORITY)
                .build();

        assertEquals(SOME_TYPE, result.getType());
        assertEquals(SOME_COUNTRY, result.getIssuingCountry());
        assertEquals(SOME_NUMBER, result.getDocumentNumber());
        assertEquals(SOME_DATE_STRING, result.getExpirationDate().toString());
        assertEquals(SOME_AUTHORITY, result.getIssuingAuthority());
    }

}
