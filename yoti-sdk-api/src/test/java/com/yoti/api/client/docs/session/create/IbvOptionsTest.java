package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IbvOptionsTest {

    private static final String SOME_SUPPORT_VALUE = "someSupportValue";
    private static final String SOME_GUIDANCE_URL = "someGuidanceUrl";

    @Mock UserPrice userPriceMock;

    @Test
    public void builder_withMandatorySupport_shouldSetCorrectSupportValue() {
        IbvOptions result = IbvOptions.builder()
                .withIbvMandatory()
                .build();

        assertThat(result.getSupport(), is("MANDATORY"));
    }

    @Test
    public void builder_withNotAllowedSupport_shouldSetCorrectSupportValue() {
        IbvOptions result = IbvOptions.builder()
                .withIbvNotAllowed()
                .build();

        assertThat(result.getSupport(), is("NOT_ALLOWED"));
    }

    @Test
    public void builder_shouldAllowUserOverrideOfSupportValue() {
        IbvOptions result = IbvOptions.builder()
                .withSupport(SOME_SUPPORT_VALUE)
                .build();

        assertThat(result.getSupport(), is(SOME_SUPPORT_VALUE));
    }

    @Test
    public void builder_shouldSetCorrectGuidanceUrlValue() {
        IbvOptions result = IbvOptions.builder()
                .withIbvMandatory()
                .withGuidanceUrl(SOME_GUIDANCE_URL)
                .build();

        assertThat(result.getGuidanceUrl(), is(SOME_GUIDANCE_URL));
    }

    @Test
    public void builder_shouldSetCorrectUserPrice() {
        IbvOptions result = IbvOptions.builder()
                .withUserPrice(userPriceMock)
                .build();

        assertThat(result.getUserPrice(), is(userPriceMock));
    }

    @Test
    public void builder_setNullForUserPriceWhenNotProvidedExplicitly() {
        IbvOptions result = IbvOptions.builder()
                .build();

        assertThat(result.getUserPrice(), nullValue());
    }

}
