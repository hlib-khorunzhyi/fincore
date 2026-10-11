package com.hlibkhorunzhyi.fincore.exceptions.handler;

import com.hlibkhorunzhyi.fincore.exceptions.dto.ErrorResponse;
import com.hlibkhorunzhyi.fincore.exceptions.dto.ValidationErrorResponse;
import com.hlibkhorunzhyi.fincore.exceptions.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountAlreadyClosedException.class)
    public ResponseEntity<ErrorResponse> handleAccountAlreadyClosed(
            AccountAlreadyClosedException exception
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "ACCOUNT_ALREADY_CLOSED",
                exception.getMessage()
        );
    }

    @ExceptionHandler(AccountHasBalanceException.class)
    public ResponseEntity<ErrorResponse> handleAccountHasBalance(
            AccountHasBalanceException exception
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "ACCOUNT_HAS_BALANCE",
                exception.getMessage()
        );
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<ErrorResponse> handleAccountNoActive(
            AccountNotActiveException exception
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "ACCOUNT_NO_ACTIVE",
                exception.getMessage()
        );
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(
            AccountNotFoundException exception
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "ACCOUNT_NOT_FOUND",
                exception.getMessage()
        );
    }

    @ExceptionHandler(CurrencyMismatchException.class)
    public ResponseEntity<ErrorResponse> handleCurrencyMismatch(
            CurrencyMismatchException exception
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "CURRENCY_MISMATCH",
                exception.getMessage()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException exception
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "DATA_INTEGRITY_VIOLATION",
                "The requested operation violates a database constraint"
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyExistsException exception
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "EMAIL_ALREADY_EXISTS",
                exception.getMessage()
        );
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(
            InsufficientFundsException exception
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "INSUFFICIENT_FUNDS",
                exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException exception
    ) {
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "INVALID_CREDENTIALS",
                exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidTransferException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransfer(
            InvalidTransferException exception
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "INVALID_TRANSFER",
                exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                "Request validation failed",
                Instant.now(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException exception
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "USER_NOT_FOUND",
                exception.getMessage()
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String error,
            String message
    ) {
        ErrorResponse response = new ErrorResponse(
                status.value(),
                error,
                message,
                Instant.now()
        );

        return ResponseEntity.status(status).body(response);
    }
}
