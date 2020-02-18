package com.yoti.api.client.docs.session.create.check;

/**
 * Builder to assist creation of {@link RequestedFaceMatchCheck}.
 */
public interface RequestedFaceMatchCheckBuilder {

    /**
     * Requires that a manual follow-up check is always performed
     *
     * @return the builder
     */
    RequestedFaceMatchCheckBuilder withManualCheckAlways();

    /**
     * Requires that a manual follow-up check is performed only on failed Checks, and those with a low level of confidence
     *
     * @return the builder
     */
    RequestedFaceMatchCheckBuilder withManualCheckFallback();

    /**
     * Requires that only an automated Check is performed.  No manual follow-up Check will ever be initiated
     *
     * @return the builder
     */
    RequestedFaceMatchCheckBuilder withManualCheckNever();

    RequestedFaceMatchCheck build();

}
