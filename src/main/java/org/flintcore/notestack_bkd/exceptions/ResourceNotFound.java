package org.flintcore.notestack_bkd.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends ApiException {

    public static final String RESOURCE_REQUESTED_NOT_PRESENT_FORMAT = "Resource requested of %s not present.";

    public ResourceNotFound(Class<?> element) {
        super(RESOURCE_REQUESTED_NOT_PRESENT_FORMAT.formatted(element.getSimpleName()), HttpStatus.NOT_FOUND);
    }
}
