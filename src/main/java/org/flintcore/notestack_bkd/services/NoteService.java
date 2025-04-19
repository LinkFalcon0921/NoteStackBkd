package org.flintcore.notestack_bkd.services;

import lombok.RequiredArgsConstructor;
import org.flintcore.notestack_bkd.dtos.NoteDTO;
import org.flintcore.notestack_bkd.entities.Note;
import org.flintcore.notestack_bkd.entities.User;
import org.flintcore.notestack_bkd.mappers.INoteMapper;
import org.flintcore.notestack_bkd.repositories.INoteRepository;
import org.flintcore.notestack_bkd.repositories.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final INoteRepository noteRepository;
    private final IUserRepository userRepository;
    private final NoteGroupService noteGroupService;

    // Mappers
    private final INoteMapper noteMapper;

    @Transactional
    public NoteDTO createNote(NoteDTO dto, String userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceAccessException("User not found!"));

        Note note = noteMapper.toEntity(dto);
        note.setOwner(owner);

        if (dto.groupId() != null) {
            noteGroupService.appendNoteToPrivate(owner.getId(), note);
        }

        Note savedNote = noteRepository.save(note);
        return noteMapper.toDto(savedNote);
    }

    @Transactional
    public NoteDTO updateNote(String noteId, NoteDTO dto) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceAccessException("Note not found!"));

        noteMapper.updateNoteFromDto(note, dto);
        Note updatedNote = noteRepository.save(note);

        return noteMapper.toDto(updatedNote);
    }
}
