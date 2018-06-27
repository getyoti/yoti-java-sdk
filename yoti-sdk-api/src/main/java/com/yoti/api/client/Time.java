package com.yoti.api.client;

/**
 * UTC time of day, to the nearest microsecond
 */
public interface Time {

    int getHour();

    int getMinute();

    int getSecond();

    int getMicrosecond();

}
