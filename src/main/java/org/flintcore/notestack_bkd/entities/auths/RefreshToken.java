package org.flintcore.notestack_bkd.entities.auths;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flintcore.notestack_bkd.entities.DBSchema.RefreshTokenColumns;
import org.flintcore.notestack_bkd.entities.DBSchema.UserColumns;
import org.flintcore.notestack_bkd.entities.User;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(
        name = RefreshTokenColumns.TABLE,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {RefreshTokenColumns.ID})
        }
)
@Data
public class RefreshToken {

    @Id
    @UuidGenerator
    @Column(name = RefreshTokenColumns.ID)
    private String requestTokenId;

    private String token;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = UserColumns.ID)
    private User user;
}
