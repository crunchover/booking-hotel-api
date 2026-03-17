package com.tuempresa.booking.hotel.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SearchRequest(

        @NotBlank(message = "hotelId is required")
        String hotelId,

        @NotBlank(message = "checkIn is required")
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "checkIn must be in format dd/MM/yyyy")
        String checkIn,

        @NotBlank(message = "checkOut is required")
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "checkOut must be in format dd/MM/yyyy")
        String checkOut,

        @NotNull(message = "ages list is required")
        @Size(min = 1, message = "ages list cannot be empty")
        List<@Min(0) @Max(120) Integer> ages

) {
}