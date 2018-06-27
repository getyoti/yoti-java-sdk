package com.yoti.api.client;

/**
 * Represents a point in UTC time, to the nearest microsecond
 *
 */
public interface DateTime {

    Date getDate();

    Time getTime();

}
