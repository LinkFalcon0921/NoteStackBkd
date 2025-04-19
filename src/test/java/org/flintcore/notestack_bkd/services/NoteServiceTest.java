package org.flintcore.notestack_bkd.services;

import org.apache.commons.lang3.ObjectUtils;
import org.flintcore.notestack_bkd.configurations.MapStructConfig;
import org.flintcore.notestack_bkd.dtos.UserDTO;
import org.flintcore.notestack_bkd.repositories.IAuthProviderRepository;
import org.flintcore.notestack_bkd.repositories.IGroupRepository;
import org.flintcore.notestack_bkd.repositories.INoteRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        MapStructConfig.class,
        NoteService.class,
        INoteRepository.class,
        IAuthProviderRepository.class,
        IGroupRepository.class,
})
@Disabled("Not ready yet. DataJPATest cannot override other settings.")
class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    private UserDTO user;

    @Test
    void createNewUser() {
        UserDTO user = new UserDTO(
                null,
                UUID.randomUUID().toString(),
                LocalDateTime.now()
        );


    }

    public UserDTO getUser() {
        return user = ObjectUtils.getIfNull(user, () -> {
            String ramdomName = UUID.randomUUID().toString();

            return new UserDTO(
                    null,
                    ramdomName,
                    LocalDateTime.now()
            );
        });
    }
}