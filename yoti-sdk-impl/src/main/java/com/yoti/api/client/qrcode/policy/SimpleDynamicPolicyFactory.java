package com.yoti.api.client.qrcode.policy;

import java.util.Collection;
import java.util.Set;

public final class SimpleDynamicPolicyFactory implements DynamicPolicyFactory {

    @Override
    public DynamicPolicy create(Collection<WantedAttribute> wantedAttributes, Set<Integer> wantedAuthTypes, boolean wantedRememberMe, boolean wantedRememberMeOptional) {
        return new SimpleDynamicPolicy(wantedAttributes, wantedAuthTypes, wantedRememberMe, wantedRememberMeOptional);
    }

}
