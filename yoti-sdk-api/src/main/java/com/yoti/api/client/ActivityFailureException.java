package com.yoti.api.client;

import java.util.Optional;

import com.yoti.api.client.spi.remote.call.ErrorDetails;
import com.yoti.api.client.spi.remote.call.ProfileResponse;

/**
 * Signals activity failure -- ie. there was a problem during sharing.
 */
public class ActivityFailureException extends ProfileException {

    private static final long serialVersionUID = 4496989849870743641L;

    private static final String ERROR_MSG = "Sharing activity unsuccessful for %s%s";

    private final ErrorDetails errorDetails;

    public ActivityFailureException(ProfileResponse profile) {
        super(
                String.format(
                        ERROR_MSG,
                        profile.getReceipt().getDisplayReceiptId(),
                        Optional.ofNullable(profile.getError())
                                .map(error -> String.format(" - %s", error))
                                .orElse("")
                )
        );
        errorDetails = profile.getError();
    }

    public ErrorDetails errorDetails() {
        return errorDetails;
    }

}
