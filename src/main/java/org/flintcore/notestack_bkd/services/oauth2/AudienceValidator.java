package org.flintcore.notestack_bkd.services.oauth2;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Custom Audience validator.
 * */
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    public static final String THE_TOKEN_AUDIENCE_NOT_MATCH_CLIENT_ID =
            "The token audience doesn't match the required client ID",
            INVALID_TOKEN = "invalid_token";

    private final String audience;

    AudienceValidator(String audience) {
        this.audience = audience;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        }

        OAuth2Error oAuth2Error = new OAuth2Error(
                INVALID_TOKEN,
                THE_TOKEN_AUDIENCE_NOT_MATCH_CLIENT_ID,
                null
        );

        return OAuth2TokenValidatorResult.failure(oAuth2Error);
    }
}
