package com.yoti.api.examples.springboot.attribute;

import com.yoti.api.client.Attribute;

public class DisplayAttribute {

    private final String label;
    private final String preValue;
    private final String icon;
    private final Attribute<?> attribute;

    public DisplayAttribute(String preValue, String label, Attribute<?> attribute, String icon) {
        this.label = label;
        this.preValue = preValue;
        this.icon = icon;
        this.attribute = attribute;
    }

    public DisplayAttribute(String label, Attribute<?> attribute, String icon) {
        this.label = label;
        this.preValue = "";
        this.icon = icon;
        this.attribute = attribute;
    }

    public DisplayAttribute(Attribute<?> attribute, AttributeDisplayProperty property) {
        this.label = property.label();
        this.preValue = "";
        this.icon = property.icon();
        this.attribute = attribute;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public Attribute<?> getAttribute() {
        return attribute;
    }

    public String getDisplayValue() {
        return this.preValue + this.attribute.getValue();
    }

}
