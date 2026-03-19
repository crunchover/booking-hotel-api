package com.hotel.booking.domain.model;

public record SearchCountResponse(
        String searchId,
        SearchPayload search,
        long count
) {
    public record SearchPayload(
            String hotelId,
            String checkIn,
            String checkOut,
            java.util.List<Integer> ages
    ) {}
}
