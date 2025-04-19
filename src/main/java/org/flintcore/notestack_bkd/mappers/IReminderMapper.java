package org.flintcore.notestack_bkd.mappers;

import org.flintcore.notestack_bkd.dtos.ReminderDTO;
import org.flintcore.notestack_bkd.entities.Reminder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IReminderMapper {

    // TODO Handle the text hardcoded as DBSchema format.

    @Mapping(source = "issuedAt", target = "expectedAt")
    ReminderDTO toDto(Reminder reminder);

    Reminder toEntity(ReminderDTO reminderDTO);

}