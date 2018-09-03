package com.yoti.api.client.spi.remote;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

public class TimeValueTest {

    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

    private Calendar calendar;

    @Before
    public void setUp() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss:SSS");
        simpleDateFormat.setTimeZone(UTC_TIME_ZONE);
        calendar = GregorianCalendar.getInstance(UTC_TIME_ZONE);
        calendar.setTime(simpleDateFormat.parse("1980-Aug-05 13:15:45:123"));
    }

    @Test
    public void from_createsValueSuccessfully() {
        TimeValue result = TimeValue.from(calendar, 999);

        assertEquals(13, result.getHour());
        assertEquals(15, result.getMinute());
        assertEquals(45, result.getSecond());
        assertEquals(123999, result.getMicrosecond());
    }

    @Test
    public void from_ensuresMicrosecondsValueNotGreaterThan999() {
        try {
            TimeValue.from(calendar, 1000);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("microsecond"));
            assertThat(e.getMessage(), containsString("1000"));
            assertThat(e.getMessage(), containsString("999"));
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void builder_appliesMinLimitOnHour() {
        try {
            TimeValue.builder().withHour(-1);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("hour"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_appliesMaxLimitOnHour() {
        try {
            TimeValue.builder().withHour(24);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("hour"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_appliesMinLimitOnMinute() {
        try {
            TimeValue.builder().withMinute(-1);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("minute"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_appliesMaxLimitOnMinute() {
        try {
            TimeValue.builder().withMinute(60);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("minute"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_appliesMinLimitOnSecond() {
        try {
            TimeValue.builder().withSecond(-1);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("second"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_appliesMaxLimitOnSecond() {
        try {
            TimeValue.builder().withSecond(60);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("second"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_appliesMinLimitOnMicrosecond() {
        try {
            TimeValue.builder().withMicrosecond(-1);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("microsecond"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_appliesMaxLimitOnMicrosecond() {
        try {
            TimeValue.builder().withMicrosecond(1000000);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("microsecond"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_buildsATimeValueWithDefaults() {
        TimeValue result = TimeValue.builder().build();

        assertEquals(0, result.getHour());
        assertEquals(0, result.getMinute());
        assertEquals(0, result.getSecond());
        assertEquals(0, result.getMicrosecond());
    }

    @Test
    public void builder_buildsATimeValueWithGivenValues() {
        TimeValue result = TimeValue.builder()
                .withHour(1)
                .withMinute(2)
                .withSecond(3)
                .withMicrosecond(4)
                .build();

        assertEquals(1, result.getHour());
        assertEquals(2, result.getMinute());
        assertEquals(3, result.getSecond());
        assertEquals(4, result.getMicrosecond());
    }

    @Test
    public void toString_shouldFormatDateCorrectly() {
        TimeValue timeValue = TimeValue.from(calendar, 999);

        String result = timeValue.toString();

        assertEquals("13:15:45.123999", result);
    }

    @Test
    public void equals_returnsFalseWhenComparedToNull() {
        TimeValue timeValue = TimeValue.from(calendar, 999);

        assertFalse(timeValue.equals(null));
    }

    @Test
    public void equals_shouldBeReflexive() {
        TimeValue timeValue = TimeValue.from(calendar, 999);

        assertTrue(timeValue.equals(timeValue));
        assertTrue(timeValue.hashCode() == timeValue.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesMatch() {
        TimeValue timeValue1 = TimeValue.from(calendar, 999);
        TimeValue timeValue2 = TimeValue.from(calendar, 999);

        assertTrue(timeValue1.equals(timeValue2));
        assertTrue(timeValue2.equals(timeValue1));
        assertTrue(timeValue1.hashCode() == timeValue2.hashCode());
    }

    @Test
    public void equals_shouldBeSymmetricWhenValuesDoNotMatch() {
        TimeValue timeValue1 = TimeValue.from(calendar, 999);
        TimeValue timeValue2 = TimeValue.from(calendar, 123);

        assertFalse(timeValue1.equals(timeValue2));
        assertFalse(timeValue2.equals(timeValue1));
    }

    @Test
    public void equals_shouldBeTransitive() {
        TimeValue timeValue1 = TimeValue.from(calendar, 999);
        TimeValue timeValue2 = TimeValue.from(calendar, 999);
        TimeValue timeValue3 = TimeValue.from(calendar, 999);

        assertTrue(timeValue1.equals(timeValue2));
        assertTrue(timeValue1.equals(timeValue3));
        assertTrue(timeValue2.equals(timeValue3));
        assertTrue(timeValue1.hashCode() == timeValue2.hashCode());
        assertTrue(timeValue2.hashCode() == timeValue3.hashCode());
    }

}
