package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

public class YotiAnnotationIntrospector extends NopAnnotationIntrospector {

    private static final long serialVersionUID = 1L;

    @Override
    public Object findDeserializer(Annotated am) {
        if (am.getRawType().getAnnotation(JsonTypeInfo.class) != null) {
            return new AbstractClassWithTypeInfoDeserializer<>(am.getRawType());
        }
        return super.findDeserializer(am);
    }

}
