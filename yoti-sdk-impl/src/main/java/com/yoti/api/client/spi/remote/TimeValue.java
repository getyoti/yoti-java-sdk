package com.yoti.api.client.spi.remote;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import static com.yoti.api.client.spi.remote.util.Validation.notGreaterThan;

import java.util.Calendar;

import com.yoti.api.client.Time;

public class TimeValue implements Time {

    private final int hour;
    private final int minute;
    private final int second;
    private final int microsecond;

    private TimeValue(int hour, int minute, int second, int microsecond) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.microsecond = microsecond;
    }

    public static TimeValue from(Calendar calendar, int microsecond) {
        notGreaterThan(microsecond, 999, "microsecond");

        return new TimeValue(calendar.get(HOUR_OF_DAY),
                calendar.get(MINUTE),
                calendar.get(SECOND),
                (calendar.get(MILLISECOND) * 1000) + microsecond);
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public int getSecond() {
        return second;
    }

    @Override
    public int getMicrosecond() {
        return microsecond;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d.%06d", hour, minute, second, microsecond);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimeValue timeValue = (TimeValue) o;

        if (hour != timeValue.hour) {
            return false;
        }
        if (minute != timeValue.minute) {
            return false;
        }
        if (second != timeValue.second) {
            return false;
        }
        return microsecond == timeValue.microsecond;
    }

    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + minute;
        result = 31 * result + second;
        result = 31 * result + microsecond;
        return result;
    }

}
