package com.yoti.api.client.identity.extension;

import com.yoti.validation.Validation;

public class TransactionalFlowExtensionBuilder implements ExtensionBuilder<Object> {

    private Object content;

    public TransactionalFlowExtensionBuilder withContent(Object content) {
        Validation.notNull(content, Property.CONTENT);

        this.content = content;
        return this;
    }

    @Override
    public Extension<Object> build() {
        return new Extension<>("TRANSACTIONAL_FLOW", content);
    }

    private static final class Property {

        private static final String CONTENT = "content";

    }

}
