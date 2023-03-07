package com.yoti.api.client.spi.remote.call.identity;

public class DigitalIdentityException extends RuntimeException {

    public DigitalIdentityException() {
        super();
    }

    public DigitalIdentityException(String message) {
        super(message);
    }

    public DigitalIdentityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DigitalIdentityException(Throwable cause) {
        super(cause);
    }

}
