package com.yoti.api.client.docs.session.create.filters;

/**
 * Defines a document to be provided by the user
 */
public interface RequiredDocument {

    /**
     * The type of the required document
     *
     * @return the type
     */
    String getType();

}
