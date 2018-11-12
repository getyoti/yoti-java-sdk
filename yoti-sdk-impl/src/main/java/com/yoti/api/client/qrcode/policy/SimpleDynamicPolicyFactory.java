package com.yoti.api.client.qrcode.policy;

import java.util.List;

public final class SimpleDynamicPolicyFactory implements DynamicPolicyFactory {

    @Override
    public DynamicPolicy create(List<Attribute> wantedAttributes, List<Integer> wantedAuthTypes, boolean wantedRememberMe, boolean wantedRememberMeOptional) {
        return new SimpleDynamicPolicy(wantedAttributes, wantedAuthTypes, wantedRememberMe, wantedRememberMeOptional);
    }

}
