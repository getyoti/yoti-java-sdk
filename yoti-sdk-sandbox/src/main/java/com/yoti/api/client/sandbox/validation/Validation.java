package com.yoti.api.client.sandbox.validation;

import com.google.common.base.Strings;

public class Validation {

    public static void checkNotNullOrEmpty(String value, String item){
        if(Strings.isNullOrEmpty(value)){
            throw new IllegalArgumentException(item + " must not be empty or null");
        }
    }

}
