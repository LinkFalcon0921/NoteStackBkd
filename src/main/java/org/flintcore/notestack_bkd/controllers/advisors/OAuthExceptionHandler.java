package org.flintcore.notestack_bkd.controllers.advisors;


import lombok.RequiredArgsConstructor;
import org.flintcore.notestack_bkd.dtos.ErrorResponse;
import org.flintcore.notestack_bkd.exceptions.auths.OAuth2Exception;
import org.flintcore.notestack_bkd.utils.ErrorResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class OAuthExceptionHandler {

    public static final String REDIRECTION_ERROR_FORMAT = "Redirection Error OAuth2: \n%s";
    public final ErrorResponseBuilder errorResponseBuilder;

    @ExceptionHandler(OAuth2Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleOAuth2Exception(OAuth2Exception ex, WebRequest request) {
        return errorResponseBuilder.buildErrorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOAuth2RedirectException(OAuth2AuthenticationException ex, WebRequest request) {
        return errorResponseBuilder.buildErrorResponse(
                REDIRECTION_ERROR_FORMAT.formatted(ex.getMessage()),
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );
    }
}
