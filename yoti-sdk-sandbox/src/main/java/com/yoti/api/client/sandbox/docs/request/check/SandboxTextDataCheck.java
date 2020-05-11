package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SandboxTextDataCheck extends SandboxDocumentCheck {

    SandboxTextDataCheck(SandboxTextDataCheckResult result, SandboxDocumentFilter documentFilter) {
        super(result, documentFilter);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public SandboxTextDataCheckResult getResult() {
        return (SandboxTextDataCheckResult) super.getResult();
    }

    /**
     * Builder for {@link SandboxTextDataCheck}
     */
    public static class Builder extends SandboxDocumentCheckBuilder<Builder> {

        private Map<String, Object> documentFields;

        public Builder withDocumentField(String key, Object value) {
            if (documentFields == null) {
                documentFields = new HashMap<>();
            }

            this.documentFields.put(key, value);
            return self();
        }

        public Builder withDocumentFields(Map<String, Object> documentFields) {
            this.documentFields = documentFields;
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SandboxTextDataCheck build() {
            notNull(recommendation, "recommendation");
            notNull(breakdown, "breakdown");
            notNull(documentFields, "documentFields");

            SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
            SandboxTextDataCheckResult result = new SandboxTextDataCheckResult(report, documentFields);

            return new SandboxTextDataCheck(result, documentFilter);
        }

    }
}
