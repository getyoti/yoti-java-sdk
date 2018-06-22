package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.yoti.api.client.DateTime;

import org.junit.Test;

public class DateTimeValueTest {

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
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
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

}
