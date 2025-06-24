package co.istad.rest_api_spring_web_mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDto<T> {
    private String message;
    private int status;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponseDto<T> success(String message, HttpStatus httpStatus, T data) {
        return ApiResponseDto.<T>builder()
                .message(message)
                .status(httpStatus.value())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponseDto<Void> noContent(String message, HttpStatus httpStatus) {
        return ApiResponseDto.<Void>builder()
                .message(message)
                .status(httpStatus.value())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
