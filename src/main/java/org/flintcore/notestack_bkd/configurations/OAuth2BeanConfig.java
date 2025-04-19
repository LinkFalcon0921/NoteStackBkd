package org.flintcore.notestack_bkd.configurations;

import org.flintcore.notestack_bkd.services.userdetails.LocalUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class OAuth2BeanConfig {

    public static final String OAUTH_SUCCESS_ENDPOINT = "/auth/oauth2/success",
            OAUTH_FAILURE_ENDPOINT_FORMAT = "/auth/oauth2/failure?error=%s",
            OAUTH_BASE_URI = "/oauth2/authorization",
            OAUTH_REQUEST_PATH_MATCHERS = "/auth/**";

    // Authorization and redirection Messages

    @Bean
    public AuthenticationSuccessHandler oauth2SuccessHandler() {
        return (request, response, authentication) ->
                response.sendRedirect(OAUTH_SUCCESS_ENDPOINT);
    }

    @Bean
    public AuthenticationFailureHandler oauth2FailureHandler() {
        return (request, response, ex) ->
                response.sendRedirect(OAUTH_FAILURE_ENDPOINT_FORMAT.formatted(ex.getMessage()));
    }
}
