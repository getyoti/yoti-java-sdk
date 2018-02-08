package com.yoti.api.client.aml;

import java.util.ServiceLoader;

public class AmlProfileBuilder {

    private String givenNames;
    private String familyName;
    private String ssn;
    private AmlAddress amlAddress;

    public AmlProfileBuilder withGivenNames(String givenNames) {
        this.givenNames = givenNames;
        return this;
    }

    public AmlProfileBuilder withFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public AmlProfileBuilder withSsn(String ssn) {
        this.ssn = ssn;
        return this;
    }

    public AmlProfileBuilder withAddress(AmlAddress amlAddress) {
        this.amlAddress = amlAddress;
        return this;
    }

    public AmlProfile build() {
        ServiceLoader<AmlProfileFactory> profileFactoryLoader = ServiceLoader.load(AmlProfileFactory.class);
        if (! profileFactoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of AmlProfileFactory");
        }
        AmlProfileFactory amlProfileFactory = profileFactoryLoader.iterator().next();
        return amlProfileFactory.create(givenNames, familyName, ssn, amlAddress);
    }

}
