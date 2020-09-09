package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

import com.yoti.api.client.docs.session.create.objective.Objective;

public interface RequiredSupplementaryDocumentBuilder {

    RequiredSupplementaryDocumentBuilder withObjective(Objective objective);

    RequiredSupplementaryDocumentBuilder withDocumentTypes(List<String> documentTypes);

    RequiredSupplementaryDocumentBuilder withCountryCodes(List<String> countryCodes);

    RequiredSupplementaryDocument build();

}
