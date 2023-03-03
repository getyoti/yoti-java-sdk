package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.hamcrest.Matchers;
import org.junit.Test;

public class RequestedDocumentSchemeValidityCheckTest {

    private final static String SOME_SCHEME = "someScheme";

    @Test
    public void shouldBuildWithManualCheckAlwaysAndScheme() {
        RequestedDocumentSchemeValidityCheck result = RequestedDocumentSchemeValidityCheck.builder()
                .withManualCheckAlways()
                .withScheme(SOME_SCHEME)
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedDocumentSchemeValidityCheck.class)));
        assertThat(result.getConfig(), instanceOf(RequestedDocumentSchemeValidityConfig.class));
        assertThat(result.getType(), Matchers.is("DOCUMENT_SCHEME_VALIDITY_CHECK"));

        RequestedDocumentSchemeValidityConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("ALWAYS"));
        assertThat(configResult.getScheme(), Matchers.is(SOME_SCHEME));
    }

    @Test
    public void shouldBuildWithManualCheckFallbackAndScheme() {
        RequestedDocumentSchemeValidityCheck result = RequestedDocumentSchemeValidityCheck.builder()
                .withManualCheckFallback()
                .withScheme(SOME_SCHEME)
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedDocumentSchemeValidityCheck.class)));
        assertThat(result.getConfig(), Matchers.is(instanceOf(RequestedDocumentSchemeValidityConfig.class)));
        assertThat(result.getType(), Matchers.is("DOCUMENT_SCHEME_VALIDITY_CHECK"));

        RequestedDocumentSchemeValidityConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("FALLBACK"));
        assertThat(configResult.getScheme(), Matchers.is(SOME_SCHEME));
    }

    @Test
    public void shouldBuildWithManualCheckNeverAndScheme() {
        RequestedDocumentSchemeValidityCheck result = RequestedDocumentSchemeValidityCheck.builder()
                .withManualCheckNever()
                .withScheme(SOME_SCHEME)
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedDocumentSchemeValidityCheck.class)));
        assertThat(result.getConfig(), Matchers.is(instanceOf(RequestedDocumentSchemeValidityConfig.class)));
        assertThat(result.getType(), Matchers.is("DOCUMENT_SCHEME_VALIDITY_CHECK"));

        RequestedDocumentSchemeValidityConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("NEVER"));
        assertThat(configResult.getScheme(), Matchers.is(SOME_SCHEME));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseForNullManualCheckType() {
        RequestedDocumentSchemeValidityCheck.builder()
                .withScheme(SOME_SCHEME)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseForNullScheme() {
        RequestedDocumentSchemeValidityCheck.builder()
                .withManualCheckFallback()
                .build();
    }

}
