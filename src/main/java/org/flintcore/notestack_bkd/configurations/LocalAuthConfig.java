package org.flintcore.notestack_bkd.configurations;

import lombok.RequiredArgsConstructor;
import org.flintcore.notestack_bkd.services.userdetails.LocalUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class LocalAuthConfig {
    private final LocalUserDetailsService localUserDetailsService;

    @Bean
    public AuthenticationProvider localAuthenticationProvider(
            PasswordEncoder localAuthenticationEncoder
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(localAuthenticationEncoder);
        daoAuthenticationProvider.setUserDetailsService(localUserDetailsService);

        return daoAuthenticationProvider;
    }
}
