package com.hotel.booking.domain.port.output;

import com.hotel.booking.domain.model.HotelSearchData;
import com.hotel.booking.domain.model.SearchEvent;

import java.util.Optional;

public interface SearchPersistencePort {
    Optional<HotelSearchData> findByHash(String hash);
    void persist(SearchEvent event);
    void incrementCount(String hash);
}
