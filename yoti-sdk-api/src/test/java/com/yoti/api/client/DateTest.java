package com.yoti.api.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.yoti.api.client.Date;

import org.junit.Test;

public class DateTest {

    private static final String VALID_DATE_STRING = "2016-12-01";
    private static final String INVALID_DATE_VALUE_STRING = "2016-13-01";
    private static final String INVALID_DATE_FORMAT_STRING = "2016-DEC-01";

    @Test
    public void from_shouldParseFromCalendar() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss:SSS");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(simpleDateFormat.parse("2016-Dec-01 14:59:49:123"));

        Date result = Date.from(calendar);

        assertDate(result, 2016, 12, 1);
    }

    @Test
    public void parseFrom_shouldParseValidString() throws Exception {
        Date result = Date.parseFrom(VALID_DATE_STRING);

        assertDate(result, 2016, 12, 1);
    }

    @Test
    public void parseFrom_shouldParseValidArray() throws Exception {
        Date result = Date.parseFrom(VALID_DATE_STRING.getBytes("UTF-8"));

        assertDate(result, 2016, 12, 1);
    }

    @Test(expected = ParseException.class)
    public void parseFrom_shouldNotParseInvalidDateFormat() throws Exception {
        Date.parseFrom(INVALID_DATE_FORMAT_STRING);
    }

    @Test(expected = ParseException.class)
    public void parseFrom_shouldNotParseInvalidDate() throws Exception {
        Date.parseFrom(INVALID_DATE_VALUE_STRING);
    }

    @Test
    public void builder_shouldNotAllowYearZero() {
        try {
            Date.builder()
                    .withYear(0);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("year"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldApplyMinLimitToMonth() {
        try {
            Date.builder()
                    .withMonth(0);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("month"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldApplyMaxLimitToMonth() {
        try {
            Date.builder()
                    .withMonth(13);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("month"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldApplyMinLimitToDay() {
        try {
            Date.builder()
                    .withDay(0);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("day"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldApplyMaxLimitToDay() {
        try {
            Date.builder()
                    .withDay(32);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("day"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldFailForAnInvalidDate() throws Exception {
        try {
            Date.builder()
                    .withYear(2015)
                    .withMonth(2)
                    .withDay(29)
                    .build();
        } catch (IllegalStateException e) {
            assertTrue(e.getCause() instanceof ParseException);
            assertThat(e.getMessage(), containsString("2015-2-29"));
            assertThat(e.getMessage(), containsString(e.getCause().getMessage()));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldBuildDateWithDefaultValues() throws Exception {
        Date result = Date.builder()
                .build();

        assertEquals(1, result.getYear());
        assertEquals(1, result.getMonth());
        assertEquals(1, result.getDay());
    }

    @Test
    public void builder_shouldBuildDateWithGivenValues() throws Exception {
        Date result = Date.builder()
                .withYear(1999)
                .withMonth(12)
                .withDay(31)
                .build();

        assertEquals(1999, result.getYear());
        assertEquals(12, result.getMonth());
        assertEquals(31, result.getDay());
    }

    @Test
    public void toString_shouldFormatDateCorrectly() throws Exception {
        Date date = Date.parseFrom(VALID_DATE_STRING);

        String result = date.toString();

        assertEquals(VALID_DATE_STRING, result);
    }

    @Test
    public void equals_returnsFalseWhenComparedToNull() throws Exception {
        Date dateValue = Date.parseFrom(VALID_DATE_STRING);

        assertFalse(dateValue.equals(null));
    }

    @Test
    public void equals_shouldBeReflexive() throws Exception {
        Date dateValue = Date.parseFrom(VALID_DATE_STRING);

        assertTrue(dateValue.equals(dateValue));
        assertTrue(dateValue.hashCode() == dateValue.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesMatch() throws Exception {
        Date date1 = Date.parseFrom(VALID_DATE_STRING);
        Date date2 = Date.parseFrom(VALID_DATE_STRING);

        assertTrue(date1.equals(date2));
        assertTrue(date2.equals(date1));
        assertTrue(date1.hashCode() == date2.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesDoNotMatch() throws Exception {
        Date dateTime1 = Date.parseFrom(VALID_DATE_STRING);
        Date dateTime2 = Date.parseFrom("2017-03-31");

        assertFalse(dateTime1.equals(dateTime2));
        assertFalse(dateTime2.equals(dateTime1));
    }

    @Test
    public void equals_shouldBeTransitive() throws Exception {
        Date dateTime1 = Date.parseFrom(VALID_DATE_STRING);
        Date dateTime2 = Date.parseFrom(VALID_DATE_STRING);
        Date dateTime3 = Date.parseFrom(VALID_DATE_STRING);

        assertTrue(dateTime1.equals(dateTime2));
        assertTrue(dateTime1.equals(dateTime3));
        assertTrue(dateTime2.equals(dateTime3));
        assertTrue(dateTime1.hashCode() == dateTime2.hashCode());
        assertTrue(dateTime2.hashCode() == dateTime3.hashCode());
    }

    private static void assertDate(Date actualDate, int expectedYear, int expectedMonth, int expectedDay) {
        assertEquals(expectedYear, actualDate.getYear());
        assertEquals(expectedMonth, actualDate.getMonth());
        assertEquals(expectedDay, actualDate.getDay());
    }

}
