package com.yoti.api.client.docs.session.create;

public class SimpleSdkConfigBuilderFactory extends SdkConfigBuilderFactory {

    @Override
    public SdkConfigBuilder create() {
        return new SimpleSdkConfigBuilder();
    }

}
