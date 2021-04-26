package com.yoti.api.client.docs.session.create.check.advanced;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class RequestedExactMatchingStrategyTest {

    @Test
    public void builder_shouldBuildRequestedExactMatchingStrategyTest() {
        RequestedExactMatchingStrategy result = RequestedCaMatchingStrategy.forExactMatchBuilder()
                .build();

        assertThat(result, is(not(nullValue())));
        assertThat(result.isExactMatch(), is(true));
    }

}