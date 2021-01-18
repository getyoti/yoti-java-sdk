package com.yoti.api.client;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.yoti.api.client.spi.remote.util.Validation;

/**
 * Represents a point in UTC time, to the nearest microsecond
 *
 */
public class DateTime {

    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

    private final Date date;
    private final Time time;

    public DateTime(Date date, Time time) {
        Validation.notNull(date, "date");
        Validation.notNull(time, "time");
        this.date = date;
        this.time = time;
    }

    public static DateTime from(long microseconds) {
        Calendar calendar = GregorianCalendar.getInstance(UTC_TIME_ZONE);
        int mod = (int) (microseconds % 1000);
        long milliseconds = (microseconds - mod) / 1000;
        calendar.setTimeInMillis(milliseconds);
        return new DateTime(Date.from(calendar), Time.from(calendar, mod));
    }

    public static DateTime from(String dateTimeStringValue) throws DateTimeParseException {
        OffsetDateTime zonedDateTime = OffsetDateTime.parse(dateTimeStringValue).truncatedTo(ChronoUnit.MICROS);
        return DateTime.from(ChronoUnit.MICROS.between(Instant.EPOCH, zonedDateTime.toInstant()));
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("DateTime{date=%s,time=%s}", date, time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DateTime that = (DateTime) o;

        return date.equals(that.getDate()) && time.equals(that.getTime());
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

}
