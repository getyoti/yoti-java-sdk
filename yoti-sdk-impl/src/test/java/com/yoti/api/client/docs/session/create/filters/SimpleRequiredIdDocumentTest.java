package com.yoti.api.client.docs.session.create.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimpleRequiredIdDocumentTest {

    private static final String EXPECTED_TYPE = "ID_DOCUMENT";

    @Mock DocumentFilter documentFilterMock;

    @Test
    public void shouldHaveTheCorrectType() {
        RequiredIdDocument result = new SimpleRequiredIdDocumentBuilder()
                .build();

        assertThat(result.getType(), is(EXPECTED_TYPE));
    }

    @Test
    public void shouldBuildWithoutAFilter() {
        RequiredIdDocument result = new SimpleRequiredIdDocumentBuilder()
                .build();

        assertThat(result.getFilter(), is(nullValue()));
    }

    @Test
    public void shouldBuildWithAFilter() {
        RequiredIdDocument result = new SimpleRequiredIdDocumentBuilder()
                .withFilter(documentFilterMock)
                .build();

        assertThat(result, is(notNullValue()));
        assertThat(result.getFilter(), is(documentFilterMock));
    }

}
