package com.tuempresa.booking.hotel.api.usecase.listener;

import com.tuempresa.booking.hotel.api.model.SearchEvent;


public interface PersistHotelSearchUseCase {
    String apply(SearchEvent request);

}
