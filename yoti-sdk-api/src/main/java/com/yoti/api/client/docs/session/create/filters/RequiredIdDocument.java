package com.yoti.api.client.docs.session.create.filters;

/**
 * Defines an identity document to be provided by the user
 */
public interface RequiredIdDocument extends RequiredDocument {

    /**
     * Filter for the required document
     *
     * @return the filter
     */
    DocumentFilter getFilter();

}
