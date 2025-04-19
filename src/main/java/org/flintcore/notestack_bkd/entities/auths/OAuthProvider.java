package org.flintcore.notestack_bkd.entities.auths;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static org.flintcore.notestack_bkd.entities.DBSchema.AuthProviderColumns;

@Entity
@Table(
        name = AuthProviderColumns.OAUTH_TABLE,
        uniqueConstraints = @UniqueConstraint(
                columnNames = { AuthProviderColumns.PROVIDER_TYPE, AuthProviderColumns.PROVIDER_USER_TOKEN }
        )
)
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuthProvider extends AuthProvider {
    @Column(name = AuthProviderColumns.PROVIDER_USER_TOKEN, nullable = false)
    private String providerId;
}
