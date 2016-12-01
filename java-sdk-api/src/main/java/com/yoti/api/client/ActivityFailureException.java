package com.yoti.api.client;

/**
 * Signals activity failure -- ie. there was a problem during sharing.
 *
 */
public class ActivityFailureException extends ProfileException {
    private static final long serialVersionUID = 4496989849870743641L;

    public ActivityFailureException(String message) {
        super(message);
    }
}
