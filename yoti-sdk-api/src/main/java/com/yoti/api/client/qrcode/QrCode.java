package com.yoti.api.client.qrcode;

/**
 * Dynamic QRCode for a customised policy for an application
 *
 */
public interface QrCode {

    /**
     * Get the dynamic QRCode URL
     *
     * @return base64 QRCode URL
     */
    String getQrCode();

    /**
     * Get the Yoti reference id for the QRCode
     *
     * @return reference id for the generated QRCode
     */
    String getRefId();

}
