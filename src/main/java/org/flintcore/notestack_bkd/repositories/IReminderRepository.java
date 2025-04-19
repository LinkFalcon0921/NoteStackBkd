package org.flintcore.notestack_bkd.repositories;

import org.flintcore.notestack_bkd.entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IReminderRepository extends JpaRepository<Reminder, Long> {

    // Recordatorios pendientes (para servicio programado)
    @Query("SELECT r FROM Reminder r WHERE r.active = true AND r.issuedAt <= :now")
    List<Reminder> findActiveRemindersDue(@Param("now") LocalDateTime now);

    // Recordatorios de una nota (para WebSocket)
    @Query("SELECT r FROM Reminder r WHERE r.note.id = :noteId ORDER BY r.issuedAt ASC")
    List<Reminder> findByNoteId(@Param("noteId") String noteId);
}
