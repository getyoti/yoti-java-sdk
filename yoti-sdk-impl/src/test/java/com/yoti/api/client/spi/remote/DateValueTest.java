package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.yoti.api.client.Date;

import org.junit.Test;

public class DateValueTest {

    private static final String VALID_DATE_STRING = "2016-12-01";
    private static final String INVALID_DATE_VALUE_STRING = "2016-13-01";
    private static final String INVALID_DATE_FORMAT_STRING = "2016-DEC-01";

    @Test
    public void from_shouldParseFromCalendar() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss:SSS");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(simpleDateFormat.parse("2016-Dec-01 14:59:49:123"));

        DateValue result = DateValue.from(calendar);

        assertDate(result);
    }

    @Test
    public void parseFrom_shouldParseValidString() throws UnsupportedEncodingException, ParseException {
        DateValue result = DateValue.parseFrom(VALID_DATE_STRING);

        assertDate(result);
    }

    @Test
    public void parseFrom_shouldParseValidArray() throws UnsupportedEncodingException, ParseException {
        DateValue result = DateValue.parseFrom(VALID_DATE_STRING.getBytes("UTF-8"));

        assertDate(result);
    }

    @Test(expected = ParseException.class)
    public void parseFrom_shouldNotParseInvalidDateFormat() throws UnsupportedEncodingException, ParseException {
        DateValue.parseFrom(INVALID_DATE_FORMAT_STRING);
    }

    @Test(expected = ParseException.class)
    public void parseFrom_shouldNotParseInvalidDateValue() throws UnsupportedEncodingException, ParseException {
        DateValue.parseFrom(INVALID_DATE_VALUE_STRING);
    }

    @Test
    public void toString_shouldFormatDateCorrectly() throws UnsupportedEncodingException, ParseException {
        DateValue date = DateValue.parseFrom(VALID_DATE_STRING);

        String result = date.toString();

        assertEquals(VALID_DATE_STRING, result);
    }

    private void assertDate(Date date) {
        assertEquals(2016, date.getYear());
        assertEquals(12, date.getMonth());
        assertEquals(1, date.getDay());
    }

}
