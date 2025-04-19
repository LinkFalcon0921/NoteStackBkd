package org.flintcore.notestack_bkd.dtos.requests;

import org.flintcore.notestack_bkd.dtos.LocalProviderDTO;
import org.flintcore.notestack_bkd.dtos.UserDTO;
import org.springframework.lang.NonNull;

public record LocalRegistrationDTO(
        @NonNull UserDTO user,
        @NonNull LocalProviderDTO localAuthRequest
) {}
