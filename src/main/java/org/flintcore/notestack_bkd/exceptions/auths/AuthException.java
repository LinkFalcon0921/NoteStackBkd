package org.flintcore.notestack_bkd.exceptions.auths;

import org.flintcore.notestack_bkd.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class AuthException extends ApiException {
    public AuthException(String message, HttpStatus status) {
        super(message, status);
    }
}
