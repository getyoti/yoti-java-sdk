package com.yoti.api.client.docs.session.create.filters;

public interface RequiredIdDocumentBuilder {

    RequiredIdDocumentBuilder withFilter(DocumentFilter filter);

    RequiredIdDocument build();

}
