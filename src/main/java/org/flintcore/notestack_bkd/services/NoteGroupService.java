package org.flintcore.notestack_bkd.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flintcore.notestack_bkd.entities.Group;
import org.flintcore.notestack_bkd.entities.Note;
import org.flintcore.notestack_bkd.repositories.IGroupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteGroupService {

//    private final INoteRepository noteRepository;
    private final IGroupRepository groupRepository;

    public Optional<Group> getGroup(String groupId) {
        return groupRepository.findById(groupId);

    }

    public void appendNoteToPrivate(String userId, Note note) {
        getPrivateGroupOf(userId)
                .ifPresent(note::setGroup);
    }

    public Optional<Group> getPrivateGroupOf(String userId) {
        return groupRepository.getDefaultGroupOf(userId);
    }
}
