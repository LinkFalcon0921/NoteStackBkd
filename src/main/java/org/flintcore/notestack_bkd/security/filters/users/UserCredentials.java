package org.flintcore.notestack_bkd.security.filters.users;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@Builder
public class UserCredentials implements UserDetails {
    private final String username;
    private final String password;
    @Singular
    private final Set<? extends GrantedAuthority> authorities;

    private final boolean isAccountExpired, isCredentialsExpired, isAccountLocked, isEnabled;

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonExpired();
    }
}
