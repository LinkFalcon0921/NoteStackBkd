package org.flintcore.notestack_bkd.dtos;

import jakarta.validation.constraints.Email;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record LocalProviderDTO(
        @NonNull @Email String email,
        @Nullable String password
) {}