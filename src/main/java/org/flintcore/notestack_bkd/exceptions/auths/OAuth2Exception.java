package org.flintcore.notestack_bkd.exceptions.auths;

import org.flintcore.notestack_bkd.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public sealed class OAuth2Exception extends ApiException {
    public OAuth2Exception(String message, HttpStatus status) {
        super(message, status);
    }

    public static final class OAuth2AuthenticationException extends OAuth2Exception {
        public static final String PROVIDER_CONNECTION_FAILED_DETAILS_FORMAT =
                "Unable to authenticate user with email %s using %s provider";

        public OAuth2AuthenticationException(String email, String provider) {
            super(PROVIDER_CONNECTION_FAILED_DETAILS_FORMAT.formatted(email, provider), HttpStatus.UNAUTHORIZED);
        }
    }

    /**Already registration exception*/
    public static final class OAuth2EmailRegistrationException extends OAuth2Exception {

        public static final String EMAIL_ALREADY_REGISTERED_OAUTH_FORMAT =
                "Email %s already registered but different provider";

        public OAuth2EmailRegistrationException(String email) {
            super(EMAIL_ALREADY_REGISTERED_OAUTH_FORMAT.formatted(email), HttpStatus.CONFLICT);
        }
    }

    public static final class OAuth2ProviderConnectionException extends OAuth2Exception {

        public static final String PROVIDER_CONNECTION_FAILED_FORMAT = "Error connecting to provider %s";

        public OAuth2ProviderConnectionException(String provider) {
            super(PROVIDER_CONNECTION_FAILED_FORMAT.formatted(provider), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
