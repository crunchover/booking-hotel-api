package com.hotel.booking.domain.model;

import com.hotel.booking.shared.validation.CheckInBeforeCheckOut;
import jakarta.validation.constraints.*;
import java.util.List;

@CheckInBeforeCheckOut
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
    public SearchRequest {
        if (ages != null) {
            ages = List.copyOf(ages);
        }
    }
}
