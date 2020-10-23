package com.yoti.api.client.docs.session.create.filters;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.List;

import com.yoti.api.client.docs.session.create.objective.Objective;

public class SimpleRequiredSupplementaryDocumentBuilder implements RequiredSupplementaryDocumentBuilder {

    private Objective objective;
    private List<String> documentTypes;
    private List<String> countryCodes;

    @Override
    public RequiredSupplementaryDocumentBuilder withObjective(Objective objective) {
        this.objective = objective;
        return this;
    }

    @Override
    public RequiredSupplementaryDocumentBuilder withDocumentTypes(List<String> documentTypes) {
        notNull(documentTypes, "documentTypes");
        this.documentTypes = documentTypes;
        return this;
    }

    @Override
    public RequiredSupplementaryDocumentBuilder withCountryCodes(List<String> countryCodes) {
        notNull(countryCodes, "countryCodes");
        this.countryCodes = countryCodes;
        return this;
    }

    @Override
    public RequiredSupplementaryDocument build() {
        notNull(objective, "objective");
        return new SimpleRequiredSupplementaryDocument(objective, documentTypes, countryCodes);
    }
}
