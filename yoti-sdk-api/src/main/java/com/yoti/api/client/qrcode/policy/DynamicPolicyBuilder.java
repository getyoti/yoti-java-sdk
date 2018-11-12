package com.yoti.api.client.qrcode.policy;

import java.util.List;
import java.util.ServiceLoader;

public class DynamicPolicyBuilder {

    private List<WantedAttribute> wantedAttributes;
    private List<Integer> wantedAuthTypes;
    private boolean wantedRememberMe;
    private boolean wantedRememberMeOptional;

    public DynamicPolicyBuilder wantedAttributes(List<WantedAttribute> wantedAttributes) {
        this.wantedAttributes = wantedAttributes;
        return this;
    }

    public DynamicPolicyBuilder wantedAuthTypes(List<Integer> wantedAuthTypes) {
        this.wantedAuthTypes = wantedAuthTypes;
        return this;
    }

    public DynamicPolicyBuilder wantedRememberMe(boolean wantedRememberMe) {
        this.wantedRememberMe = wantedRememberMe;
        return this;
    }

    public DynamicPolicyBuilder wantedRememberMeOptional(boolean wantedRememberMeOptional) {
        this.wantedRememberMeOptional = wantedRememberMeOptional;
        return this;
    }

    public DynamicPolicy build() {
        ServiceLoader<DynamicPolicyFactory> factoryLoader = ServiceLoader.load(DynamicPolicyFactory.class);
        if (!factoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of DynamicPolicyFactory");
        }
        DynamicPolicyFactory dynamicPolicyFactory = factoryLoader.iterator().next();
        return dynamicPolicyFactory.create(wantedAttributes, wantedAuthTypes, wantedRememberMe, wantedRememberMeOptional);
    }

}
