package com.yoti.api.client;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import static com.yoti.api.client.spi.remote.util.Validation.notGreaterThan;

import java.util.Calendar;

import com.yoti.api.client.spi.remote.util.Validation;

/**
 * UTC time of day, to the nearest microsecond
 */
public class Time {

    private final int hour;
    private final int minute;
    private final int second;
    private final int microsecond;

    private Time(int hour, int minute, int second, int microsecond) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.microsecond = microsecond;
    }

    public static Time from(Calendar calendar, int microsecond) {
        notGreaterThan(microsecond, 999, "microsecond");

        return new Time(calendar.get(HOUR_OF_DAY),
                calendar.get(MINUTE),
                calendar.get(SECOND),
                (calendar.get(MILLISECOND) * 1000) + microsecond);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

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

        Time timeValue = (Time) o;

        if (hour != timeValue.getHour()) {
            return false;
        }
        if (minute != timeValue.getMinute()) {
            return false;
        }
        if (second != timeValue.getSecond()) {
            return false;
        }
        return microsecond == timeValue.getMicrosecond();
    }

    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + minute;
        result = 31 * result + second;
        result = 31 * result + microsecond;
        return result;
    }

    public static Time.TimeBuilder builder() {
        return new Time.TimeBuilder();
    }

    public static class TimeBuilder {

        private int hour;
        private int minute;
        private int second;
        private int microsecond;

        private TimeBuilder() {
        }

        public Time.TimeBuilder withHour(int hour) {
            Validation.withinRange(hour, 0, 23, "hour");
            this.hour = hour;
            return this;
        }

        public Time.TimeBuilder withMinute(int minute) {
            Validation.withinRange(minute, 0, 59, "minute");
            this.minute = minute;
            return this;
        }

        public Time.TimeBuilder withSecond(int second) {
            Validation.withinRange(second, 0, 59, "second");
            this.second = second;
            return this;
        }

        public Time.TimeBuilder withMicrosecond(int microsecond) {
            Validation.withinRange(microsecond, 0, 999999, "microsecond");
            this.microsecond = microsecond;
            return this;
        }

        public Time build() {
            return new Time(hour, minute, second, microsecond);
        }

    }

}
