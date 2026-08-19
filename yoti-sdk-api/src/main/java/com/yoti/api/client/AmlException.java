package com.yoti.api.client;

/**
 * Signals that a problem occurred while performing an AML check
 *
 * @deprecated The AML check service has been discontinued and this class is no longer used.
 *             This API will be removed in the next major release.
 */
@Deprecated
public class AmlException extends Exception {

    private static final long serialVersionUID = 3210133542178934016L;

    public AmlException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
