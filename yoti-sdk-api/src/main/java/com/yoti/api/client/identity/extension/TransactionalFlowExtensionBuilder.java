package com.yoti.api.client.identity.extension;

import com.yoti.api.client.spi.remote.util.Validation;

public class TransactionalFlowExtensionBuilder implements ExtensionBuilder<Object> {

    public static final String TYPE = "TRANSACTIONAL_FLOW";

    private Object content;

    public TransactionalFlowExtensionBuilder withContent(Object content) {
        Validation.notNull(content, Property.CONTENT);

        this.content = content;
        return this;
    }

    @Override
    public Extension<Object> build() {
        return new Extension<>(TYPE, content);
    }

    private static final class Property {

        private static final String CONTENT = "content";

    }

}
