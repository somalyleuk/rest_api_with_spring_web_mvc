package co.istad.rest_api_spring_web_mvc.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String resourceName, Long id) {
        super(HttpStatus.NOT_FOUND, String.format("%s With id = %d not found", resourceName, id));
    }
}
