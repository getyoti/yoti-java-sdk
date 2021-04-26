package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.docs.session.create.check.advanced.RequestedExactMatchingStrategy;
import com.yoti.api.client.docs.session.create.check.advanced.RequestedSearchProfileSources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestedCustomAccountWatchlistAdvancedCaConfigTest {

    private static final boolean SOME_REMOVE_DECEASED = true;
    private static final boolean SOME_SHARE_URL = false;
    private static final String SOME_API_KEY = "someApiKey";
    private static final boolean SOME_MONITORING = true;
    private static final Map<String, String> SOME_TAGS;
    private static final String SOME_CLIENT_REF = "someClientRef";

    static {
        SOME_TAGS = new HashMap<>();
        SOME_TAGS.put("someKey", "someValue");
    }

    @Mock RequestedExactMatchingStrategy exactMatchingStrategyMock;
    @Mock RequestedSearchProfileSources profileSourceMock;

    @Test
    public void builder_shouldBuildWithCorrectProperties() {
        RequestedCustomAccountWatchlistAdvancedCaConfig result = RequestedWatchlistAdvancedCaConfig.customAccountConfigBuilder()
                .withRemoveDeceased(SOME_REMOVE_DECEASED)
                .withShareUrl(SOME_SHARE_URL)
                .withSources(profileSourceMock)
                .withMatchingStrategy(exactMatchingStrategyMock)
                .withApiKey(SOME_API_KEY)
                .withClientRef(SOME_CLIENT_REF)
                .withMonitoring(SOME_MONITORING)
                .withTags(SOME_TAGS)
                .build();

        assertThat(result, is(not(nullValue())));
        assertThat(result.getRemoveDeceased(), is(SOME_REMOVE_DECEASED));
        assertThat(result.getShareUrl(), is(SOME_SHARE_URL));
        assertThat(result.getSources(), is(profileSourceMock));
        assertThat(result.getMatchingStrategy(), is(exactMatchingStrategyMock));
        assertThat(result.getApiKey(), is(SOME_API_KEY));
        assertThat(result.getClientRef(), is(SOME_CLIENT_REF));
        assertThat(result.getMonitoring(), is(SOME_MONITORING));
        assertThat(result.getTags(), is(SOME_TAGS));
    }

}