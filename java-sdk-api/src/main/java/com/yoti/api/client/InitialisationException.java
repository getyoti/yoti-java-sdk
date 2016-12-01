package com.yoti.api.client;

/**
 * Signals an issue initialising the client
 */
public class InitialisationException extends RuntimeException {

    public InitialisationException(String message) {
        super(message);
    }

    public InitialisationException(String message, Throwable cause) {
        super(message, cause);
    }
}
