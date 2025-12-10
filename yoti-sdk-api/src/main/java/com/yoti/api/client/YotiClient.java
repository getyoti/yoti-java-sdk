package com.yoti.api.client;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.aml.AmlResult;
import com.yoti.api.client.shareurl.DynamicScenario;
import com.yoti.api.client.shareurl.DynamicShareException;
import com.yoti.api.client.shareurl.ShareUrlResult;
import com.yoti.api.client.spi.remote.ActivityDetailsFactory;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.ReceiptFetcher;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.call.aml.RemoteAmlService;
import com.yoti.api.client.spi.remote.call.qrcode.DynamicSharingService;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Entry point to interact with the Yoti Connect API.
 * </p>
 * <p>
 * It can be safely cached and shared even by multiple threads.
 * </p>
 */
public class YotiClient {

    private static final Logger LOG = LoggerFactory.getLogger(YotiClient.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final String appId;
    private final KeyPair keyPair;
    private final ReceiptFetcher receiptFetcher;
    private final RemoteAmlService remoteAmlService;
    private final ActivityDetailsFactory activityDetailsFactory;
    private final DynamicSharingService dynamicSharingService;

    YotiClient(String applicationId,
            KeyPair keyPair,
            ReceiptFetcher receiptFetcher,
            RemoteAmlService remoteAmlService,
            ActivityDetailsFactory activityDetailsFactory,
            DynamicSharingService dynamicSharingService) throws InitialisationException {
        this.appId = notNull(applicationId, "Application id");
        this.keyPair = keyPair;
        this.receiptFetcher = notNull(receiptFetcher, "receiptFetcher");
        this.remoteAmlService = notNull(remoteAmlService, "amlService");
        this.activityDetailsFactory = notNull(activityDetailsFactory, "activityDetailsFactory");
        this.dynamicSharingService = notNull(dynamicSharingService, "QR Code service");
    }

    /**
     * Creates a new {@link YotiClient.Builder} to assist with the creation
     * of the YotiClient
     *
     * @return the new builder
     */
    public static YotiClient.Builder builder() {
        return new YotiClient.Builder();
    }

    /**
     * Get the activity details for a token. Amongst others contains the user profile with the user's attributes you
     * have selected in your application configuration on Yoti Portal.
     *
     * <b>Note: encrypted tokens should only be used once. You should not invoke this method multiple times with the same token.</b>
     *
     * @param encryptedYotiToken encrypted Yoti token (can be only decrypted with your application's private key). Note that this token must only be used once.
     * @return an {@link ActivityDetails} instance holding the user's attributes
     * @throws ProfileException aggregate exception signalling issues during the call
     */
    public ActivityDetails getActivityDetails(String encryptedYotiToken) throws ProfileException {
        Receipt receipt = receiptFetcher.fetch(encryptedYotiToken, keyPair);
        return activityDetailsFactory.create(receipt, keyPair.getPrivate());
    }

    /**
     * Request an AML check for the given profile.
     *
     * @param amlProfile
     *            Details of the profile to search for when performing the AML check
     * @return an {@link AmlProfile} with the results of the check
     *
     * @throws AmlException
     *             aggregate exception signalling issues during the call
     */
    public AmlResult performAmlCheck(AmlProfile amlProfile) throws AmlException {
        LOG.debug("Performing aml check...");
        return remoteAmlService.performCheck(amlProfile);
    }

    /**
     * Initiate a sharing process based on a dynamic scenario and policy
     *
     * @param  dynamicScenario
     *             Details of the device's callback endpoint, dynamic policy and extensions for the application
     *
     * @return an {@link ShareUrlResult}
     *             sharing url and reference id
     *
     * @throws DynamicShareException
     *             aggregate exception signalling issues during the call
     */
    public ShareUrlResult createShareUrl(DynamicScenario dynamicScenario) throws DynamicShareException {
        LOG.debug("Request a share url for a dynamicScenario...");
        return dynamicSharingService.createShareUrl(appId, dynamicScenario);
    }

    public static class Builder {

        private String sdkId;
        private KeyPairSource keyPairSource;

        private Builder() {
        }

        public Builder withClientSdkId(String sdkId) {
            this.sdkId = sdkId;
            return this;
        }

        public Builder withKeyPair(KeyPairSource keyPairSource) {
            this.keyPairSource = keyPairSource;
            return this;
        }

        public YotiClient build() {
            checkBuilderState();
            KeyPair keyPair = loadKeyPair(notNull(keyPairSource, "Key pair source"));

            return new YotiClient(
                    sdkId,
                    keyPair,
                    ReceiptFetcher.newInstance(keyPair, sdkId),
                    RemoteAmlService.newInstance(keyPair, sdkId),
                    ActivityDetailsFactory.newInstance(),
                    DynamicSharingService.newInstance(keyPair)
            );
        }

        private void checkBuilderState() {
            if (keyPairSource == null) {
                throw new IllegalStateException("No key pair supplied");
            }
            if (sdkId == null) {
                throw new IllegalStateException("No SDK ID supplied");
            }
        }

        private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
            try {
                return kpSource.getFromStream(new KeyStreamVisitor());
            } catch (IOException e) {
                throw new InitialisationException("Cannot load key pair", e);
            }
        }

    }

}
