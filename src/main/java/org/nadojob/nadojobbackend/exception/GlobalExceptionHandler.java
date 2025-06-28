package org.nadojob.nadojobbackend.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.nadojob.nadojobbackend.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EntityNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(Exception e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
    })
    public ResponseEntity<ErrorResponseDto> handleEntityAlreadyExistException(Exception e) {
        log.warn(e.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

//    @ExceptionHandler({
//            InvalidVerificationCodeException.class
//    })
//    public ResponseEntity<ErrorResponseDto> handleInvalidException(Exception e) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
//    }

    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(Exception e) {
        log.warn(e.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({
            JwtAuthenticationException.class,
            PasswordNotCorrectException.class
    })
    public ResponseEntity<ErrorResponseDto> handleAuthenticationException(Exception e) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

//    @ExceptionHandler({
//            DuplicateNameException.class,
//            DuplicateEmailException.class,
//            DuplicateCompanyNameException.class
//    })
//    public ResponseEntity<ErrorResponseDto> handleDuplicateNameException(Exception e) {
//        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
//    }

    @ExceptionHandler(StackOverflowError.class)
    public ResponseEntity<ErrorResponseDto> handleStackOverflowError(Exception e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(Exception e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

//    @ExceptionHandler({
//            AppointmentConflictException.class,
//            EmailNotVerifiedException.class
//    })
//    public ResponseEntity<ErrorResponseDto> handleConflictException(Exception e) {
//        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
//    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(Exception e) {
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

//    @ExceptionHandler(OutOfWorkingHoursException.class)
//    public ResponseEntity<ErrorResponseDto> handleHoursException(Exception e) {
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponseDto> handlerMethodArgumentExceptions(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Некорректные данные");

        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDto(status.value(), status.name(), message));
    }
}
