package com.yoti.api.client.qrcode.policy;

import java.util.List;

public final class SimplePolicyFactory implements PolicyFactory {

    @Override
    public Policy create(List<Attribute> wantedAttributes, List<Integer> wantedAuthTypes, boolean wantedRememberMe, boolean wantedRememberMeOptional) {
        return new SimplePolicy(wantedAttributes, wantedAuthTypes, wantedRememberMe, wantedRememberMeOptional);
    }

}
