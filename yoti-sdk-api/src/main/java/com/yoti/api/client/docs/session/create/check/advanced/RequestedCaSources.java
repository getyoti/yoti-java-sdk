package com.yoti.api.client.docs.session.create.check.advanced;

import static com.yoti.api.client.docs.DocScanConstants.PROFILE;
import static com.yoti.api.client.docs.DocScanConstants.TYPE_LIST;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestedTypeListSources.class, name = TYPE_LIST),
        @JsonSubTypes.Type(value = RequestedSearchProfileSources.class, name = PROFILE)
})
public abstract class RequestedCaSources {

}
