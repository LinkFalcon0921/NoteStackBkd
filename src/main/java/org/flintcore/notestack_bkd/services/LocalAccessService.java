package org.flintcore.notestack_bkd.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flintcore.notestack_bkd.configurations.tokens.JWTUtil;
import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.flintcore.notestack_bkd.dtos.requests.LocalRegistrationDTO;
import org.flintcore.notestack_bkd.dtos.responses.TokenAuthResponse;
import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.flintcore.notestack_bkd.entities.User;
import org.flintcore.notestack_bkd.entities.auths.AuthProvider;
import org.flintcore.notestack_bkd.exceptions.auths.OAuth2Exception;
import org.flintcore.notestack_bkd.mappers.IAuthProviderMapper;
import org.flintcore.notestack_bkd.mappers.IRefreshTokenMapper;
import org.flintcore.notestack_bkd.mappers.IUserMapper;
import org.flintcore.notestack_bkd.repositories.IAuthProviderRepository;
import org.flintcore.notestack_bkd.repositories.IRefreshTokenRepository;
import org.flintcore.notestack_bkd.repositories.IUserRepository;
import org.flintcore.notestack_bkd.services.auths.IAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.Set;

/**
 * Class responsible to validate and create local users access.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocalAccessService implements IAccessService<LocalProviderDTO, LocalRegistrationDTO> {

    private static final EAuthProvider PROVIDER = EAuthProvider.LOCAL;

    // Users
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    // Auth providers
    private final IAuthProviderRepository authProviderRepository;
    private final IAuthProviderMapper authProviderMapper;

    // Refresh token
    private final IRefreshTokenRepository refreshTokenRepository;
    private final IRefreshTokenMapper refreshTokenMapper;

    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // TODO

    @Override
    @Transactional
    public TokenAuthResponse register(LocalRegistrationDTO userRegistration) {
        LocalProviderDTO authRequest = userRegistration.localAuthRequest();
        var email = authRequest.email();

        if (authProviderRepository.existsByProviderNameAndEmail(PROVIDER, email)) {
            throw new OAuth2Exception.OAuth2EmailRegistrationException(email);
        }

        User user = userMapper.toEntity(userRegistration.user());

        // Encode the password
        authRequest = new LocalProviderDTO(
                email, passwordEncoder.encode(authRequest.password())
        );

        AuthProvider authProvider = authProviderMapper.toEntity(authRequest);

        user.setAuthProviders(Set.of(authProvider));

        // Store user
        userRepository.save(user);

        // Handle refresh token
        var refreshToken = createRefreshAccessToken(authRequest, user);

        // Store token for further cases.
        refreshTokenRepository.save(
                refreshTokenMapper.with(refreshToken, user)
        );
        // TODO Store in database

        var token = createAccessToken(authRequest, user);

        return new TokenAuthResponse(
                token,
                refreshToken
        );
    }

    @Override
    @Transactional(readOnly = true)
    public TokenAuthResponse authenticate(LocalProviderDTO localAuth) {
        var localCredentials = authProviderRepository.findByCredentials(
                localAuth.email(),
                passwordEncoder.encode(localAuth.password())
        ).orElseThrow(() -> new OAuth2Exception.OAuth2AuthenticationException(
                localAuth.email(), PROVIDER.name()
        ));

        var lastRefreshToken = refreshTokenRepository.findTokenByUser(
                localCredentials.getUser()
        ).orElseThrow(() -> new ResourceAccessException("User not authorized."));

        if (!jwtUtil.isValid(lastRefreshToken.getToken())) {
            // If token expires or not present, update it.
            lastRefreshToken.setToken(
                    createRefreshAccessToken(localAuth, localCredentials.getUser())
            );
        }

        var token = createAccessToken(localAuth, localCredentials.getUser());

        // Store last refresh token.
        refreshTokenRepository.save(lastRefreshToken);

        return new TokenAuthResponse(
                token, lastRefreshToken.getToken()
        );
    }

    private String createRefreshAccessToken(LocalProviderDTO authRequest, User user) {
        return jwtUtil.createRefreshToken(authRequest)
                .orElseThrow(() -> {
                    log.warn("Could not generate refresh token for user {}", user);
                    return new ResourceAccessException("Unable to authenticate user");
                });
    }

    private String createAccessToken(LocalProviderDTO authRequest, User user) {
        return jwtUtil.createToken(authRequest)
                .orElseThrow(() -> {
                    log.warn("Could not generate token for user {}", user);
                    return new ResourceAccessException("Unable to authenticate user");
                });
    }
}
