package org.flintcore.notestack_bkd.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

import static org.flintcore.notestack_bkd.entities.DBSchema.GroupColumns;

@Entity
@Table(name = GroupColumns.TABLE)
@Data
public class Group {
    // Database related constants
    @Id
    @UuidGenerator
    @Column(name = GroupColumns.ID)
    private String id;

    @Column
    private String name;

    @OneToMany(
            mappedBy = GroupColumns.GROUP_MEMBERS_BY,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<GroupMember> members;

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    private User owner;

    @OneToMany(mappedBy = GroupColumns.NOTE_MAPPED_BY, fetch = FetchType.LAZY)
    private Set<Note> notes;
}
