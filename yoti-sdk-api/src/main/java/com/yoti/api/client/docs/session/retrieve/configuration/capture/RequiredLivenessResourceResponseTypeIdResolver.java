package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import static com.yoti.api.client.docs.DocScanConstants.ZOOM;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

public class RequiredLivenessResourceResponseTypeIdResolver extends TypeIdResolverBase {

    private JavaType superType;

    @Override
    public void init(JavaType baseType) {
        superType = baseType;
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) {
        Class<?> subType;
        if (ZOOM.equals(id)) {
            subType = RequiredZoomLivenessResourceResponse.class;
        } else {
            subType = UnknownRequiredLivenessResourceResponse.class;
        }
        return context.constructSpecializedType(superType, subType);
    }

    @Override
    public String idFromValue(Object value) {
        return null;
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return null;
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }

}
