package org.flintcore.notestack_bkd.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.flintcore.notestack_bkd.dtos.OAuthProviderDTO;
import org.flintcore.notestack_bkd.dtos.UserDTO;
import org.springframework.lang.NonNull;

public record OAuthRegistrationDTO(
        @NonNull UserDTO user,
        @NonNull @JsonAlias({ "auth", "authCredentials", "authCredential", "oauth" })
        OAuthProviderDTO oauthProvider
) {}
