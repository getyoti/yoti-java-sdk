package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.AmlException;
import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.aml.AmlResult;
import com.yoti.api.client.qrcode.QrCode;
import com.yoti.api.client.qrcode.DynamicScenario;
import com.yoti.api.client.qrcode.QRCodeException;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.call.aml.RemoteAmlService;
import com.yoti.api.client.spi.remote.call.qrcode.RemoteQrCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * YotiClient talking to the Yoti Connect API remotely.
 */
final class SecureYotiClient implements YotiClient {

    private static final Logger LOG = LoggerFactory.getLogger(SecureYotiClient.class);

    private final String appId;
    private final KeyPair keyPair;
    private final ReceiptFetcher receiptFetcher;
    private final RemoteAmlService remoteAmlService;
    private final ActivityDetailsFactory activityDetailsFactory;
    private final RemoteQrCodeService remoteQrCodeService;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    SecureYotiClient(String applicationId,
            KeyPairSource kpSource,
            ReceiptFetcher receiptFetcher,
            ActivityDetailsFactory activityDetailsFactory,
            RemoteAmlService remoteAmlService,
            RemoteQrCodeService remoteQrCodeService) throws InitialisationException {
        this.appId = notNull(applicationId, "Application id");
        this.keyPair = loadKeyPair(notNull(kpSource, "Key pair source"));
        this.receiptFetcher = notNull(receiptFetcher, "receiptFetcher");
        this.remoteAmlService = notNull(remoteAmlService, "amlService");
        this.activityDetailsFactory = notNull(activityDetailsFactory, "activityDetailsFactory");
        this.remoteQrCodeService = notNull(remoteQrCodeService, "QR Code service");
    }

    @Override
    public ActivityDetails getActivityDetails(String encryptedConnectToken) throws ProfileException {
        Receipt receipt = receiptFetcher.fetch(encryptedConnectToken, keyPair, appId);
        return activityDetailsFactory.create(receipt, keyPair.getPrivate());
    }

    @Override
    public AmlResult performAmlCheck(AmlProfile amlProfile) throws AmlException {
        LOG.debug("Performing aml check...");
        return remoteAmlService.performCheck(keyPair, appId, amlProfile);
    }

    @Override
    public QrCode requestQRCode(DynamicScenario dynamicScenario) throws QRCodeException {
        LOG.debug("Requesting a QRCode...");
        return remoteQrCodeService.requestQRCode(appId, keyPair, dynamicScenario);
    }

    private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
        try {
            LOG.debug("Loading key pair from '{}'", kpSource);
            return kpSource.getFromStream(new KeyStreamVisitor());
        } catch (IOException e) {
            throw new InitialisationException("Cannot load key pair", e);
        }
    }

}
