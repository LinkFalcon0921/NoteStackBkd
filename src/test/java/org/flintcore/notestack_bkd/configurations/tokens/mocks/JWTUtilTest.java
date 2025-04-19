package org.flintcore.notestack_bkd.configurations.tokens.mocks;

import com.auth0.jwt.interfaces.Claim;
import org.flintcore.notestack_bkd.configurations.properties.AuthTokenProperties;
import org.flintcore.notestack_bkd.configurations.properties.SecretsProperties;
import org.flintcore.notestack_bkd.configurations.properties.SpringProperties;
import org.flintcore.notestack_bkd.configurations.tokens.JWTUtil;
import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JWTUtilTest {

    public static final String APP_NAME = "appName",
            SECRET = "Secret token done!";
    private static final long EXPIRATION_TIME = 20,
            REFRESH_TIME = 40,
            TOLERANCE_TIME = 1;

    @Mock
    private SpringProperties springProperties;

    @Mock
    private SpringProperties.Application springApplicationProperties;

    @Mock
    private AuthTokenProperties authTokenProperties;

    @Mock
    private SecretsProperties secretsProperties;

    @InjectMocks
    private JWTUtil jwtUtil;

    @Test
    @DisplayName("Create and validate token.")
    void shouldCreateUserTokenAndValidate() {
        setDefaultMocks();

        var localAuthentication = new LocalProviderDTO(
                "test@gamil.com", UUID.randomUUID().toString()
        );

        var tokenValue = jwtUtil.createToken(localAuthentication);

        verify(springProperties).application();
        verify(authTokenProperties).expirationTime();
        verify(secretsProperties).secret();

        assertNotNull(tokenValue);

        tokenValue.map(jwtUtil::isValid)
                .ifPresentOrElse(Assertions::assertTrue, Assertions::fail);

        verify(authTokenProperties).toleranceTime();
    }

    @Test
    @DisplayName("Validate token creation and validate credentials")
    void shouldCreateUserTokenAndValidateCredentials() {
        setDefaultMocks();

        var localAuthentication = new LocalProviderDTO(
                "test@gamil.com", UUID.randomUUID().toString()
        );

        var tokenValue = jwtUtil.createToken(localAuthentication);

        verify(springProperties).application();
        verify(authTokenProperties).expirationTime();
        verify(secretsProperties).secret();

        assertNotNull(tokenValue);

        tokenValue.map(jwtUtil::validateToken)
                .ifPresentOrElse(tk -> {
                    assertEquals(localAuthentication.email(), tk.getSubject());
                    assertEquals(APP_NAME, tk.getIssuer());

                    // Contains the user password stored in token
                    assertTrue(
                            tk.getClaims().values()
                                    .stream().map(Claim::asString)
                                    .noneMatch(v -> Objects.equals(v, localAuthentication.password()))
                    );
                }, Assertions::fail);

        verify(authTokenProperties).toleranceTime();
    }

    private void setDefaultMocks() {
        given(springProperties.application())
                .willReturn(springApplicationProperties);

        given(springApplicationProperties.name())
                .willReturn(APP_NAME);

        given(secretsProperties.secret())
                .willReturn(SECRET);

        given(authTokenProperties.expirationTime())
                .willReturn(Duration.ofSeconds(EXPIRATION_TIME));
    }
}