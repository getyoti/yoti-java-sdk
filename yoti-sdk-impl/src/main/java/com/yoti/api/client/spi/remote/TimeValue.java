package com.yoti.api.client.spi.remote;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

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

}
