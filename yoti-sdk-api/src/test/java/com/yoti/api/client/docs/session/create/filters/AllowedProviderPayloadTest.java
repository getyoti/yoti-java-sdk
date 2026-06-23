package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class AllowedProviderPayloadTest {

    @Test
    public void shouldBuildWithName() {
        AllowedProviderPayload result = AllowedProviderPayload.builder()
                .withName("DIGILOCKER")
                .build();

        assertThat(result.getName(), is("DIGILOCKER"));
    }

}
