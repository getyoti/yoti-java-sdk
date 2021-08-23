package com.yoti.api.client.docs.session.instructions.document;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class SelectedIdDocumentTest {

    private static final String SOME_COUNTRY_CODE = "someCountryCode";
    private static final String SOME_DOCUMENT_TYPE = "someDocumentType";

    @Test
    public void builder_shouldBuildWithCountryCode() {
        SelectedIdDocument result = SelectedIdDocument.builder()
                .withCountryCode(SOME_COUNTRY_CODE)
                .build();

        assertThat(result.getType(), is("ID_DOCUMENT"));
        assertThat(result.getCountryCode(), is(SOME_COUNTRY_CODE));
        assertThat(result.getDocumentType(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithDocumentType() {
        SelectedIdDocument result = SelectedIdDocument.builder()
                .withDocumentType(SOME_DOCUMENT_TYPE)
                .build();

        assertThat(result.getType(), is("ID_DOCUMENT"));
        assertThat(result.getDocumentType(), is(SOME_DOCUMENT_TYPE));
        assertThat(result.getCountryCode(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithAllProperties() {
        SelectedIdDocument result = SelectedIdDocument.builder()
                .withCountryCode(SOME_COUNTRY_CODE)
                .withDocumentType(SOME_DOCUMENT_TYPE)
                .build();

        assertThat(result.getType(), is("ID_DOCUMENT"));
        assertThat(result.getCountryCode(), is(SOME_COUNTRY_CODE));
        assertThat(result.getDocumentType(), is(SOME_DOCUMENT_TYPE));
    }

}
