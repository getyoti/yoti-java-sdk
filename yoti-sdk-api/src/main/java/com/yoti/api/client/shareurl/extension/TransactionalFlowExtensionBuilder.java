package com.yoti.api.client.shareurl.extension;

import com.yoti.api.client.spi.remote.util.Validation;

public class TransactionalFlowExtensionBuilder implements ExtensionBuilder<Object> {

    private Object content;

    /**
     * Allows you to provide a non-null object representing the content to be submitted in the TRANSACTIONAL_FLOW extension.
     *
     * @return this TransactionalFlowExtensionBuilder
     */
    public TransactionalFlowExtensionBuilder withContent(Object content) {
        Validation.notNull(content, "content");
        this.content = content;
        return this;
    }

    @Override
    public Extension<Object> build() {
        return new Extension<>(ExtensionConstants.TRANSACTIONAL_FLOW, content);
    }

}
