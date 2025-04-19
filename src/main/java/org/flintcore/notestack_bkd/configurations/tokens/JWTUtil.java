package org.flintcore.notestack_bkd.configurations.tokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.flintcore.notestack_bkd.configurations.properties.AuthTokenProperties;
import org.flintcore.notestack_bkd.configurations.properties.SecretsProperties;
import org.flintcore.notestack_bkd.configurations.properties.SpringProperties;
import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Scope("prototype")
@Slf4j
public class JWTUtil {

    private final SpringProperties appProperties;
    private final AuthTokenProperties tokenProperties;
    private final SecretsProperties secretsProperties;

    protected Algorithm signAlgorithm;

    private Optional<String> createToken(LocalProviderDTO providerData, Supplier<Long> seconds) {
        Instant instant = Instant.now();

        var tokenBuilder = JWT.create()
                .withIssuer(getAppIssuer()) // Set application name
                .withSubject(providerData.email()) // Use user email
                .withIssuedAt(instant)
                .withExpiresAt(
                        instant.plusSeconds(seconds.get())
                );

        return Optional.of(tokenBuilder.sign(getSignAlgorithm()));
    }

    public Optional<String> createRefreshToken(LocalProviderDTO localProvider) {
        return createToken(
                new LocalProviderDTO(localProvider.email(), null),
                () -> tokenProperties.refreshTime().toSeconds()
        );
    }

    public Optional<String> createToken(LocalProviderDTO localProvider) {
        return createToken(
                new LocalProviderDTO(localProvider.email(), null),
                () -> tokenProperties.expirationTime().toSeconds()
        );
    }

    public boolean isValid(String token) {
        try {
            validateToken(token);
        } catch (RuntimeException e) {
            log.error("JWT validation failed", e);
            return false;
        }

        return true;
    }

    /**
     * Throws an exception
     */
    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        if(Objects.isNull(token))
            return null;

        return JWT.require(this.getSignAlgorithm())
                .withIssuer(this.getAppIssuer())
                .acceptNotBefore(tokenProperties.toleranceTime().toSeconds())
                .build()
                .verify(token);
    }

    protected String getAppIssuer() {
        return appProperties.application().name();
    }

    protected Algorithm getSignAlgorithm() {
        return signAlgorithm = ObjectUtils.getIfNull(signAlgorithm, this::initSignAlgorithm);
    }

    // Start the instance of secret
    protected Algorithm initSignAlgorithm() {
        return Algorithm.HMAC512(secretsProperties.secret());
    }
}
