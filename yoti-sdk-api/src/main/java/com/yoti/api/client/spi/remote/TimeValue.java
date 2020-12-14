package com.yoti.api.client.spi.remote;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import static com.yoti.api.client.spi.remote.util.Validation.notGreaterThan;

import java.util.Calendar;

import com.yoti.api.client.Time;
import com.yoti.api.client.spi.remote.util.Validation;

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

    public static TimeValueBuilder builder() {
        return new TimeValueBuilder();
    }

    public static class TimeValueBuilder {

        private int hour;
        private int minute;
        private int second;
        private int microsecond;

        private TimeValueBuilder() {
        }

        public TimeValueBuilder withHour(int hour) {
            Validation.withinRange(hour, 0, 23, "hour");
            this.hour = hour;
            return this;
        }

        public TimeValueBuilder withMinute(int minute) {
            Validation.withinRange(minute, 0, 59, "minute");
            this.minute = minute;
            return this;
        }

        public TimeValueBuilder withSecond(int second) {
            Validation.withinRange(second, 0, 59, "second");
            this.second = second;
            return this;
        }

        public TimeValueBuilder withMicrosecond(int microsecond) {
            Validation.withinRange(microsecond, 0, 999999, "microsecond");
            this.microsecond = microsecond;
            return this;
        }

        public TimeValue build() {
            return new TimeValue(hour, minute, second, microsecond);
        }

    }

}
