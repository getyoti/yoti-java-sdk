package com.yoti.api.client.qrcode.policy;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SimplePolicy
 */
public class SimplePolicy implements Policy {

    /**
     * wantedAttributes
     */
    @JsonProperty("wanted")
    private final List<Attribute> wantedAttributes;

    /**
     * wantedAuthTypes
     */
    @JsonProperty("wanted_auth_types")
    private final List<Integer> wantedAuthTypes;

    /**
     * wantedRememberMe
     */
    @JsonProperty("wanted_remember_me")
    private final boolean wantedRememberMe;

    /**
     * wantedRememberMeOptional
     */
    @JsonProperty("wanted_remember_me_optional")
    private final boolean wantedRememberMeOptional;

    public SimplePolicy(List<Attribute> wantedAttributes,
            List<Integer> wantedAuthTypes,
            boolean wantedRememberMe,
            boolean wantedRememberMeOptional) {
        this.wantedAttributes = wantedAttributes;
        this.wantedAuthTypes = wantedAuthTypes;
        this.wantedRememberMe = wantedRememberMe;
        this.wantedRememberMeOptional = wantedRememberMeOptional;
    }

    @Override
    public List<Attribute> getWantedAttributes() {
        return wantedAttributes;
    }

    @Override
    public List<Integer> getWantedAuthTypes() {
        return wantedAuthTypes;
    }

    @Override
    public boolean isWantedRememberMe() {
        return wantedRememberMe;
    }

    @Override
    public boolean isWantedRememberMeOptional() {
        return wantedRememberMeOptional;
    }

}
