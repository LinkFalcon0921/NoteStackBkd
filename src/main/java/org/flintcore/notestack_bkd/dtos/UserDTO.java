package org.flintcore.notestack_bkd.dtos;

import java.time.LocalDateTime;

public record UserDTO(
        String id,
        String username,
        LocalDateTime createdAt
) {}
