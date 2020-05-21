package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SandboxDocumentTextDataCheck extends SandboxDocumentCheck {

    SandboxDocumentTextDataCheck(SandboxDocumentTextDataCheckResult result, SandboxDocumentFilter documentFilter) {
        super(result, documentFilter);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public SandboxDocumentTextDataCheckResult getResult() {
        return (SandboxDocumentTextDataCheckResult) super.getResult();
    }

    /**
     * Builder for {@link SandboxDocumentTextDataCheck}
     */
    public static class Builder extends SandboxDocumentCheckBuilder<Builder> {

        private Map<String, Object> documentFields;

        private Builder() {}

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
        public SandboxDocumentTextDataCheck build() {
            notNull(recommendation, "recommendation");
            notNull(documentFields, "documentFields");

            SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
            SandboxDocumentTextDataCheckResult result = new SandboxDocumentTextDataCheckResult(report, documentFields);

            return new SandboxDocumentTextDataCheck(result, documentFilter);
        }

    }
}
