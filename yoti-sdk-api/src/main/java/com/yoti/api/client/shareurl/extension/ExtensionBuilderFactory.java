package com.yoti.api.client.shareurl.extension;

import java.util.ServiceLoader;

public class ExtensionBuilderFactory {

    private ExtensionBuilderFactory() {}

    public static ExtensionBuilderFactory newInstance() {
        return new ExtensionBuilderFactory();
    }

    public BasicExtensionBuilder createExtensionBuilder() { return new BasicExtensionBuilder(); }

    public LocationConstraintExtensionBuilder createLocationConstraintExtensionBuilder() {
        return new LocationConstraintExtensionBuilder();
    }

    public TransactionalFlowExtensionBuilder createTransactionalFlowExtensionBuilder() {
        return new TransactionalFlowExtensionBuilder();
    }

    public ThirdPartyAttributeExtensionBuilder createThirdPartyAttributeExtensionBuilder() {
        return new ThirdPartyAttributeExtensionBuilder();
    }

}
