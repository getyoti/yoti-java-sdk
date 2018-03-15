package com.yoti.api.client.sandbox.profile.request.attribute;

import static java.lang.String.format;

public final class SandboxDateAttribute implements com.yoti.api.client.Date {

    private final int year;
    private final int month;
    private final int day;

    public SandboxDateAttribute(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
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

}