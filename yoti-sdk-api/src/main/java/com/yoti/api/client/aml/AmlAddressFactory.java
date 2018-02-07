package com.yoti.api.client.aml;

public interface AmlAddressFactory {

    AmlAddress create(String postCode, String country);

}
