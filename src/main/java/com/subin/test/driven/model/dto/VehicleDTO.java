package com.subin.test.driven.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record VehicleDTO(
        @NotNull(message = "Make cannot be null") String make,
        @NotNull(message = "Model cannot be null") String model,
        @Min(value = 1998, message = "Year must be greater than 1998") int year,
        @PositiveOrZero(message = "Price cannot be negative") double price,
        @PositiveOrZero(message = "Mileage cannot be negative") double mileage
        ) {
}
