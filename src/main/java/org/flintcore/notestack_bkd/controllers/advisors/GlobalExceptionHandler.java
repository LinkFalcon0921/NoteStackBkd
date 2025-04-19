package org.flintcore.notestack_bkd.controllers.advisors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.flintcore.notestack_bkd.dtos.ErrorResponse;
import org.flintcore.notestack_bkd.exceptions.ApiException;
import org.flintcore.notestack_bkd.utils.ErrorResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    public static final String MESSAGE_VALIDATION_ERROR = "Unable to validate fields values",
            INTERNAL_SERVER_MESSAGE = "Internal server error",
            NOT_AVAILABLE = "Not available";

    public final ErrorResponseBuilder errorBuilder;


    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleNotImplementedException(NotImplementedException ex, WebRequest request) {
        return errorBuilder.buildErrorResponse(
                NOT_AVAILABLE,
                HttpStatus.UNAUTHORIZED,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleApiException(ApiException ex, WebRequest request) {
        return errorBuilder.buildErrorResponse(
                ex.getMessage(),
                ex.getStatus(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {

        List<ErrorResponse.ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.ValidationError(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue())
                ).toList();

        return errorBuilder.buildErrorResponse(
                MESSAGE_VALIDATION_ERROR,
                HttpStatus.BAD_REQUEST,
                request.getDescription(false),
                errors
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex, WebRequest request) {
        return errorBuilder.buildErrorResponse(
                INTERNAL_SERVER_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getDescription(false)
        );
    }
}
