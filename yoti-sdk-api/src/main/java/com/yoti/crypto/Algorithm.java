package com.yoti.crypto;

public enum Algorithm {

    AES("AES");

    private final String value;

    Algorithm(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
