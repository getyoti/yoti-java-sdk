package com.yoti.api.client.spi.remote.call;

import java.security.KeyPair;
import com.yoti.api.client.qrcode.DynamicScenario;
import com.yoti.api.client.qrcode.QRCodeException;
import com.yoti.api.client.spi.remote.call.qrcode.SimpleQrCode;

public interface QrCodeService {

    SimpleQrCode requestQRCode(String appId, KeyPair keyPair, DynamicScenario dynamicScenario) throws QRCodeException;

}
