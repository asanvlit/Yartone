package ru.asanvlit.controller.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.sun.mail.util.MailConnectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.asanvlit.dto.response.ExceptionResponse;
import ru.asanvlit.validation.response.ValidationExceptionResponse;
import ru.asanvlit.exception.badrequest.YartoneBadDbRequestException;
import ru.asanvlit.exception.base.YartoneBadRequestException;
import ru.asanvlit.exception.base.YartoneNotFoundException;
import ru.asanvlit.exception.base.YartoneServiceException;
import ru.asanvlit.exception.base.YartoneValidationException;
import ru.asanvlit.exception.illegalargument.YartoneBadTokenFormatException;
import ru.asanvlit.exception.methodnotallowed.YartoneEmailException;
import ru.asanvlit.validation.response.ValidationErrorResponse;

import javax.servlet.ServletException;
import java.util.List;
import java.util.stream.Collectors;

import static ru.asanvlit.constant.YartoneApiConstants.VALIDATION_ERROR_MESSAGE_TITLE;
import static ru.asanvlit.constant.YartoneImplConstants.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> onUnhandledExceptions(Exception exception) {
        log.error("An unhandled exception has occurred: {}", exception.getMessage());
        return buildExceptionMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
                exception.getClass());
    }

    @ExceptionHandler(YartoneServiceException.class)
    public final ResponseEntity<ExceptionResponse> onYartoneServiceExceptions(YartoneServiceException exception) {
        return buildExceptionMessageResponse(exception.getHttpStatus(), exception.getMessage(),
                exception.getClass());
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> onAuthenticationExceptions(AuthenticationException exception) {
        return buildExceptionMessageResponse(HttpStatus.UNAUTHORIZED, exception.getMessage(),
                exception.getClass());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> onDuplicateException(DataIntegrityViolationException e) {
        return buildExceptionMessageResponse(HttpStatus.BAD_REQUEST, DB_DUPLICATE_MESSAGE,
                YartoneBadDbRequestException.class);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionResponse> onNotReadableMessageExceptions(HttpMessageNotReadableException e) {
        return buildExceptionMessageResponse(HttpStatus.BAD_REQUEST, JSON_PARSE_ERROR_MESSAGE,
                YartoneBadRequestException.class);
    }

    @ExceptionHandler({ServletException.class, PropertyReferenceException.class})
    public final ResponseEntity<ExceptionResponse> onRequestException(Exception e) {
        return buildExceptionMessageResponse(HttpStatus.BAD_REQUEST, e.getMessage(),
                YartoneBadRequestException.class);
    }

    @ExceptionHandler({JWTDecodeException.class, YartoneBadTokenFormatException.class})
    public final ResponseEntity<ExceptionResponse> onInvalidTokenException(Exception e) {
        return buildExceptionMessageResponse(HttpStatus.UNAUTHORIZED, e.getMessage(),
                YartoneBadRequestException.class);
    }

    @ExceptionHandler({MailException.class, MailConnectException.class})
    public final ResponseEntity<ExceptionResponse> onMailExceptions(Exception e) {
        return buildExceptionMessageResponse(HttpStatus.METHOD_NOT_ALLOWED, EMAIL_EXCEPTIONS_MESSAGE,
                YartoneEmailException.class);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, InvalidDataAccessApiUsageException.class})
    public final ResponseEntity<ExceptionResponse> onMethodArgumentTypeMismatchException(Exception e) {
        return buildExceptionMessageResponse(HttpStatus.BAD_REQUEST, METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE,
                YartoneBadRequestException.class);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<ExceptionResponse> onNoHandlerFoundExceptions(Exception exception) {
        return buildExceptionMessageResponse(HttpStatus.NOT_FOUND, exception.getMessage(),
                YartoneNotFoundException.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ValidationExceptionResponse> onMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        log.info("A validation exception has occurred: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ValidationExceptionResponse.builder()
                        .message(VALIDATION_ERROR_MESSAGE_TITLE)
                        .exceptionName(YartoneValidationException.class.getSimpleName())
                        .errors(buildFieldValidationErrors(ex.getFieldErrors()))
                        .build());
    }

    private List<ValidationErrorResponse> buildFieldValidationErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(e -> ValidationErrorResponse.builder()
                        .object(e.getObjectName())
                        .rejectedField(e.getField())
                        .rejectedFieldErrorMessage(e.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
    }

    private ResponseEntity<ExceptionResponse> buildExceptionMessageResponse(HttpStatus status, String message, Class<? extends Exception> exceptionClass) {
        return ResponseEntity.status(status)
                .body(ExceptionResponse.builder()
                        .message(message)
                        .exceptionName(exceptionClass.getSimpleName())
                        .build());
    }
}

