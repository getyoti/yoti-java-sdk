package com.yoti.api.client.qrcode.extension;

public class SimpleExtensionBuilderFactory extends ExtensionBuilderFactory {

    @Override
    protected ExtensionBuilderFactory createExtensionBuilderFactory() {
        return new SimpleExtensionBuilderFactory();
    }

    @Override
    public BasicExtensionBuilder createExtensionBuilder() {
        return new SimpleExtensionBuilder();
    }

    @Override
    public LocationConstraintExtensionBuilder createLocationConstraintExtensionBuilder() {
        return new SimpleLocationConstraintExtensionBuilder();
    }

    @Override
    public TransactionalFlowExtensionBuilder createTransactionalFlowExtensionBuilder() {
        return new SimpleTransactionalFlowExtensionBuilder();
    }

}
