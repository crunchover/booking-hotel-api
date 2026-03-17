package com.tuempresa.booking.hotel.api.model;

import java.util.List;

public record SearchCountResponse(
    String searchId,
    SearchPayload search,
    long count
) {
        public record SearchPayload(
                String hotelId,
                String checkIn,
                String checkOut,
                List<Integer> ages
        ) {}
}
