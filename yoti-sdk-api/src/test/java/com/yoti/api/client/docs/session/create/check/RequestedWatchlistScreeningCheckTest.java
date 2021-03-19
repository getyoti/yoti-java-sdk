package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.yoti.api.client.docs.DocScanConstants;

import org.junit.Test;

public class RequestedWatchlistScreeningCheckTest {

    private static final String SOME_UNKNOWN_CATEGORY = "SOME_UNKNOWN_CATEGORY";

    @Test
    public void builder_shouldBuildWithoutAnySuppliedCategories() {
        RequestedWatchlistScreeningCheck result = RequestedWatchlistScreeningCheck.builder()
                .build();

        assertThat(result.getType(), is(DocScanConstants.WATCHLIST_SCREENING));
        assertThat(result.getConfig(), is(not(nullValue())));
        assertThat(result.getConfig().getCategories(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithAdverseMediaCategory() {
        RequestedWatchlistScreeningCheck result = RequestedWatchlistScreeningCheck.builder()
                .withAdverseMediaCategory()
                .build();

        assertThat(result.getConfig().getCategories(), hasSize(1));
        assertThat(result.getConfig().getCategories(), hasItem("ADVERSE-MEDIA"));
    }

    @Test
    public void builder_shouldBuildWithSanctionsCategory() {
        RequestedWatchlistScreeningCheck result = RequestedWatchlistScreeningCheck.builder()
                .withSanctionsCategory()
                .build();

        assertThat(result.getConfig().getCategories(), hasSize(1));
        assertThat(result.getConfig().getCategories(), hasItem("SANCTIONS"));
    }

    @Test
    public void builder_shouldAllowMultipleCategoriesToBeAdded() {
        RequestedWatchlistScreeningCheck result = RequestedWatchlistScreeningCheck.builder()
                .withAdverseMediaCategory()
                .withSanctionsCategory()
                .build();

        assertThat(result.getConfig().getCategories(), hasSize(2));
        assertThat(result.getConfig().getCategories(), hasItems("ADVERSE-MEDIA", "SANCTIONS"));
    }

    @Test
    public void builder_shouldNotAddCategoryMoreThanOnceEvenIfCalled() {
        RequestedWatchlistScreeningCheck result = RequestedWatchlistScreeningCheck.builder()
                .withAdverseMediaCategory()
                .withAdverseMediaCategory()
                .build();

        assertThat(result.getConfig().getCategories(), hasSize(1));
        assertThat(result.getConfig().getCategories(), hasItem("ADVERSE-MEDIA"));
    }

    @Test
    public void builder_shouldAllowCategoryUnknownToTheSdk() {
        RequestedWatchlistScreeningCheck result = RequestedWatchlistScreeningCheck.builder()
                .withCategory(SOME_UNKNOWN_CATEGORY)
                .build();

        assertThat(result.getConfig().getCategories(), hasSize(1));
        assertThat(result.getConfig().getCategories(), hasItem(SOME_UNKNOWN_CATEGORY));
    }

}