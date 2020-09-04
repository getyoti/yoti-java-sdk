package com.yoti.api.client.docs.session.create.check;

/**
 * Builder to assist the creation of {@link RequestedDocumentAuthenticityCheck}.
 */
public interface RequestedDocumentAuthenticityCheckBuilder {

    /**
     * Requires that a manual follow-up check is always performed
     *
     * @return the builder
     */
    RequestedDocumentAuthenticityCheckBuilder withManualCheckAlways();

    /**
     * Requires that a manual follow-up check is performed only on failed Checks, and those with a low level of confidence
     *
     * @return the builder
     */
    RequestedDocumentAuthenticityCheckBuilder withManualCheckFallback();

    /**
     * Requires that only an automated Check is performed.  No manual follow-up Check will ever be initiated
     *
     * @return the builder
     */
    RequestedDocumentAuthenticityCheckBuilder withManualCheckNever();

    RequestedDocumentAuthenticityCheck build();

}
