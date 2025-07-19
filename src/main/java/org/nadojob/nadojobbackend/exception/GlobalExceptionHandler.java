package org.nadojob.nadojobbackend.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.nadojob.nadojobbackend.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EntityNotFoundException.class,
            CandidateProfileNotFoundException.class,
            CompanyNotFoundException.class,
            SectorNotFoundException.class,
            InviteTokenNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleEntityNotFound(RuntimeException e) {
        log.warn("Entity not found: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({
            InviteTokenExpiredException.class
    })
    public ResponseEntity<ErrorResponseDto> handleExpired(RuntimeException e) {
        log.warn("Entity not found: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }


    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            PhoneAlreadyExistsException.class,
            CompanyNameAlreadyExistsException.class,
            ProfileTitleAlreadyExistsException.class,
            SectorNameAlreadyExistsException.class,
            UserAlreadyInvitedException.class,
            JobApplicationAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponseDto> handleAlreadyExists(RuntimeException e) {
        log.warn("Conflict: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({
            OpenAiIntegrationException.class
    })
    public ResponseEntity<ErrorResponseDto> handleIntegration(RuntimeException e) {
        log.warn("Conflict: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ErrorResponseDto> handleUserBlocked(UserBlockedException e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler({
            JwtAuthenticationException.class,
            PasswordNotCorrectException.class
    })
    public ResponseEntity<ErrorResponseDto> handleAuthErrors(RuntimeException e) {
        log.warn(e.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(ValidationException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Некорректные данные");

        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(StackOverflowError.class)
    public ResponseEntity<ErrorResponseDto> handleStackOverflow(StackOverflowError e) {
        log.error("Critical: Stack overflow", e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnexpected(Exception e) {
        log.error("Unexpected error: ", e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(AccessDeniedException e) {
        log.warn("Access denied: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Доступ запрещён");
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponseDto(status.value(), status.getReasonPhrase(), message));
    }
}
