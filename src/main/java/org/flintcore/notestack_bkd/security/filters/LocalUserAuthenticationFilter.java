package org.flintcore.notestack_bkd.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.flintcore.notestack_bkd.configurations.tokens.JWTUtil;
import org.flintcore.notestack_bkd.services.userdetails.LocalUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocalUserAuthenticationFilter extends OncePerRequestFilter {

    private final LocalUserDetailsService localUserDetailsService;
    private final JWTUtil jwtUtil;

    private BearerTokenResolver bearerTokenResolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String tokenRequested = getBearerTokenResolver().resolve(request);

        // TODO Make doFilter call once
        // IF already authenticated
        try {
            var jwt = jwtUtil.validateToken(tokenRequested);

            String email = jwt.getSubject();

            var authentication = localUserDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authentication, null, null
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (JWTVerificationException | NullPointerException e) {
            log.error("JWT verification failed", e);
        }

        filterChain.doFilter(request, response);
    }

    public BearerTokenResolver getBearerTokenResolver() {
        return bearerTokenResolver = ObjectUtils.getIfNull(bearerTokenResolver, DefaultBearerTokenResolver::new);
    }
}
