package com.yoti.api.client.spi.remote;

import com.yoti.api.client.DateTime;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class DateTimeValueTest {

    @Test
    public void constructor_willNotAllowNullDate() {
        try {
            new DateTimeValue(null, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("date"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void constructor_willNotAllowNullTime() throws Exception {
        try {
            new DateTimeValue(DateValue.parseFrom("1984-10-31"), null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("time"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void from_shouldWorkForEpochBeginning() {
        DateTime result = DateTimeValue.from(0);

        assertEquals(1970, result.getDate().getYear());
        assertEquals(1, result.getDate().getMonth());
        assertEquals(1, result.getDate().getDay());

        assertEquals(0, result.getTime().getHour());
        assertEquals(0, result.getTime().getMinute());
        assertEquals(0, result.getTime().getSecond());
        assertEquals(0, result.getTime().getMicrosecond());
    }

    @Test
    public void from_shouldWorkForAnActualTimeWithMicros() throws Exception {
        TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(utcTimeZone);
        Calendar calendar = GregorianCalendar.getInstance(utcTimeZone);
        calendar.setTime(simpleDateFormat.parse("1980-Aug-05 13:15:45"));
        long microseconds = calendar.getTimeInMillis() * 1000;
        microseconds = microseconds + 123456;

        DateTime result = DateTimeValue.from(microseconds);

        assertEquals(1980, result.getDate().getYear());
        assertEquals(8, result.getDate().getMonth());
        assertEquals(5, result.getDate().getDay());

        assertEquals(13, result.getTime().getHour());
        assertEquals(15, result.getTime().getMinute());
        assertEquals(45, result.getTime().getSecond());
        assertEquals(123456, result.getTime().getMicrosecond());
    }

    @Test
    public void equals_returnsFalseWhenComparedToNull() {
        DateTime dateTime = DateTimeValue.from(123456789);

        assertFalse(dateTime.equals(null));
    }

    @Test
    public void equals_shouldBeReflexive() {
        DateTime dateTime = DateTimeValue.from(123456789);

        assertTrue(dateTime.equals(dateTime));
        assertTrue(dateTime.hashCode() == dateTime.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesMatch() {
        DateTime dateTime1 = DateTimeValue.from(123456789);
        DateTime dateTime2 = DateTimeValue.from(123456789);

        assertTrue(dateTime1.equals(dateTime2));
        assertTrue(dateTime2.equals(dateTime1));
        assertTrue(dateTime1.hashCode() == dateTime2.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesDoNotMatch() {
        DateTime dateTime1 = DateTimeValue.from(987654321);
        DateTime dateTime2 = DateTimeValue.from(123456789);

        assertFalse(dateTime1.equals(dateTime2));
        assertFalse(dateTime2.equals(dateTime1));
    }

    @Test
    public void equals_shouldBeTransitive() {
        DateTime dateTime1 = DateTimeValue.from(123456789);
        DateTime dateTime2 = DateTimeValue.from(123456789);
        DateTime dateTime3 = DateTimeValue.from(123456789);

        assertTrue(dateTime1.equals(dateTime2));
        assertTrue(dateTime1.equals(dateTime3));
        assertTrue(dateTime2.equals(dateTime3));
        assertTrue(dateTime1.hashCode() == dateTime2.hashCode());
        assertTrue(dateTime2.hashCode() == dateTime3.hashCode());
    }

    public static void assertDateTimeValue(DateTime test,
                                               int year,
                                               int month,
                                               int day,
                                               int hour,
                                               int minute,
                                               int second,
                                               int microseconds) {

        assertEquals(year, test.getDate().getYear());
        assertEquals(month, test.getDate().getMonth());
        assertEquals(day, test.getDate().getDay());

        assertEquals(hour, test.getTime().getHour());
        assertEquals(minute, test.getTime().getMinute());
        assertEquals(second, test.getTime().getSecond());
        assertEquals(microseconds, test.getTime().getMicrosecond());
    }

}
