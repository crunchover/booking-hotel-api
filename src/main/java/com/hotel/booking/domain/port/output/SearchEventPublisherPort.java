package com.hotel.booking.domain.port.output;

import com.hotel.booking.domain.model.SearchRequest;

public interface SearchEventPublisherPort {
    String publish(SearchRequest searchRequest);
}
