package com.yoti.api.client.docs.session.create.check.advanced;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class RequestedSearchProfileSourcesTest {

    private static final String SOME_SEARCH_PROFILE = "someSearchProfile";

    @Test
    public void builder_shouldBuildWithCorrectSearchProfile() {
        RequestedSearchProfileSources result = RequestedCaSources.searchProfileSourcesBuilder()
                .withSearchProfile(SOME_SEARCH_PROFILE)
                .build();

        assertThat(result, is(not(nullValue())));
        assertThat(result.getSearchProfile(), is(SOME_SEARCH_PROFILE));
    }
}