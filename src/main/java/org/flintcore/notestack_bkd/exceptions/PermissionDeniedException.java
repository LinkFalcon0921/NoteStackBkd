package org.flintcore.notestack_bkd.exceptions;

import org.springframework.http.HttpStatus;

public class PermissionDeniedException extends ApiException {

    public static final String NOT_ALLOWED_PERMISSIONS = "Do not have access to this resource";

    public PermissionDeniedException(String message, HttpStatus status) {
            super(NOT_ALLOWED_PERMISSIONS, HttpStatus.FORBIDDEN);
    }
}
