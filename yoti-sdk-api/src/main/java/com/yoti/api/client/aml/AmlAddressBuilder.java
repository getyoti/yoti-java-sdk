package com.yoti.api.client.aml;

import java.util.ServiceLoader;

public class AmlAddressBuilder {

    private String postCode;
    private String country;

    public AmlAddressBuilder withPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public AmlAddressBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public AmlAddress build() {
        ServiceLoader<AmlAddressFactory> addressFactoryLoader = ServiceLoader.load(AmlAddressFactory.class);
        if (! addressFactoryLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of AmlAddressFactory");
        }
        AmlAddressFactory amlAddressFactory = addressFactoryLoader.iterator().next();
        return amlAddressFactory.create(postCode, country);
    }

}
