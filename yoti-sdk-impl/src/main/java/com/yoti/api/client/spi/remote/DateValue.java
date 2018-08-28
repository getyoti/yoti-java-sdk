package com.yoti.api.client.spi.remote;

import static java.lang.String.format;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.yoti.api.client.Date;

/**
 * Attribute value holding a year/month/day tuple.
 *
 */
public final class DateValue implements Date {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private final int year;
    private final int month;
    private final int day;

    private DateValue(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static DateValue from(Calendar calendar) {
        return new DateValue(calendar.get(YEAR), calendar.get(MONTH) + 1, calendar.get(DAY_OF_MONTH));
    }

    public static DateValue parseFrom(byte[] content) throws UnsupportedEncodingException, ParseException {
        String source = new String(content, DEFAULT_CHARSET);
        return parseFrom(source);
    }

    public static DateValue parseFrom(String source) throws UnsupportedEncodingException, ParseException {
        Calendar calendar = parseDate(source);
        return from(calendar);
    }

    private static Calendar parseDate(String source) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        simpleDateFormat.setLenient(false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(source));
        return calendar;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return format("%04d-%02d-%02d", year, month, day);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DateValue dateValue = (DateValue) o;

        if (year != dateValue.year) {
            return false;
        }
        if (month != dateValue.month) {
            return false;
        }
        return day == dateValue.day;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        return result;
    }

}
