package co.istad.rest_api_spring_web_mvc.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}
