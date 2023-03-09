package com.yoti.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public enum CipherType {

    AES_GCM("AES/GCM/NoPadding"),
    AES_CBC("AES/CBC/PKCS7Padding"),
    RSA_NONE_PKCS1("RSA/NONE/PKCS1Padding");

    private final String value;

    CipherType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    Cipher getInstance() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(value);
    }

}
