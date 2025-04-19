package org.flintcore.notestack_bkd.services.oauth2;

import lombok.RequiredArgsConstructor;
import org.flintcore.notestack_bkd.dtos.OAuthProviderDTO;
import org.flintcore.notestack_bkd.exceptions.auths.OAuth2Exception;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OauthTokenValidator {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final JwtDecoderFactory decoderFactory;

    public Jwt validateToken(OAuthProviderDTO tokenRequest) {
        String registrationId = tokenRequest.providerName().asRegistrationId();

        ClientRegistration clientRegistration = clientRegistrationRepository
                .findByRegistrationId(registrationId);

        if (Objects.isNull(clientRegistration)) {
            throw new OAuth2Exception.OAuth2AuthenticationException(
                    "Invalid client registration: " + registrationId, registrationId
            );
        }

        JwtDecoder jwtDecoder = decoderFactory.getDecoder(tokenRequest.providerName());

        var idToken = tokenRequest.accessToken();

        return jwtDecoder.decode(idToken);
    }
}
