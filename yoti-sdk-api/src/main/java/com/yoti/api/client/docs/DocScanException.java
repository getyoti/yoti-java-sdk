package com.yoti.api.client.docs;

/**
 * Signals that a problem occurred in a Yoti Doc Scan call
 */
public class DocScanException extends Exception {

    public DocScanException(String message) {
        super(message);
    }

    public DocScanException(String message, Throwable cause) {
        super(message, cause);
    }

}
