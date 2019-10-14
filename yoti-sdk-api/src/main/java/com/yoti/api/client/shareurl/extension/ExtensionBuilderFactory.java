package com.yoti.api.client.shareurl.extension;

import java.util.ServiceLoader;

public abstract class ExtensionBuilderFactory {

    public static final ExtensionBuilderFactory newInstance() {
        ServiceLoader<ExtensionBuilderFactory> extensionBuilderLoader = ServiceLoader.load(ExtensionBuilderFactory.class);
        if (!extensionBuilderLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + ExtensionBuilderFactory.class.getSimpleName());
        }
        ExtensionBuilderFactory extensionBuilderFactory = extensionBuilderLoader.iterator().next();
        return extensionBuilderFactory.createExtensionBuilderFactory();
    }

    protected abstract ExtensionBuilderFactory createExtensionBuilderFactory();

    public abstract BasicExtensionBuilder createExtensionBuilder();

    public abstract LocationConstraintExtensionBuilder createLocationConstraintExtensionBuilder();

    public abstract TransactionalFlowExtensionBuilder createTransactionalFlowExtensionBuilder();

    public abstract ThirdPartyAttributeExtensionBuilder createThirdPartyAttributeExtensionBuilder();

}
