package com.yoti.api.client.sandbox.docs.request.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxDocumentTextDataExtractionTaskTest {

    private static final String SOME_KEY = "someKey";
    private static final String SOME_VALUE = "someValue";
    private static final String SOME_CONTENT_TYPE = "someContentType";
    private static final byte[] SOME_DOCUMENT_ID_PHOTO = new byte[] { 1, 2, 3, 4 };
    private static final String EXPECTED_B64_DOCUMENT_ID_PHOTO = Base64.getEncoder().encodeToString(SOME_DOCUMENT_ID_PHOTO);
    private static final Map<String, Object> SOME_DOCUMENT_FIELDS;
    private static final String SOME_DETECTED_COUNTRY = "someDetectedCountry";

    static {
        SOME_DOCUMENT_FIELDS = new HashMap<>();
        SOME_DOCUMENT_FIELDS.put("firstKey", "firstValue");
        SOME_DOCUMENT_FIELDS.put("secondKey", 100);
    }

    @Mock SandboxDocumentFilter documentFilterMock;
    @Mock SandboxTextExtractionTaskRecommendation recommendationMock;

    @Test
    public void builder_shouldAllowIndividualDocumentFields() {
        SandboxDocumentTextDataExtractionTask result = SandboxDocumentTextDataExtractionTask.builder()
                .withDocumentField(SOME_KEY, SOME_VALUE)
                .build();

        assertThat(result.getResult()
                .getDocumentFields(), hasEntry(SOME_KEY, (Object) SOME_VALUE));
    }

    @Test
    public void builder_shouldAllowDocumentFieldsToBeSet() {
        SandboxDocumentTextDataExtractionTask result = SandboxDocumentTextDataExtractionTask.builder()
                .withDocumentFields(SOME_DOCUMENT_FIELDS)
                .build();

        assertThat(result.getResult()
                .getDocumentFields(), hasEntry("firstKey", (Object) "firstValue"));
        assertThat(result.getResult()
                .getDocumentFields(), hasEntry("secondKey", (Object) 100));
    }

    @Test
    public void builder_shouldAllowDocumentFilter() {
        SandboxDocumentTextDataExtractionTask result = SandboxDocumentTextDataExtractionTask.builder()
                .withDocumentField(SOME_KEY, SOME_VALUE)
                .withDocumentFilter(documentFilterMock)
                .build();

        assertThat(result.getDocumentFilter(), is(documentFilterMock));
    }

    @Test
    public void builder_shouldEncodeDocumentIdPhotoToBase64() {
        SandboxDocumentTextDataExtractionTask result = SandboxDocumentTextDataExtractionTask.builder()
                .withDocumentIdPhoto(SOME_CONTENT_TYPE, SOME_DOCUMENT_ID_PHOTO)
                .build();

        SandboxDocumentIdPhoto documentIdPhoto = result.getResult().getDocumentIdPhoto();
        assertThat(documentIdPhoto.getContentType(), is(SOME_CONTENT_TYPE));
        assertThat(documentIdPhoto.getData(), is(EXPECTED_B64_DOCUMENT_ID_PHOTO));
    }

    @Test
    public void builder_shouldAllowDetectedCountry() {
        SandboxDocumentTextDataExtractionTask result = SandboxDocumentTextDataExtractionTask.builder()
                .withDetectedCountry(SOME_DETECTED_COUNTRY)
                .build();

        assertThat(result.getResult().getDetectedCountry(), is(SOME_DETECTED_COUNTRY));
    }

    @Test
    public void builder_shouldAllowRecommendation() {
        SandboxDocumentTextDataExtractionTask result = SandboxDocumentTextDataExtractionTask.builder()
                .withRecommendation(recommendationMock)
                .build();

        assertThat(result.getResult().getRecommendation(), is(recommendationMock));
    }

    @Test
    public void builder_shouldSetResponseDelay() {
        SandboxDocumentTextDataExtractionTask result = SandboxDocumentTextDataExtractionTask.builder()
                .withResponseDelay(10)
                .build();

        assertThat(result.getResponseDelay(), is(10));
    }

    @Test
    public void builder_shouldSetResultTemplate() {
        SandboxDocumentTextDataExtractionTask result = SandboxDocumentTextDataExtractionTask.builder()
                .withResultTemplate("someResultTemplate")
                .build();

        assertThat(result.getResultTemplate(), is("someResultTemplate"));
        assertThat(result.getResult(), is(nullValue()));
    }

}
