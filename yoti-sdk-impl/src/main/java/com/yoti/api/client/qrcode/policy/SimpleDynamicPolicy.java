package com.yoti.api.client.qrcode.policy;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see DynamicPolicy
 *
 */
public class SimpleDynamicPolicy implements DynamicPolicy {

    @JsonProperty("wanted")
    private final Collection<WantedAttribute> wantedAttributes;

    @JsonProperty("wanted_auth_types")
    private final Set<Integer> wantedAuthTypes;

    @JsonProperty("wanted_remember_me")
    private final boolean wantedRememberMe;

    @JsonProperty("wanted_remember_me_optional")
    private final boolean wantedRememberMeOptional;

    // FIXME: Should this be public?
    public SimpleDynamicPolicy(Collection<WantedAttribute> wantedAttributes,
            Set<Integer> wantedAuthTypes,
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
    public Collection<WantedAttribute> getWantedAttributes() {
        return wantedAttributes;
    }

    /**
     * @see DynamicPolicy#getWantedAuthTypes()
     *
     */
    @Override
    public Set<Integer> getWantedAuthTypes() {
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
