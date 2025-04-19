package org.flintcore.notestack_bkd.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.flintcore.notestack_bkd.entities.DBSchema.GroupColumns;
import org.flintcore.notestack_bkd.entities.DBSchema.GroupMemberColumns;
import org.flintcore.notestack_bkd.entities.DBSchema.UserColumns;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = GroupMemberColumns.TABLE)
@Data
public class GroupMember {

    @EmbeddedId
    private GroupMemberId id;

    @MapsId(GroupMemberColumns.USER_ID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserColumns.ID)
    private User user;

    private EUserRole role;

    @MapsId(GroupMemberColumns.GROUP_ID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = GroupColumns.ID)
    private Group group;


    @Embeddable
    public static class GroupMemberId implements Serializable {
        @Serial
        private static final long serialVersionUID = 2942L;

        String userId;
        String groupId;
    }
}
