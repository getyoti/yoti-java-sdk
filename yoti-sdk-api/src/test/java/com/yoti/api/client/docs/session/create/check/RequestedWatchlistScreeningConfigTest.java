package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.docs.DocScanConstants.ADVERSE_MEDIA;
import static com.yoti.api.client.docs.DocScanConstants.SANCTIONS;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class RequestedWatchlistScreeningConfigTest {

    private static final String SOME_UNKNOWN_CATEGORY = "SOME_UNKNOWN_CATEGORY";

    @Test
    public void builder_shouldBuildWithoutAnySuppliedCategories() {
        RequestedWatchlistScreeningConfig result = RequestedWatchlistScreeningConfig.builder().build();

        assertThat(result.getCategories(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithAdverseMediaCategory() {
        RequestedWatchlistScreeningConfig result = RequestedWatchlistScreeningConfig.builder()
                .withAdverseMediaCategory()
                .build();

        assertThat(result.getCategories(), hasSize(1));
        assertThat(result.getCategories(), hasItem(ADVERSE_MEDIA));
    }

    @Test
    public void builder_shouldBuildWithSanctionsCategory() {
        RequestedWatchlistScreeningConfig result = RequestedWatchlistScreeningConfig.builder()
                .withSanctionsCategory()
                .build();

        assertThat(result.getCategories(), hasSize(1));
        assertThat(result.getCategories(), hasItem(SANCTIONS));
    }

    @Test
    public void builder_shouldAllowMultipleCategoriesToBeAdded() {
        RequestedWatchlistScreeningConfig result = RequestedWatchlistScreeningConfig.builder()
                .withAdverseMediaCategory()
                .withSanctionsCategory()
                .build();

        assertThat(result.getCategories(), hasSize(2));
        assertThat(result.getCategories(), hasItems(ADVERSE_MEDIA, SANCTIONS));
    }

    @Test
    public void builder_shouldNotAddCategoryMoreThanOnceEvenIfCalled() {
        RequestedWatchlistScreeningConfig result = RequestedWatchlistScreeningConfig.builder()
                .withCategory(ADVERSE_MEDIA)
                .withCategory(ADVERSE_MEDIA)
                .withAdverseMediaCategory()
                .withAdverseMediaCategory()
                .build();

        assertThat(result.getCategories(), hasSize(1));
        assertThat(result.getCategories(), hasItem(ADVERSE_MEDIA));
    }

    @Test
    public void builder_shouldNotAllowNullCategory() {
        RequestedWatchlistScreeningConfig.Builder builder = RequestedWatchlistScreeningConfig.builder();

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> builder.withCategory(null));

        assertThat(ex.getMessage(), containsString("category"));
        RequestedWatchlistScreeningConfig result = builder.build();
        assertThat(result.getCategories(), is(nullValue()));
    }

    @Test
    public void builder_shouldAllowCategoryUnknownToTheSdk() {
        RequestedWatchlistScreeningConfig result = RequestedWatchlistScreeningConfig.builder()
                .withAdverseMediaCategory()
                .withSanctionsCategory()
                .withCategory(SOME_UNKNOWN_CATEGORY)
                .build();

        assertThat(result.getCategories(), hasSize(3));
        assertThat(result.getCategories(), hasItems(ADVERSE_MEDIA, SANCTIONS, SOME_UNKNOWN_CATEGORY));
    }

}
