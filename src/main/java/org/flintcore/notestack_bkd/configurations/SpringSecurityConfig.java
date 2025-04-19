package org.flintcore.notestack_bkd.configurations;

import lombok.RequiredArgsConstructor;
import org.flintcore.notestack_bkd.configurations.properties.AuthTokenProperties;
import org.flintcore.notestack_bkd.configurations.properties.OpenapiProperties;
import org.flintcore.notestack_bkd.configurations.properties.SecretsProperties;
import org.flintcore.notestack_bkd.configurations.properties.SpringProperties;
import org.flintcore.notestack_bkd.security.filters.LocalUserAuthenticationFilter;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Profile({"!test"})
public class SpringSecurityConfig {

    private static final String[] PERMITTED_URLS = {
            OAuth2BeanConfig.OAUTH_REQUEST_PATH_MATCHERS,
            "/h2-console/**",
            "/docs/**"
    };


    // Local credentials validator
    private final LocalUserAuthenticationFilter localJWTAuthFilter;
    // Note: Check subclasses of this one. To Check OAuth2 impl
    private final AuthenticationProvider localAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header ->
                        header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                ).authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMITTED_URLS).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(localJWTAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Maneja multiples proveedores.
     * */
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(localAuthenticationProvider);
    }
}
