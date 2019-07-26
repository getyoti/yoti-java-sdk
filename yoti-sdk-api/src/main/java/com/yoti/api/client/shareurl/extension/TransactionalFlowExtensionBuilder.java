package com.yoti.api.client.shareurl.extension;

public interface TransactionalFlowExtensionBuilder extends ExtensionBuilder<Object> {

    /**
     * Allows you to provide a non-null object representing the content to be submitted in the TRANSACTIONAL_FLOW extension.
     *
     * @return this TransactionalFlowExtensionBuilder
     */
    TransactionalFlowExtensionBuilder withContent(Object value);

}
