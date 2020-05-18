package com.yoti.api.client.docs.session.create.filters;

/**
 * Filter for a required document, allowing specification
 * of restrictive parameters
 */
public interface DocumentFilter {

    /**
     * The type of the filter
     *
     * @return the type
     */
    String getType();

}
