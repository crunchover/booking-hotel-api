package com.hotel.booking.domain.model;

import java.time.LocalDate;
import java.util.List;

public record SearchEvent(
        String hash,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages
) {
    public SearchEvent {
        if (ages != null) {
            ages = List.copyOf(ages);
        }
    }
}
