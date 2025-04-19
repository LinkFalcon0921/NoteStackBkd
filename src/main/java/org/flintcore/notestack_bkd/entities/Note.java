package org.flintcore.notestack_bkd.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;

import static org.flintcore.notestack_bkd.entities.DBSchema.*;

@Entity
@Table(name = NoteColumns.TABLE)
@Data
public class Note {

    @Id
    @UuidGenerator
    @Column(name = NoteColumns.ID)
    private String id;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = NoteColumns.OWNER_ID)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = NoteColumns.GROUP_ID)
    private Group group;

    @Column(name = NoteColumns.IS_ENABLED, nullable = false)
    private boolean enabled;

    @Column(name = CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    // TODO Handle note can have multiple reminders
    @OneToMany(mappedBy = NoteColumns.NOTE_MAPPED_BY, cascade = CascadeType.ALL)
    private Set<Reminder> reminders;
}