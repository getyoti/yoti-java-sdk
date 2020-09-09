package com.yoti.api.client.docs.session.create.filters;

import java.util.List;

import com.yoti.api.client.docs.session.create.objective.Objective;

public interface RequiredSupplementaryDocument extends RequiredDocument {

    /**
     * Get the objective this required supplementary document
     * will satisfy
     *
     * @return the objective
     */
    Objective getObjective();

    /**
     * Get the list of document types that can be used
     * to satisfy this required supplementary document
     *
     * @return the list of document types
     */
    List<String> getDocumentTypes();

    /**
     * Get the list of country codes that can be used
     * to satisfy this required supplementary document
     *
     * @return the list of country codes
     */
    List<String> getCountryCodes();

}
