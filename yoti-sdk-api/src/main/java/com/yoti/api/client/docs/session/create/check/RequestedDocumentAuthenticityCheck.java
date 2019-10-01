package com.yoti.api.client.docs.session.create.check;

/**
 * Requests creation of a DocumentAuthenticityCheck
 *
 * @param <T> class extending {@link RequestedDocumentAuthenticityConfig}
 */
public interface RequestedDocumentAuthenticityCheck<T extends RequestedDocumentAuthenticityConfig> extends RequestedCheck<T> {

    @Override
    T getConfig();

}
