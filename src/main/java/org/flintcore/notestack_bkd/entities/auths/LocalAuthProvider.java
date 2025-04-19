package org.flintcore.notestack_bkd.entities.auths;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.flintcore.notestack_bkd.entities.DBSchema;
import org.flintcore.notestack_bkd.entities.EAuthProvider;

@Entity
@Table
@PrimaryKeyJoinColumn(name = DBSchema.PRIMARY_ID_JOIN_NAME)
@Data
@EqualsAndHashCode(callSuper = true)
public class LocalAuthProvider extends AuthProvider {

   // Must be encrypted
   @Column(nullable = false)
    private String password;

   @Override
    protected void whileCreate() {
        providerName = EAuthProvider.LOCAL;
    }
}
