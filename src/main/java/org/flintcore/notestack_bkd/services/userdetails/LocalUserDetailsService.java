package org.flintcore.notestack_bkd.services.userdetails;

import lombok.RequiredArgsConstructor;
import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.flintcore.notestack_bkd.entities.auths.LocalAuthProvider;
import org.flintcore.notestack_bkd.repositories.IAuthProviderRepository;
import org.flintcore.notestack_bkd.security.filters.users.UserCredentials;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;


@Service
@RequiredArgsConstructor
public class LocalUserDetailsService implements UserDetailsService {

    private final IAuthProviderRepository authProviderRepository;

    /**
     * Use email to extract user details of {@link UserCredentials Credentials} stored in server..
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, ResourceAccessException {
        return authProviderRepository.findLocalByEmail(email)
                .map(this::castUser)
                .orElseThrow(() -> new ResourceAccessException("User not registered"));
    }

    public UserDetails loadUserByUsername(LocalProviderDTO localProvider)
            throws UsernameNotFoundException, ResourceAccessException {
        return loadUserByUsername(localProvider.email());
    }

    /**
     * Convert to user credentials with email integrated.
     * */
    private UserCredentials castUser(LocalAuthProvider usProvider) {
        return UserCredentials.builder()
                .username(usProvider.getEmail())
                .password(usProvider.getPassword())
                .build();
    }
}
