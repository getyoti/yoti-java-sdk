package com.yoti.api.client.spi.remote.call.aml;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.yoti.api.client.aml.AmlAddress;
import com.yoti.api.client.aml.AmlProfile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AmlTest {

    private static final String SOME_GIVEN_NAME = "Jandice";
    private static final String SOME_FAMILY_NAME = "Witherspoon";
    private static final String SOME_SSN = "someSsn";

    private static final String SOME_POSTCODE = "WD14 4TF";
    private static final String SOME_COUNTRY = "GB";

    @Mock
    AmlAddress amlAddressMock;

    @Test
    public void shouldReturnAmlProfile() {
        AmlProfile amlProfile = AmlProfile.builder()
                .withGivenNames(SOME_GIVEN_NAME)
                .withFamilyName(SOME_FAMILY_NAME)
                .withSsn(SOME_SSN)
                .withAddress(amlAddressMock)
                .build();

        assertNotNull(amlProfile);
        assertThat(amlProfile.getGivenNames(), is(SOME_GIVEN_NAME));
        assertThat(amlProfile.getFamilyName(), is(SOME_FAMILY_NAME));
        assertThat(amlProfile.getSsn(), is(SOME_SSN));
        assertEquals(amlProfile.getAmlAddress(), amlAddressMock);
    }

    @Test
    public void factoryShouldReturnValidAmlProfile() {
        AmlProfile amlProfile = AmlProfile.builder()
                .withGivenNames(SOME_GIVEN_NAME)
                .withFamilyName(SOME_FAMILY_NAME)
                .withSsn(SOME_SSN)
                .withAddress(amlAddressMock)
                .build();

        assertNotNull(amlProfile);
        assertThat(amlProfile.getGivenNames(), is(SOME_GIVEN_NAME));
        assertThat(amlProfile.getFamilyName(), is(SOME_FAMILY_NAME));
        assertThat(amlProfile.getSsn(), is(SOME_SSN));
        assertEquals(amlProfile.getAmlAddress(), amlAddressMock);
    }

    @Test
    public void shouldReturnAmlAddress() {
        AmlAddress amlAddress = new AmlAddress(SOME_POSTCODE, SOME_COUNTRY);

        assertNotNull(amlAddress);
        assertThat(amlAddress.getPostCode(), is(SOME_POSTCODE));
        assertThat(amlAddress.getCountry(), is(SOME_COUNTRY));
    }

    @Test
    public void factoryShouldReturnValidSimpleAmlAddress() {
        AmlAddress amlAddress = AmlAddress.builder()
                .withPostCode(SOME_POSTCODE)
                .withCountry(SOME_COUNTRY)
                .build();

        assertNotNull(amlAddress);
        assertThat(amlAddress.getPostCode(), is(SOME_POSTCODE));
        assertThat(amlAddress.getCountry(), is(SOME_COUNTRY));
    }

}
