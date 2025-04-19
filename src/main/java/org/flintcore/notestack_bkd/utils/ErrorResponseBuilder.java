package org.flintcore.notestack_bkd.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.flintcore.notestack_bkd.dtos.ErrorResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ErrorResponseBuilder {

    public ErrorResponse buildErrorResponse(String message, HttpStatus status, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    }

    public ErrorResponse buildErrorResponse(
            String message,
            HttpStatus status,
            String path,
            List<ErrorResponse.ValidationError> details) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                details
        );
    }
}
