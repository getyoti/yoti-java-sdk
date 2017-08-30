package com.yoti.api.client.spi.dummy;

import com.yoti.api.client.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DummyYotiClient implements YotiClient, YotiClientFactory {
    private static final String EXCEPTION_TRIGGER_PREFIX = "exception-";
    private static final String DUMMY_PREFIX = "dummy-";
    private static final ActivityDetails DUMMY_RECEIPT;

    static {
        final HashMap<String, String> attributes = new HashMap<String, String>();
        attributes.put("date_of_birth", "1964-11-23");
        attributes.put("given_names", "John");
        attributes.put("family_name", "Doe");
        final HumanProfile userProfile = new HumanProfile() {
            @Override
            public String getAttribute(String name) {
                return attributes.get(name);
            }

            @Override
            public boolean is(String name, boolean defaultValue) {
                return defaultValue;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getAttribute(String name, Class<T> clazz) {
                return String.class.equals(clazz) ? (T) getAttribute(name) : null;
            }

            @Override
            public Collection<Attribute> getAttributes() {
                Collection<Attribute> a = new HashSet<Attribute>();
                for (Map.Entry<String, String> e : attributes.entrySet()) {
                    a.add(new Attribute(e.getKey(), e.getValue()));
                }
                return a;
            }

            @Override
            public String getFamilyName() {
                return "Doe";
            }

            @Override
            public String getGivenNames() {
                return "John";
            }

            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public Date getDateOfBirth() {
                return null;
            }

            @Override
            public Gender getGender() {
                return null;
            }

            @Override
            public String getNationality() {
                return null;
            }

            @Override
            public String getPhoneNumber() {
                return null;
            }

            @Override
            public Image getSelfie() {
                return null;
            }

            @Override
            public String getEmailAddress() {
                return null;
            }

            @Override
            public DocumentDetails getDocumentDetails() {
                return null;
            }
        };

        final ApplicationProfile applicationProfile = new ApplicationProfile() {
            @Override
            public String getAttribute(String name) {
                return attributes.get(name);
            }

            @Override
            public boolean is(String name, boolean defaultValue) {
                return defaultValue;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getAttribute(String name, Class<T> clazz) {
                return String.class.equals(clazz) ? (T) getAttribute(name) : null;
            }

            @Override
            public Collection<Attribute> getAttributes() {
                Collection<Attribute> a = new HashSet<Attribute>();
                for (Map.Entry<String, String> e : attributes.entrySet()) {
                    a.add(new Attribute(e.getKey(), e.getValue()));
                }
                return a;
            }

            @Override
            public String getApplicationName() {
                return null;
            }

            @Override
            public String getApplicationUrl() {
                return null;
            }

            @Override
            public Image getApplicationLogo() {
                return null;
            }

            @Override
            public String getApplicationReceiptBgColor() {
                return null;
            }
        };

        DUMMY_RECEIPT = new ActivityDetails() {

            @Override
            public String getUserId() {
                return "YmFkYWRhZGEtZGFkYWJhZGEK";
            }

            @Override
            public HumanProfile getUserProfile() {
                return userProfile;
            }

            @Override
            public ApplicationProfile getApplicationProfile() {
                return applicationProfile;
            }

            @Override
            public java.util.Date getTimestamp() {
                return new java.util.Date();
            }

            @Override
            public String getReceiptId() {
                return "12345678";
            }

        };
    }

    @Override
    public ActivityDetails getActivityDetails(String encryptedConnectToken) throws ProfileException {
        if (encryptedConnectToken != null && encryptedConnectToken.startsWith(EXCEPTION_TRIGGER_PREFIX)) {
            throw new ProfileException(encryptedConnectToken);
        }
        return DUMMY_RECEIPT;
    }

    @Override
    public boolean accepts(String applicationId) {
        return applicationId.startsWith(DUMMY_PREFIX);
    }

    @Override
    public YotiClient getInstance(YotiClientConfiguration yotiClientBuilder) throws InitialisationException {
        return new DummyYotiClient();
    }

}
