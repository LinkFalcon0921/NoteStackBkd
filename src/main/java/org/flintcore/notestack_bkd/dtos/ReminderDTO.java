package org.flintcore.notestack_bkd.dtos;

import org.flintcore.notestack_bkd.entities.EReminderFrequency;

import java.time.LocalDateTime;

public record ReminderDTO(
        String id,
        LocalDateTime createdAt,
        LocalDateTime expectedAt,
        EReminderFrequency repeatAt,
        Boolean active,
        Long noteId
) {

}