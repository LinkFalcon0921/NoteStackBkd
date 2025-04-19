package org.flintcore.notestack_bkd.dtos.responses;

/**
 * General response for success Authentication
 * */
public record TokenAuthResponse(
        String accessToken, String refreshToken
) {
}
