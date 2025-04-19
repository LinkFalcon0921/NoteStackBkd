package org.flintcore.notestack_bkd.repositories;

import org.flintcore.notestack_bkd.entities.User;
import org.flintcore.notestack_bkd.entities.auths.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findTokenByUser(User user);

    @Query("SELECT tk FROM RefreshToken tk JOIN tk.user.authProviders providers WHERE providers.email = :email")
    Optional<RefreshToken> findTokenByUser(@Param("email") String email);

    Optional<RefreshToken> findTokenByToken(String token);

    boolean existsTokenByToken(String token);

    boolean existsTokenByUser(User user);
}
