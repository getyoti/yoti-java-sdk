package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.yoti.api.client.docs.session.create.filters.DocumentFilter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IssuingAuthoritySubCheckTest {

    @Mock DocumentFilter documentFilterMock;

    @Test
    public void withRequested_shouldSetTheValueCorrectly() {
        IssuingAuthoritySubCheck result = IssuingAuthoritySubCheck.builder()
                .withRequested(true)
                .build();

        assertThat(result.isRequested(), is(true));
    }

    @Test
    public void withDocumentFilter_shouldSetTheDocumentFilter() {
        IssuingAuthoritySubCheck result = IssuingAuthoritySubCheck.builder()
                .withDocumentFilter(documentFilterMock)
                .build();

        assertThat(result.getFilter(), is(documentFilterMock));
    }

}
