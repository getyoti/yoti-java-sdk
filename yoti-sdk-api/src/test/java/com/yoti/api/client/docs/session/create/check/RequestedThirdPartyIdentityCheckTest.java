package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class RequestedThirdPartyIdentityCheckTest {

    @Test
    public void shouldSuccessfullyBuildRequestedThirdPartyIdentityCheck() {
        RequestedThirdPartyIdentityCheck result = RequestedThirdPartyIdentityCheck.builder()
                .build();

        assertThat(result, is(not(nullValue())));
        assertThat(result.getType(), is("THIRD_PARTY_IDENTITY"));
        assertThat(result.getConfig(), is(not(nullValue())));
        assertThat(result.getConfig(), is(instanceOf(RequestedThirdPartyIdentityConfig.class)));
    }

}