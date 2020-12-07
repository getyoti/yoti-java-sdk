package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.Matchers;
import org.junit.Test;

public class SimpleRequestedThirdPartyIdentityCheckBuilderTest {

    @Test
    public void shouldSuccessfullyBuildRequestedThirdPartyIdentityCheck() {
        RequestedThirdPartyIdentityCheck<?> result = new SimpleRequestedThirdPartyIdentityCheckBuilder()
                .build();

        assertThat(result, is(not(nullValue())));
        assertThat(result.getType(), is("THIRD_PARTY_IDENTITY"));
        assertThat(result.getConfig(), is(not(nullValue())));
        assertThat(result.getConfig(), Matchers.<RequestedThirdPartyIdentityConfig>instanceOf(SimpleRequestedThirdPartyIdentityConfig.class));
    }

}