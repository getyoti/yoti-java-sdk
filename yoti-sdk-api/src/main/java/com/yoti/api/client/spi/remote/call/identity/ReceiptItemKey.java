package com.yoti.api.client.spi.remote.call.identity;

import java.util.Base64;

import javax.crypto.spec.IvParameterSpec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = ReceiptItemKey.Builder.class)
public final class ReceiptItemKey {

    private final String id;
    private final IvParameterSpec iv;
    private final byte[] value;

    private ReceiptItemKey(Builder builder) {
        id = builder.id;
        iv = builder.iv;
        value = builder.value;
    }

    public String getId() {
        return id;
    }

    public IvParameterSpec getIv() {
        return iv;
    }

    public byte[] getValue() {
        return value.clone();
    }

    public static final class Builder {

        private String id;
        private IvParameterSpec iv;
        private byte[] value;

        private Builder() { }

        @JsonProperty(Property.ID)
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        @JsonProperty(Property.IV)
        public Builder iv(String iv) {
            this.iv = new IvParameterSpec(decode(iv));
            return this;
        }

        @JsonProperty(Property.VALUE)
        public Builder value(String value) {
            this.value = decode(value);
            return this;
        }

        public ReceiptItemKey build() {
            return new ReceiptItemKey(this);
        }

        private static byte[] decode(String value) {
            return Base64.getDecoder().decode(value);
        }

    }

    private static class Property {

        private static final String ID = "id";
        private static final String IV = "iv";
        private static final String VALUE = "value";

    }

}
