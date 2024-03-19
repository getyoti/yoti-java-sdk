package com.yoti.api.client.common;

public final class Share {

    public static final class Policy {

        public static final class Profile {

            private Profile() { }

            public static final String TYPE = "type";
            public static final String OBJECTIVE = "objective";
            public static final String TRUST_FRAMEWORK = "trust_framework";

            // identity profile only
            public static final String SCHEME = "scheme";
            public static final String IDENTITY_PROFILE_REQUIREMENTS = "identity_profile_requirements";

            // advanced identity profile only
            public static final String LABEL = "label";
            public static final String FILTER = "filter";
            public static final String CONFIG = "config";
            public static final String SCHEMES = "schemes";
            public static final String PROFILES = "profiles";
            public static final String DOCUMENTS = "documents";
            public static final String INCLUSION = "inclusion";
            public static final String COUNTRY_CODES = "country_codes";
            public static final String DOCUMENT_TYPES = "document_types";
            public static final String ADVANCED_IDENTITY_PROFILE_REQUIREMENTS = "advanced_identity_profile_requirements";

        }

    }

}
