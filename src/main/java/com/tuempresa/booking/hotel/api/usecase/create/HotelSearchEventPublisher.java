package com.tuempresa.booking.hotel.api.usecase.create;

import com.tuempresa.booking.hotel.api.model.SearchRequest;

public interface HotelSearchEventPublisher {
    String publish(SearchRequest searchRequest);
}
