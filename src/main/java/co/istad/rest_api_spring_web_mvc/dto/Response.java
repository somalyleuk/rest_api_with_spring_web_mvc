package co.istad.rest_api_spring_web_mvc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response<Object>(
        Integer status,
        String message,
        Object data,
        LocalDateTime timestamp
) {
}