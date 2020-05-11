package com.yoti.api.client.sandbox.docs.request.check;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;

abstract class SandboxDocumentCheckBuilder<T extends SandboxDocumentCheckBuilder<T>> extends SandboxCheckBuilder<T> {

    protected SandboxDocumentFilter documentFilter;

    public T withDocumentFilter(SandboxDocumentFilter documentFilter) {
        this.documentFilter = documentFilter;
        return self();
    }

}
