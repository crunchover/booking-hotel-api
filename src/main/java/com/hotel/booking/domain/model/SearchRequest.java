package com.hotel.booking.domain.model;

import java.util.List;

public record SearchRequest(
        String hotelId,
        String checkIn,
        String checkOut,
        List<Integer> ages
) {
    public SearchRequest {
        if (ages != null) {
            ages = List.copyOf(ages);
        }
    }
}
