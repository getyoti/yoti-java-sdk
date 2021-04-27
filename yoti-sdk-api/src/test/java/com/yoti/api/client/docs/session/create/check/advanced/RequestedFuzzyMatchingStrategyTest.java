package com.yoti.api.client.docs.session.create.check.advanced;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class RequestedFuzzyMatchingStrategyTest {

    private static final double SOME_FUZZINESS = 0.5D;

    @Test
    public void builder_shouldBuildWithCorrectFuzziness() {
        RequestedFuzzyMatchingStrategy result = RequestedFuzzyMatchingStrategy.builder()
                .withFuzziness(SOME_FUZZINESS)
                .build();

        assertThat(result, is(not(nullValue())));
        assertThat(result.getFuzziness(), is(SOME_FUZZINESS));
    }

}