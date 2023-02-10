package com.yoti.api.client.spi.remote.call;

public class ResourceException extends Exception {

    private final int responseCode;
    private final String responseBody;

    public ResourceException(int responseCode, String responseMessage, String responseBody) {
        super(responseMessage);
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public ResourceException(int responseCode, String responseMessage) {
        super(responseMessage);
        this.responseCode = responseCode;
        responseBody = null;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String toString() {
        return String.format("%s, code: %s, body: %s", super.toString(), responseCode, responseBody);
    }

}
