package com.yoti.api.client.spi.remote;

import static java.lang.Boolean.parseBoolean;

import com.yoti.api.client.AgeVerification;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.spi.remote.util.Validation;

public class SimpleAgeVerification implements AgeVerification {

    private final Attribute<String> derivedAttribute;
    private final int ageVerified;
    private final String checkPerformed;
    private final boolean result;

    public SimpleAgeVerification(Attribute<String> derivedAttribute) {
        Validation.notNull(derivedAttribute, "derivedAttribute");
        Validation.matchesPattern(derivedAttribute.getName(), "[^:]+:(?!.*:)[0-9]+", "attribute.name");
        this.derivedAttribute = derivedAttribute;

        String[] split = derivedAttribute.getName().split(":");
        this.checkPerformed = split[0];
        this.ageVerified = Integer.parseInt(split[1]);
        this.result = parseBoolean(derivedAttribute.getValue());
    }

    @Override
    public int getAgeVerified() {
        return ageVerified;
    }

    @Override
    public String getCheckPerformed() {
        return checkPerformed;
    }

    @Override
    public boolean getResult() {
        return result;
    }

    @Override
    public Attribute<String> getAttribute() {
        return derivedAttribute;
    }

}
