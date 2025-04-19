package org.flintcore.notestack_bkd.entities.auths;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.flintcore.notestack_bkd.entities.User;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static org.flintcore.notestack_bkd.entities.DBSchema.*;

@Entity
@Table(name = AuthProviderColumns.TABLE,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                AuthProviderColumns.EMAIL,
                AuthProviderColumns.PROVIDER_TYPE
        }),
    }
)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class AuthProvider {

    @Id
    @UuidGenerator
    protected String id;

    @Enumerated(EnumType.STRING)
    @Column(name = AuthProviderColumns.PROVIDER_TYPE, nullable = false)
    protected EAuthProvider providerName;

    @Email
    @Column(name = AuthProviderColumns.EMAIL, nullable = false)
    protected String email;

    @CreatedDate
    @Column(nullable = false)
    protected LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserColumns.ID)
    protected User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        whileCreate();
    }

    /**
     * Override to add additional on create Entity settings
     */
    protected void whileCreate() {
    }
}
