package org.flintcore.notestack_bkd.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends ApiException {

    public static final String INVALID_TOKEN_FORMAT = "Invalid token by: \n%s";

    public InvalidTokenException(String details) {
        super(INVALID_TOKEN_FORMAT.formatted(details), HttpStatus.UNAUTHORIZED);
    }
}
