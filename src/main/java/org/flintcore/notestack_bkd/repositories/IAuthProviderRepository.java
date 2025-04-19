package org.flintcore.notestack_bkd.repositories;

import jakarta.validation.constraints.Email;
import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.flintcore.notestack_bkd.entities.User;
import org.flintcore.notestack_bkd.entities.auths.AuthProvider;
import org.flintcore.notestack_bkd.entities.auths.LocalAuthProvider;
import org.flintcore.notestack_bkd.entities.auths.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAuthProviderRepository extends JpaRepository<AuthProvider, String> {
    @Query("SELECT lp FROM LocalAuthProvider lp WHERE lp.email = :email AND lp.password = :password")
    Optional<LocalAuthProvider> findByCredentials(
            @Param("email") String email,
            @Param("password") String password
    );

    @Query("SELECT lp FROM LocalAuthProvider lp WHERE lp.email = :email")
    Optional<LocalAuthProvider> findLocalByEmail(@Email String email);

    //OAUTH2

    @Query("SELECT ap FROM OAuthProvider ap WHERE ap.providerName = :providerName AND ap.providerId = :providerId")
    Optional<OAuthProvider> findAuthByProvider(
            @Param("providerName") EAuthProvider providerName,
            @Param("accessToken") String providerId
    );

    default Optional<User> findUserByProvider(EAuthProvider providerName, String providerId) {
        return findAuthByProvider(providerName, providerId)
                .map(AuthProvider::getUser);
    }

    List<AuthProvider> findByCreatedAtBefore(LocalDateTime createdAtBefore);

    List<AuthProvider> findByProviderName(EAuthProvider providerName);

    boolean existsByProviderNameAndEmail(EAuthProvider providerName, @Email String email);
}