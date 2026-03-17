package com.tuempresa.booking.hotel.api.usecase.listener;

import com.tuempresa.booking.hotel.api.model.SearchEvent;

public interface HotelSearchEventListenerUseCase {
    String apply(SearchEvent searchEvent);
}
