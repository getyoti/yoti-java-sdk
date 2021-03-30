package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.yoti.api.client.docs.DocScanConstants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestedWatchlistScreeningCheckTest {

    @Mock RequestedWatchlistScreeningConfig configMock;

    @Test
    public void builder_shouldBuildWithoutAnySuppliedConfig() {
        RequestedWatchlistScreeningCheck result = RequestedWatchlistScreeningCheck.builder().build();

        assertThat(result.getType(), is(DocScanConstants.WATCHLIST_SCREENING));
        assertThat(result.getConfig(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithSuppliedConfig() {
        RequestedWatchlistScreeningCheck result = RequestedWatchlistScreeningCheck.builder()
                .withConfig(configMock)
                .build();

        assertThat(result.getType(), is(DocScanConstants.WATCHLIST_SCREENING));
        assertThat(result.getConfig(), is(configMock));
    }

}