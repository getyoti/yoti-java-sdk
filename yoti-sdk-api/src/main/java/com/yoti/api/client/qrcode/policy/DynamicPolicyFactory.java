package com.yoti.api.client.qrcode.policy;

import java.util.Collection;
import java.util.Set;

public interface DynamicPolicyFactory {

    DynamicPolicy create(Collection<WantedAttribute> wantedAttributes, Set<Integer> wantedAuthTypes, boolean wantedRememberMe, boolean wantedRememberMeOptional);

}
