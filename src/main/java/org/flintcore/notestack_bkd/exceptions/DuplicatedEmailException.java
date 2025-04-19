package org.flintcore.notestack_bkd.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicatedEmailException extends ApiException {

    public static final String EMAIL_ALREADY_REGISTERED_FORMAT = "Email %s is already registered";

    public DuplicatedEmailException(String email) {
        super(EMAIL_ALREADY_REGISTERED_FORMAT.formatted(email), HttpStatus.CONFLICT);
    }
}
