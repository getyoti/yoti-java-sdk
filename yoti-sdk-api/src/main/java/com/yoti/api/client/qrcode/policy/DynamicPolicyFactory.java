package com.yoti.api.client.qrcode.policy;

import java.util.List;

public interface DynamicPolicyFactory {

    DynamicPolicy create(List<WantedAttribute> wantedAttributes, List<Integer> wantedAuthTypes, boolean wantedRememberMe, boolean wantedRememberMeOptional);

}