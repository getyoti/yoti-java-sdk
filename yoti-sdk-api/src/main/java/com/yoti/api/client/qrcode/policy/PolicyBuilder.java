package com.yoti.api.client.qrcode.policy;

import java.util.List;
import java.util.ServiceLoader;

public class PolicyBuilder {

    private List<Attribute> wantedAttributes;

    private List<Integer> wantedAuthTypes;

    private boolean wantedRememberMe;

    private boolean wantedRememberMeOptional;

    public PolicyBuilder wantedAttributes(List<Attribute> wantedAttributes) {
        this.wantedAttributes = wantedAttributes;
        return this;
    }

    public PolicyBuilder wantedAuthTypes(List<Integer> wantedAuthTypes) {
        this.wantedAuthTypes = wantedAuthTypes;
        return this;
    }

    public PolicyBuilder wantedRememberMe(boolean wantedRememberMe) {
        this.wantedRememberMe = wantedRememberMe;
        return this;
    }

    public PolicyBuilder wantedRememberMeOptional(boolean wantedRememberMeOptional) {
        this.wantedRememberMeOptional = wantedRememberMeOptional;
        return this;
    }

    public Policy build() {
        ServiceLoader<PolicyFactory> factoryLoader = ServiceLoader.load(PolicyFactory.class);
        if (!factoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of PolicyFactory");
        }
        PolicyFactory policyFactory = factoryLoader.iterator().next();
        return policyFactory.create(wantedAttributes, wantedAuthTypes, wantedRememberMe, wantedRememberMeOptional);
    }

}
