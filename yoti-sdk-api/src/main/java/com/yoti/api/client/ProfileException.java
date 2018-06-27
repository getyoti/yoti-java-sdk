package com.yoti.api.client;

/**
 * Signals an issue during profile retrieval
 *
 */
public class ProfileException extends Exception {
    private static final long serialVersionUID = 1440096271615095912L;

    public ProfileException(String message) {
        super(message);
    }

    public ProfileException(String message, Throwable cause) {
        super(message, cause);
    }
}
