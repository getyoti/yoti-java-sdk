package com.yoti.api.examples.springboot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MatchInputForm {

    private String value;
    private String endpoint;
    private String httpMethod;
    private boolean verifyTls;
    private List<String> headerKeys = new ArrayList<>();
    private List<String> headerValues = new ArrayList<>();

    public MatchInputForm() {
        value = "+442532733270";
        endpoint = "https://company.com/callback/did-match";
        httpMethod = "POST";
        verifyTls = true;
        headerKeys.add("X-Request-ID");
        headerValues.add(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public boolean isVerifyTls() {
        return verifyTls;
    }

    public void setVerifyTls(boolean verifyTls) {
        this.verifyTls = verifyTls;
    }

    public List<String> getHeaderKeys() {
        return headerKeys;
    }

    public void setHeaderKeys(List<String> headerKeys) {
        this.headerKeys = headerKeys;
    }

    public List<String> getHeaderValues() {
        return headerValues;
    }

    public void setHeaderValues(List<String> headerValues) {
        this.headerValues = headerValues;
    }

}
