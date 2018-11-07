package com.yoti.api.examples.springboot;

import com.yoti.api.client.Attribute;

public class DisplayAttribute {

    private final String displayName;
    private final String preValue;
    private final String icon;
    private final Attribute attribute;

    public DisplayAttribute(String preValue, String displayName, Attribute attribute, String icon) {
        this.displayName = displayName;
        this.preValue = preValue;
        this.icon = icon;
        this.attribute = attribute;
    }

    public DisplayAttribute(String displayName, Attribute attribute, String icon) {
        this.displayName = displayName;
        this.preValue = "";
        this.icon = icon;
        this.attribute = attribute;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPreValue() {
        return preValue;
    }

    public String getIcon() {
        return icon;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public String getDisplayValue() {
        return this.preValue + this.attribute.getValue();
    }

}
