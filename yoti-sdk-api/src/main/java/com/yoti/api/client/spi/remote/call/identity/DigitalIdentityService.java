package com.yoti.api.client.spi.remote.call.identity;

public class DigitalIdentityService {

    public static DigitalIdentityService newInstance() {
        return new DigitalIdentityService();
    }

    public Object createShareSession() {
        return null;
    }

    public Object fetchShareSession(String sessionId) {
        return null;
    }

    public Object createShareQrCode(String sessionId) {
        return null;
    }

    public Object fetchShareQrCode(String qrCodeId) {
        return null;
    }

    public Object fetchShareReceipt(String receiptId) {
        return null;
    }

}
