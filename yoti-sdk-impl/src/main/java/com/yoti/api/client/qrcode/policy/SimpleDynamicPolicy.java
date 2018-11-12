package com.yoti.api.client.qrcode.policy;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see DynamicPolicy
 *
 */
public class SimpleDynamicPolicy implements DynamicPolicy {

    @JsonProperty("wanted")
    private final List<Attribute> wantedAttributes;

    @JsonProperty("wanted_auth_types")
    private final List<Integer> wantedAuthTypes;

    @JsonProperty("wanted_remember_me")
    private final boolean wantedRememberMe;

    @JsonProperty("wanted_remember_me_optional")
    private final boolean wantedRememberMeOptional;

    public SimpleDynamicPolicy(List<Attribute> wantedAttributes,
            List<Integer> wantedAuthTypes,
            boolean wantedRememberMe,
            boolean wantedRememberMeOptional) {
        this.wantedAttributes = wantedAttributes;
        this.wantedAuthTypes = wantedAuthTypes;
        this.wantedRememberMe = wantedRememberMe;
        this.wantedRememberMeOptional = wantedRememberMeOptional;
    }

    /**
     * @see DynamicPolicy#getWantedAttributes()
     *
     */
    @Override
    public List<Attribute> getWantedAttributes() {
        return wantedAttributes;
    }

    /**
     * @see DynamicPolicy#getWantedAuthTypes()
     *
     */
    @Override
    public List<Integer> getWantedAuthTypes() {
        return wantedAuthTypes;
    }

    /**
     * @see DynamicPolicy#isWantedRememberMe()
     *
     */
    @Override
    public boolean isWantedRememberMe() {
        return wantedRememberMe;
    }

    /**
     * @see DynamicPolicy#isWantedRememberMeOptional()
     *
     */
    @Override
    public boolean isWantedRememberMeOptional() {
        return wantedRememberMeOptional;
    }

}
