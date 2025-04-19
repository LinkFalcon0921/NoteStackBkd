package org.flintcore.notestack_bkd.mappers;

import org.flintcore.notestack_bkd.dtos.NoteDTO;
import org.flintcore.notestack_bkd.entities.Group;
import org.flintcore.notestack_bkd.entities.Note;
import org.flintcore.notestack_bkd.entities.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.Objects;

@Mapper(componentModel = "spring",
        uses = {IUserMapper.class, IReminderMapper.class, IGroupMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.WARN
)
public interface INoteMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "enabled", target = "isEnabled")
    NoteDTO toDto(Note note);

    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "groupId", target = "group.id")
    @Mapping(target = "reminders", ignore = true)
    @Mapping(source = "isEnabled", target = "enabled")
    Note toEntity(NoteDTO noteDTO);

    default void updateNoteFromDto(Note note, NoteDTO noteDTO) {
        note.setContent(noteDTO.content());
        note.setTitle(noteDTO.title());
        note.setTitle(noteDTO.title());
        note.setUpdatedAt(LocalDateTime.now());

        if(Objects.nonNull(noteDTO.groupId())) {
            var group = new Group();

            group.setId(noteDTO.groupId());
            note.setGroup(group);
        }

        if(Objects.nonNull(noteDTO.ownerId())) {
            var user = new User();

            user.setId(noteDTO.groupId());
            note.setOwner(user);
        }

        note.setUpdatedAt(LocalDateTime.now());
    }
}
