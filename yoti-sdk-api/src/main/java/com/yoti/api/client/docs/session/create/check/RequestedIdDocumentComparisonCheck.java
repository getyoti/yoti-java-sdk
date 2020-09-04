package com.yoti.api.client.docs.session.create.check;

public interface RequestedIdDocumentComparisonCheck<T extends RequestedIdDocumentComparisonConfig> extends RequestedCheck<T> {

    @Override
    T getConfig();

}
