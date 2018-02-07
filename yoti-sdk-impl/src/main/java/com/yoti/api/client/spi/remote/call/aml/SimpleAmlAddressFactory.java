package com.yoti.api.client.spi.remote.call.aml;

import com.yoti.api.client.aml.AmlAddress;
import com.yoti.api.client.aml.AmlAddressFactory;

public class SimpleAmlAddressFactory implements AmlAddressFactory {

    public AmlAddress create(String postCode, String country) {
        return new SimpleAmlAddress(postCode, country);
    }

}
