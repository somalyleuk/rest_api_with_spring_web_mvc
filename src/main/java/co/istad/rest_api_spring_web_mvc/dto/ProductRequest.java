package co.istad.rest_api_spring_web_mvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductRequest(
        @NotBlank(message = "Name cannot be empty.")
        String name,
        @NotNull(message = "Quantity is required.")
        @Positive(message = "Quantity must be a positive number.")
        Integer quantity,
        @NotNull(message = "Price is required.")
        @Positive(message = "Price must be a positive number.")
        Double price
) {
}

