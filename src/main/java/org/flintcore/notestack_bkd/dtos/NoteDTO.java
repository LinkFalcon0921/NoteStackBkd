package org.flintcore.notestack_bkd.dtos;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record NoteDTO(
        String id,
        @Size(min = 1, max = 100)
        String title,
        @Size(min = 1, max = 250)
        String content,
        String ownerId,
        Boolean isEnabled,
        String groupId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
