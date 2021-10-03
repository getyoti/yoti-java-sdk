package com.yoti.api.client.spi.remote.util;

import org.apache.commons.lang3.reflect.FieldUtils;

public class FieldSetter {

    public static void setField(Object object, String fieldName, Object value) {
        try {
            FieldUtils.writeField(object, fieldName, value, true);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

}
