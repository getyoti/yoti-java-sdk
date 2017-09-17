package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.yoti.api.client.Date;

public class DateAttributeValueTest {

    private static final String VALID_DATE_STRING = "2016-05-01";
    private byte[] VALID_DATE_BYTES;
    private static final String INVALID_DATE_VALUE_STRING = "2016-13-01";
    private static final String INVALID_DATE_FORMAT_STRING = "2016-MAR-01";

    @Before
    public void before() throws UnsupportedEncodingException {
        VALID_DATE_BYTES = VALID_DATE_STRING.getBytes("UTF-8");
    }

    @Test
    public void shouldParseValidString() throws UnsupportedEncodingException, ParseException {
        DateAttributeValue date = DateAttributeValue.parseFrom(VALID_DATE_STRING);
        assertDate(date);
    }

    @Test
    public void shouldParseValidArray() throws UnsupportedEncodingException, ParseException {
        DateAttributeValue date = DateAttributeValue.parseFrom(VALID_DATE_BYTES);
        assertDate(date);
    }

    @Test(expected = ParseException.class)
    public void shouldNotParseInvalidDateFormat() throws UnsupportedEncodingException, ParseException {
        DateAttributeValue.parseFrom(INVALID_DATE_FORMAT_STRING);
    }

    @Test(expected = ParseException.class)
    public void shouldNotParseInvalidDateValue() throws UnsupportedEncodingException, ParseException {
        Date date = DateAttributeValue.parseFrom(INVALID_DATE_VALUE_STRING);
    }

    private void assertDate(Date date) {
        assertEquals(2016, date.getYear());
        assertEquals(5, date.getMonth());
        assertEquals(1, date.getDay());
    }
}
