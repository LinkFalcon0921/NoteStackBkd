package org.flintcore.notestack_bkd.dtos;


import org.springframework.lang.Nullable;

import java.util.Set;

public record GroupDTO(
        String id,
        String name,
        String ownerId
) {}