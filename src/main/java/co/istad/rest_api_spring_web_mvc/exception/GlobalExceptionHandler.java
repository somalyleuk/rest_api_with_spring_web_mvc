package co.istad.rest_api_spring_web_mvc.exception;

import co.istad.rest_api_spring_web_mvc.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateBookException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponseDto<String> handleDuplicateBookException(DuplicateBookException ex) {
        return ApiResponseDto.<String>builder()
                .message("Conflict Detected")
                .status(HttpStatus.CONFLICT.value())
                .data(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseDto<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ApiResponseDto.<Map<String, String>>builder()
                .message("Validation Failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .data(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseDto<String> handleGenericException(Exception ex) {
        return ApiResponseDto.<String>builder()
                .message("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .data(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}