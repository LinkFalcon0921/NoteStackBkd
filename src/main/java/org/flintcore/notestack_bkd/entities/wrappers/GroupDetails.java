package org.flintcore.notestack_bkd.entities.wrappers;

import org.flintcore.notestack_bkd.entities.Group;

public record GroupDetails(
        Group group,
        long noteCount
) {}
