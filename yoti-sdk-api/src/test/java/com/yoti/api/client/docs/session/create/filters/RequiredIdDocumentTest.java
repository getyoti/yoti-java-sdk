package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequiredIdDocumentTest {

    private static final String EXPECTED_TYPE = "ID_DOCUMENT";

    @Mock DocumentFilter documentFilterMock;

    @Test
    public void shouldHaveTheCorrectType() {
        RequiredIdDocument result = RequiredIdDocument.builder()
                .build();

        assertThat(result.getType(), is(EXPECTED_TYPE));
    }

    @Test
    public void shouldBuildWithoutAFilter() {
        RequiredIdDocument result = RequiredIdDocument.builder()
                .build();

        assertThat(result.getFilter(), is(nullValue()));
    }

    @Test
    public void shouldBuildWithAFilter() {
        RequiredIdDocument result = RequiredIdDocument.builder()
                .withFilter(documentFilterMock)
                .build();

        assertThat(result, is(notNullValue()));
        assertThat(result.getFilter(), is(documentFilterMock));
    }

}
