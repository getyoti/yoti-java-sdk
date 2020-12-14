package com.yoti.api.client.spi.remote.call.aml;

import com.yoti.api.client.aml.AmlAddress;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.aml.AmlProfileFactory;

public final class SimpleAmlProfileFactory implements AmlProfileFactory {

    public AmlProfile create(String givenNames, String familyName, String ssn, AmlAddress amlAddress) {
        return new SimpleAmlProfile(givenNames, familyName, ssn, amlAddress);
    }

}
