package com.yoti.api.client.spi.remote.call.aml;

import com.yoti.api.client.aml.AmlAddress;
import com.yoti.api.client.aml.AmlAddressFactory;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.aml.AmlProfileFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleAmlTest {

    private static final String SOME_GIVEN_NAME = "Jandice";
    private static final String SOME_FAMILY_NAME = "Witherspoon";
    private static final String SOME_SSN = "someSsn";

    private static final String SOME_POSTCODE = "WD14 4TF";
    private static final String SOME_COUNTRY = "GB";

    @Mock
    AmlAddress amlAddressMock;

    @Test
    public void shouldReturnAmlProfile() {
        AmlProfile amlProfile = new SimpleAmlProfile(SOME_GIVEN_NAME, SOME_FAMILY_NAME, SOME_SSN, amlAddressMock);

        assertNotNull(amlProfile);
        assertThat(amlProfile.getGivenNames(), is(SOME_GIVEN_NAME));
        assertThat(amlProfile.getFamilyName(), is(SOME_FAMILY_NAME));
        assertThat(amlProfile.getSsn(), is(SOME_SSN));
        assertEquals(amlProfile.getAmlAddress(), amlAddressMock);
    }

    @Test
    public void factoryShouldReturnValidAmlProfile() {
        AmlProfileFactory amlProfileFactory = new SimpleAmlProfileFactory();
        AmlProfile amlProfile = amlProfileFactory.create(SOME_GIVEN_NAME, SOME_FAMILY_NAME, SOME_SSN, amlAddressMock);

        assertNotNull(amlProfile);
        assertThat(amlProfile.getGivenNames(), is(SOME_GIVEN_NAME));
        assertThat(amlProfile.getFamilyName(), is(SOME_FAMILY_NAME));
        assertThat(amlProfile.getSsn(), is(SOME_SSN));
        assertEquals(amlProfile.getAmlAddress(), amlAddressMock);
    }

    @Test
    public void shouldReturnAmlAddress() {
        AmlAddress amlAddress = new SimpleAmlAddress(SOME_POSTCODE, SOME_COUNTRY);

        assertNotNull(amlAddress);
        assertThat(amlAddress.getPostCode(), is(SOME_POSTCODE));
        assertThat(amlAddress.getCountry(), is(SOME_COUNTRY));
    }

    @Test
    public void factoryShouldReturnValidSimpleAmlAddress() {
        AmlAddressFactory amlAddressFactory = new SimpleAmlAddressFactory();
        AmlAddress amlAddress = amlAddressFactory.create(SOME_POSTCODE, SOME_COUNTRY);

        assertNotNull(amlAddress);
        assertThat(amlAddress.getPostCode(), is(SOME_POSTCODE));
        assertThat(amlAddress.getCountry(), is(SOME_COUNTRY));
    }

}
