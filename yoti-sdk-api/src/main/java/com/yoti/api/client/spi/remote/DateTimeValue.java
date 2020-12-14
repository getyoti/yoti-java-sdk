package com.yoti.api.client.spi.remote;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.yoti.api.client.Date;
import com.yoti.api.client.DateTime;
import com.yoti.api.client.Time;
import com.yoti.api.client.spi.remote.util.Validation;

public class DateTimeValue implements DateTime {

    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

    private final Date date;
    private final Time time;

    public DateTimeValue(Date date, Time time) {
        Validation.notNull(date, "date");
        Validation.notNull(time, "time");
        this.date = date;
        this.time = time;
    }

    public static DateTimeValue from(long microseconds) {
        Calendar calendar = GregorianCalendar.getInstance(UTC_TIME_ZONE);
        int mod = (int) (microseconds % 1000);
        long milliseconds = (microseconds - mod) / 1000;
        calendar.setTimeInMillis(milliseconds);
        return new DateTimeValue(DateValue.from(calendar), TimeValue.from(calendar, mod));
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("DateTimeValue{date=%s,time=%s}", date, time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DateTimeValue that = (DateTimeValue) o;

        return date.equals(that.date) && time.equals(that.time);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

}
