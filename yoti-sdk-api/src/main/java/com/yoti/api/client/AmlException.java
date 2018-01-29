package com.yoti.api.client;

/**
 * Signals that a problem occurred while performing an AML check
 */
public class AmlException extends Exception {

    private static final long serialVersionUID = 3210133542178934016L;

    public AmlException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
