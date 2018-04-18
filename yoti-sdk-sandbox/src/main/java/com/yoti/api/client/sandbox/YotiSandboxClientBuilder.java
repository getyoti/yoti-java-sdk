package com.yoti.api.client.sandbox;

import java.security.KeyPair;

import com.yoti.api.client.FileKeyPairSource;
import com.yoti.api.client.sandbox.validation.Validation;

import com.fasterxml.jackson.databind.ObjectMapper;

public class YotiSandboxClientBuilder {

    private String appId;
    private KeyPair keyPair;

    public static YotiSandboxClientBuilder newBuilder(){
        return new YotiSandboxClientBuilder();
    }

    public YotiSandboxClientBuilder appId(String appId) {
        this.appId = appId;
        return this;
    }

    public YotiSandboxClientBuilder fileKeyPairSource(FileKeyPairSource keyPair) {
        this.keyPair = KeyPairLoader.loadKeyPair(keyPair);
        return this;
    }

    public YotiSandboxClient build() {
        Validation.checkNotNullOrEmpty(appId, "Application Id");
        return new YotiSandboxClient(appId, keyPair, new ObjectMapper());
    }
}
