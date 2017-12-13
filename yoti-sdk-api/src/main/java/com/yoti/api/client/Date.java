package com.yoti.api.client;

/**
 * Represents a day in time.
 *
 */
public interface Date {
    int getYear();

    int getMonth();

    int getDay();
    
    String toString();
}