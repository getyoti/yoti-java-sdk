package com.yoti.api.client.qrcode.policy;

import java.util.List;

public interface PolicyFactory {

    Policy create(List<Attribute> wantedAttributes, List<Integer> wantedAuthTypes, boolean wantedRememberMe, boolean wantedRememberMeOptional);

}
