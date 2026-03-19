package com.hotel.booking.domain.model;

import java.time.LocalDate;
import java.util.List;

public record HotelSearchData(
        String hash,
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages,
        long searchCount
) {}
