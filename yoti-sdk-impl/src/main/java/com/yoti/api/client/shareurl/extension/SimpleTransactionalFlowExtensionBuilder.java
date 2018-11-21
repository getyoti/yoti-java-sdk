package com.yoti.api.client.shareurl.extension;

import com.yoti.api.client.spi.remote.util.Validation;

public class SimpleTransactionalFlowExtensionBuilder implements TransactionalFlowExtensionBuilder {

    private Object content;

    @Override
    public SimpleTransactionalFlowExtensionBuilder withContent(Object content) {
        Validation.notNull(content, "content");
        this.content = content;
        return this;
    }

    @Override
    public Extension<Object> build() {
        return new SimpleExtension<>(ExtensionConstants.TRANSACTIONAL_FLOW, content);
    }

}
