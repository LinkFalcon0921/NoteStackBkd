package org.flintcore.notestack_bkd.services.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.WeakHashMap;

@Service
@RequiredArgsConstructor
@Lazy
@Slf4j
public class JwtDecoderFactory {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private Map<EAuthProvider, JwtDecoder> decoders;


    public JwtDecoder getDecoder(EAuthProvider registrationId) {
        return getDecoders().computeIfAbsent(registrationId, this::createDecoder);
    }

    private JwtDecoder createDecoder(EAuthProvider provider) {
        ClientRegistration clientRegistration = clientRegistrationRepository
                .findByRegistrationId(provider.asRegistrationId());

        if (clientRegistration == null) {
            throw new IllegalArgumentException("Unknown client registration: " + provider);
        }

        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withJwkSetUri(clientRegistration.getProviderDetails().getJwkSetUri())
                .build();

        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(
                new JwtTimestampValidator(),
                new JwtIssuerValidator(clientRegistration.getProviderDetails().getIssuerUri()),
                new AudienceValidator(clientRegistration.getClientId()));

        decoder.setJwtValidator(validator);

        return decoder;
    }

    private final Map<EAuthProvider, JwtDecoder> getDecoders() {
        return decoders = ObjectUtils.getIfNull(decoders, WeakHashMap::new);
    }
}
