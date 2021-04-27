package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.yoti.api.client.docs.session.create.check.advanced.RequestedExactMatchingStrategy;
import com.yoti.api.client.docs.session.create.check.advanced.RequestedSearchProfileSources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestedYotiAccountWatchlistAdvancedCaConfigTest {

    private static final boolean SOME_REMOVE_DECEASED = true;
    private static final boolean SOME_SHARE_URL = false;

    @Mock RequestedExactMatchingStrategy exactMatchingStrategyMock;
    @Mock RequestedSearchProfileSources profileSourceMock;

    @Test
    public void builder_shouldBuildWithCorrectProperties() {
        RequestedYotiAccountWatchlistAdvancedCaConfig result = RequestedYotiAccountWatchlistAdvancedCaConfig.builder()
                .withRemoveDeceased(SOME_REMOVE_DECEASED)
                .withShareUrl(SOME_SHARE_URL)
                .withSources(profileSourceMock)
                .withMatchingStrategy(exactMatchingStrategyMock)
                .build();

        assertThat(result, is(not(nullValue())));
        assertThat(result.getRemoveDeceased(), is(SOME_REMOVE_DECEASED));
        assertThat(result.getShareUrl(), is(SOME_SHARE_URL));
        assertThat(result.getSources(), is(profileSourceMock));
        assertThat(result.getMatchingStrategy(), is(exactMatchingStrategyMock));
    }

}