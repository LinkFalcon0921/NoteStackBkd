package org.flintcore.notestack_bkd.services.mock;

import org.flintcore.notestack_bkd.configurations.tokens.JWTUtil;
import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.flintcore.notestack_bkd.dtos.UserDTO;
import org.flintcore.notestack_bkd.dtos.requests.LocalRegistrationDTO;
import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.flintcore.notestack_bkd.entities.User;
import org.flintcore.notestack_bkd.entities.auths.LocalAuthProvider;
import org.flintcore.notestack_bkd.exceptions.auths.OAuth2Exception;
import org.flintcore.notestack_bkd.mappers.IAuthProviderMapper;
import org.flintcore.notestack_bkd.mappers.IRefreshTokenMapper;
import org.flintcore.notestack_bkd.mappers.IUserMapper;
import org.flintcore.notestack_bkd.repositories.IAuthProviderRepository;
import org.flintcore.notestack_bkd.repositories.IRefreshTokenRepository;
import org.flintcore.notestack_bkd.repositories.IUserRepository;
import org.flintcore.notestack_bkd.services.LocalAccessService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class LocalAccessServiceTest {

    private static final String EMAIL_FORMAT = "%s@mail.com";
    private static final EAuthProvider DEFAULT_PROVIDER = EAuthProvider.LOCAL;

    private final String RAW_PASSWORD = "password123";
    private final String ENCODED_PASSWORD = "encodedPassword123";
    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IUserMapper userMapper;

    // Auth providers
    @Mock
    private IAuthProviderRepository authProviderRepository;
    @Mock
    private IAuthProviderMapper authProviderMapper;

    // Refresh token
    @Mock
    private IRefreshTokenRepository refreshTokenRepository;

    @Mock
    private IRefreshTokenMapper refreshTokenMapper;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private LocalAccessService localAccessService;

    protected void setupSuccessCommonMocks() {
        given(passwordEncoder.encode(any()))
                .willReturn(ENCODED_PASSWORD);

        given(jwtUtil.createToken(any()))
                .willReturn(Optional.of(ACCESS_TOKEN));

        given(jwtUtil.createRefreshToken(any()))
                .willReturn(Optional.of(REFRESH_TOKEN));
    }
    // Caso POSITIVO: Registro exitoso

    @Test
    @DisplayName("Register new user")
    void register_WithNewEmail_ShouldReturnTokens() {
        this.setupSuccessCommonMocks();

        // Arrange
        var userSaved = getUser();

        LocalRegistrationDTO registrationDTO = buildLocalRegistration(userSaved);
        mockSuccessfulRegistration();

        // Act
        var response = localAccessService.register(registrationDTO);

        // Assert
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(ACCESS_TOKEN, response.accessToken()),
                () -> assertEquals(REFRESH_TOKEN, response.refreshToken()),
                () -> verify(userRepository).save(any(User.class)),
                () -> verify(refreshTokenRepository).save(any())
        );
    }

    // Caso NEGATIVO: Email ya registrado
    @Test
    @DisplayName("No register already registered users.")
    void register_WithExistingEmail_ShouldThrowException() {

        // Arrange
        var userSaved = getUser();
        LocalRegistrationDTO registrationDTO = buildLocalRegistration(userSaved);

        given(authProviderRepository.existsByProviderNameAndEmail(eq(DEFAULT_PROVIDER), any()))
                .willReturn(true);

        // Act & Assert
        assertThrows(OAuth2Exception.OAuth2EmailRegistrationException.class,
                () -> localAccessService.register(registrationDTO));

        verify(userRepository, never()).save(any(User.class));
    }

    // Caso POSITIVO: Codificación de contraseña
    @Test
    @DisplayName("Encode user password")
    void register_ShouldEncodePassword() {
        this.setupSuccessCommonMocks();


        // Arrange
        var userSaved = getUser();

        LocalRegistrationDTO registrationDTO = buildLocalRegistration(userSaved);
        mockSuccessfulRegistration();

        // Act
        localAccessService.register(registrationDTO);

        // Assert
        verify(passwordEncoder).encode(any());
    }

    private void mockSuccessfulRegistration() {
        User mockUser = new User();

        var mockAuthProvider = new LocalAuthProvider();

        given(userMapper.toEntity(any()))
                .willReturn(mockUser);

        given(authProviderMapper.toEntity(any(LocalProviderDTO.class)))
                .willReturn(mockAuthProvider);

        given(authProviderRepository.existsByProviderNameAndEmail(eq(DEFAULT_PROVIDER), any()))
                .willReturn(false);
    }

    private LocalRegistrationDTO buildLocalRegistration(UserDTO userTested) {
        return new LocalRegistrationDTO(
                userTested,
                getLocalProvider(userTested)
        );
    }

    protected UserDTO getUser() {
        String randomName = UUID.randomUUID().toString();

        return new UserDTO(
                null,
                randomName,
                LocalDateTime.now()
        );
    }

    protected LocalProviderDTO getLocalProvider(UserDTO user) {
        return new LocalProviderDTO(
                EMAIL_FORMAT.formatted(user.username()),
                UUID.randomUUID().toString()
        );
    }
}