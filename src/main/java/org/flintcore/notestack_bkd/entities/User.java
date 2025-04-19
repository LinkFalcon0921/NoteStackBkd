package org.flintcore.notestack_bkd.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.flintcore.notestack_bkd.entities.auths.AuthProvider;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;

import static org.flintcore.notestack_bkd.entities.DBSchema.*;

@Entity
@Table(name = UserColumns.TABLE)
@Data
public class User {

    @Id
    @UuidGenerator
    @Column(name = UserColumns.ID)
    private String id;

    @Column(name = UserColumns.NAME, nullable = false)
    private String username;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = AuthProviderColumns.USER_MAPPED_BY, cascade = CascadeType.ALL)
    private Set<AuthProvider> authProviders;
}