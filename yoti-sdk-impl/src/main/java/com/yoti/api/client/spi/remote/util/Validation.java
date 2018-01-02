package com.yoti.api.client.spi.remote.util;

public class Validation {

    public static <T> T notNull(T value, String item) {
        if (value == null) {
            throw new IllegalArgumentException(item + " must not be null.");
        }
        return value;
    }

}
