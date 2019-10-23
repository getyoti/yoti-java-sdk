package com.yoti.api.client.shareurl.policy;

public class SimpleWantedAnchorBuilder extends WantedAnchorBuilder {

    private String value;

    private String subType;

    @Override
    protected WantedAnchorBuilder createWantedAnchorBuilder() {
        return new SimpleWantedAnchorBuilder();
    }

    @Override
    public WantedAnchorBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public WantedAnchorBuilder withSubType(String subType) {
        this.subType = subType;
        return this;
    }

    @Override
    public WantedAnchor build() {
        return new SimpleWantedAnchor(value, subType);
    }
}
