package com.yoti.api.client.aml;

public interface AmlProfileFactory {

    AmlProfile create(String givenNames, String familyName, String ssn, AmlAddress amlAddress);
}
