package org.flintcore.notestack_bkd.services;

import org.flintcore.notestack_bkd.configurations.GeneralPropertiesConfig;
import org.flintcore.notestack_bkd.configurations.MapStructConfig;
import org.flintcore.notestack_bkd.configurations.PasswordBeanConfig;
import org.flintcore.notestack_bkd.configurations.tokens.JWTUtil;
import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.flintcore.notestack_bkd.dtos.OAuthProviderDTO;
import org.flintcore.notestack_bkd.dtos.UserDTO;
import org.flintcore.notestack_bkd.dtos.requests.LocalRegistrationDTO;
import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.flintcore.notestack_bkd.exceptions.auths.OAuth2Exception;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({
        GeneralPropertiesConfig.class,
        MapStructConfig.class,
        PasswordBeanConfig.class,
        LocalAccessService.class,
        JWTUtil.class
})
@Disabled("Not ready yet. DataJPATest cannot override other settings.")
class LocalAccessServiceTest {

    private static final String EMAIL_FORMAT = "%s@mail.com";
    private static final EAuthProvider DEFAULT_PROVIDER = EAuthProvider.LOCAL;

    @Autowired
    private LocalAccessService localAccessService;

    @Test
    @DisplayName("Register a new local user")
    void createUserByLocalAuth() {
        LocalRegistrationDTO request = buildLocalRegistration();

        // TODO
        var tokenResponse = localAccessService.register(request);

        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.accessToken());
        assertNotNull(tokenResponse.refreshToken());
    }

    @Test
    @DisplayName("Throws error by duplicate user.")
    void handleDuplicatedUsers() {
        LocalRegistrationDTO request = buildLocalRegistration();

        // TODO
        var tokenResponse = localAccessService.register(request);

        assertThrows(
                OAuth2Exception.OAuth2EmailRegistrationException.class,
                () -> localAccessService.register(request)
        );
    }

    private LocalRegistrationDTO buildLocalRegistration() {
        UserDTO user = getUser();
        return new LocalRegistrationDTO(user, getLocalProvider(user));
    }

    protected UserDTO getUser() {
        String ramdomName = UUID.randomUUID().toString();

        return new UserDTO(
                null,
                ramdomName,
                LocalDateTime.now()
        );
    }

    protected LocalProviderDTO getLocalProvider(UserDTO user) {
        return new LocalProviderDTO(
                EMAIL_FORMAT.formatted(user.username()),
                UUID.randomUUID().toString()
        );
    }

    protected OAuthProviderDTO getOAuthProvider() {
        return new OAuthProviderDTO(
                DEFAULT_PROVIDER,
                UUID.randomUUID().toString()
        );
    }
}