package org.flintcore.notestack_bkd.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static org.flintcore.notestack_bkd.entities.DBSchema.*;
import static org.flintcore.notestack_bkd.entities.DBSchema.NoteColumns;

@Entity
@Table(name = ReminderColumns.TABLE)
@Data
public class Reminder {

    @Id
    @UuidGenerator
    @Column(name = ReminderColumns.ID)
    private String id;

    @Column(name = CREATED_AT, nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = ReminderColumns.ISSUED_AT_COLUMN, nullable = false)
    private LocalDateTime issuedAt;

    @Enumerated(EnumType.STRING)
    private EReminderFrequency repeatAt;

    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = NoteColumns.ID)
    private Note note;

    @PrePersist
    protected void onCreate() {
        setCreatedAt(LocalDateTime.now());
    }
}