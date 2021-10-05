package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.yoti.api.client.docs.session.create.filters.DocumentFilter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestedDocumentAuthenticityCheckTest {

    @Mock DocumentFilter documentFilterMock;

    @Test
    public void shouldBuildRequestedDocumentAuthenticityCheck() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .build();

        assertThat(result, is(instanceOf(RequestedDocumentAuthenticityCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(RequestedDocumentAuthenticityConfig.class)));
        assertThat(result.getType(), is("ID_DOCUMENT_AUTHENTICITY"));
        assertThat(result.getConfig().getManualCheck(), is(nullValue()));
    }

    @Test
    public void withManualCheckAlways_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void withManualCheckFallback_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .withManualCheckFallback()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void withManualCheckNever_shouldCorrectlySetManualCheckValue() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .withManualCheckNever()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("NEVER"));
    }

    @Test
    public void withIssuingAuthoritySubCheck_shouldBuildDefaultObject() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .withIssuingAuthoritySubCheck()
                .build();

        assertThat(result.getConfig().getIssuingAuthoritySubCheck().isRequested(), is(true));
        assertThat(result.getConfig().getIssuingAuthoritySubCheck().getFilter(), is(nullValue()));
    }

    @Test
    public void withIssuingAuthoritySubCheck_shouldAcceptDocumentFilter() {
        RequestedDocumentAuthenticityCheck result = RequestedDocumentAuthenticityCheck.builder()
                .withIssuingAuthoritySubCheck(documentFilterMock)
                .build();

        assertThat(result.getConfig().getIssuingAuthoritySubCheck().isRequested(), is(true));
        assertThat(result.getConfig().getIssuingAuthoritySubCheck().getFilter(), is(documentFilterMock));
    }

}
