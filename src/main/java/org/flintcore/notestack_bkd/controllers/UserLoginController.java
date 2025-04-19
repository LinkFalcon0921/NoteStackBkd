package org.flintcore.notestack_bkd.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.flintcore.notestack_bkd.dtos.UserDTO;
import org.flintcore.notestack_bkd.dtos.requests.LocalRegistrationDTO;
import org.flintcore.notestack_bkd.dtos.requests.OAuthRegistrationDTO;
import org.flintcore.notestack_bkd.dtos.responses.TokenAuthResponse;
import org.flintcore.notestack_bkd.services.LocalAccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller that handle the Login endpoints
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserLoginController {

    // private final OauthTokenValidator oauthTokenValidator;

    private final LocalAccessService localAccessService;

    @PostMapping("/local/register")
    public TokenAuthResponse registerByLocal(
            @RequestBody @Validated LocalRegistrationDTO authRegistration
    ) {
        return localAccessService.register(authRegistration);
    }

    // TODO
    @PostMapping("/local")
    public TokenAuthResponse loginByLocal(
            @AuthenticationPrincipal Authentication authentication,
            @RequestBody LocalProviderDTO loginRequest
    ) {
        return null;
    }

    @PostMapping("/oauth/register")
    public UserDTO registerByOAuth(
            @RequestBody @Validated OAuthRegistrationDTO authRegistration
    ) {
        throw new NotImplementedException();
    }

    @PostMapping("/oauth")
    public UserDTO loginByOAuth(
            @RequestBody OAuth2UserRequest loginRequest
    ) {
        throw new NotImplementedException();
    }
}
