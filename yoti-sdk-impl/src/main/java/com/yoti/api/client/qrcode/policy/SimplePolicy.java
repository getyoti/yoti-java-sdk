package com.yoti.api.client.qrcode.policy;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see Policy
 *
 */
public class SimplePolicy implements Policy {

    @JsonProperty("wanted")
    private final List<Attribute> wantedAttributes;

    @JsonProperty("wanted_auth_types")
    private final List<Integer> wantedAuthTypes;

    @JsonProperty("wanted_remember_me")
    private final boolean wantedRememberMe;

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

    /**
     * @see Policy#getWantedAttributes()
     *
     */
    @Override
    public List<Attribute> getWantedAttributes() {
        return wantedAttributes;
    }

    /**
     * @see Policy#getWantedAuthTypes()
     *
     */
    @Override
    public List<Integer> getWantedAuthTypes() {
        return wantedAuthTypes;
    }

    /**
     * @see Policy#isWantedRememberMe()
     *
     */
    @Override
    public boolean isWantedRememberMe() {
        return wantedRememberMe;
    }

    /**
     * @see Policy#isWantedRememberMeOptional()
     *
     */
    @Override
    public boolean isWantedRememberMeOptional() {
        return wantedRememberMeOptional;
    }

}
