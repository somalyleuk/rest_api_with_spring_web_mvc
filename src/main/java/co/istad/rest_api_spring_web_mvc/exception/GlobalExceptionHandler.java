package co.istad.rest_api_spring_web_mvc.exception;

import co.istad.rest_api_spring_web_mvc.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Response<?>> handleApiException(ApiException e) {
        Response<Object> response = Response.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleGenericException(Exception e) {
        Response<Object> response = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred: " + e.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(ApiException.class)
//    public ResponseEntity<?> handleApiException(ApiException e) {
//        ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getMessage());
//        return ResponseEntity.status(e.getStatus()).body(errorResponse);
//    }
//}
