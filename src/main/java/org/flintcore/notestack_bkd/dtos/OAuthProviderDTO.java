package org.flintcore.notestack_bkd.dtos;

import org.flintcore.notestack_bkd.entities.EAuthProvider;
import org.springframework.lang.NonNull;

public record OAuthProviderDTO(
        @NonNull EAuthProvider providerName,
        @NonNull String accessToken
) {}