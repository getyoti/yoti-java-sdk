package com.yoti.api.client;

public class ExtraDataException extends Exception {

    private static final long serialVersionUID = 1928058788889981507L;

    public ExtraDataException(String message) {
        super(message);
    }

    public ExtraDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
