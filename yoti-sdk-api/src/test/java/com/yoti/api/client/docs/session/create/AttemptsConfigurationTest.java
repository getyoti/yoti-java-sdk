package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Map;

import org.junit.Test;

public class AttemptsConfigurationTest {

    private static final String GENERIC = "GENERIC";
    private static final String RECLASSIFICATION = "RECLASSIFICATION";

    @Test
    public void idDocumentTextExtractionRetries_shouldBeNullWhenNotSet() {
        AttemptsConfiguration result = AttemptsConfiguration.builder()
                .build();

        Map<String, Integer> idDocRetries = result.getIdDocumentTextDataExtraction();
        assertThat(idDocRetries, is(nullValue()));
    }

    @Test
    public void idDocumentTextExtractionRetries_shouldOnlySetReclassification() {
        AttemptsConfiguration result = AttemptsConfiguration.builder()
                .withIdDocumentTextExtractionReclassificationRetries(1)
                .build();

        Map<String, Integer> idDocRetries = result.getIdDocumentTextDataExtraction();
        assertThat(idDocRetries, is(notNullValue()));
        assertThat(idDocRetries.get(RECLASSIFICATION), equalTo(1));
        assertThat(idDocRetries.get(GENERIC), is(nullValue()));
    }

    @Test
    public void idDocumentTextExtractionRetries_shouldOnlySetGeneric() {
        AttemptsConfiguration result = AttemptsConfiguration.builder()
                .withIdDocumentTextExtractionGenericRetries(1)
                .build();

        Map<String, Integer> idDocRetries = result.getIdDocumentTextDataExtraction();
        assertThat(idDocRetries, is(notNullValue()));
        assertThat(idDocRetries.get(GENERIC), equalTo(1));
        assertThat(idDocRetries.get(RECLASSIFICATION), is(nullValue()));
    }

    @Test
    public void idDocumentTextExtractionRetries_shouldSetBothValues() {
        AttemptsConfiguration result = AttemptsConfiguration.builder()
                .withIdDocumentTextExtractionGenericRetries(1)
                .withIdDocumentTextExtractionReclassificationRetries(2)
                .build();

        Map<String, Integer> idDocRetries = result.getIdDocumentTextDataExtraction();
        assertThat(idDocRetries, is(notNullValue()));
        assertThat(idDocRetries.get(GENERIC), equalTo(1));
        assertThat(idDocRetries.get(RECLASSIFICATION), equalTo(2));
    }

    @Test
    public void idDocumentTextExtractionRetries_shouldAllowValuesToBeOverwritten() {
        AttemptsConfiguration result = AttemptsConfiguration.builder()
                .withIdDocumentTextExtractionGenericRetries(1)
                .withIdDocumentTextExtractionReclassificationRetries(2)
                .withIdDocumentTextExtractionGenericRetries(3)
                .withIdDocumentTextExtractionReclassificationRetries(4)
                .build();

        Map<String, Integer> idDocRetries = result.getIdDocumentTextDataExtraction();
        assertThat(idDocRetries, is(notNullValue()));
        assertThat(idDocRetries.get(GENERIC), equalTo(3));
        assertThat(idDocRetries.get(RECLASSIFICATION), equalTo(4));
    }

}
