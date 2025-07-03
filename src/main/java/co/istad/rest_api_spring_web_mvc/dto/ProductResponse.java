package co.istad.rest_api_spring_web_mvc.dto;

import lombok.Builder;

@Builder
public record ProductResponse(
        Integer id,
        String name,
        Integer quantity,
        Double price
) {
}