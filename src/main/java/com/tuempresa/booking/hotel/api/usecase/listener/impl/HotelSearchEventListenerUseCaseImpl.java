package com.tuempresa.booking.hotel.api.usecase.listener.impl;

import com.tuempresa.booking.hotel.api.model.SearchEvent;
import com.tuempresa.booking.hotel.api.usecase.listener.HotelSearchEventListenerUseCase;
import com.tuempresa.booking.hotel.api.usecase.listener.PersistHotelSearchUseCase;
import org.springframework.stereotype.Service;

@Service
public class HotelSearchEventListenerUseCaseImpl implements HotelSearchEventListenerUseCase {

    private final PersistHotelSearchUseCase persistHotelSearchUseCase;

    public HotelSearchEventListenerUseCaseImpl(PersistHotelSearchUseCase persistHotelSearchUseCase) {
        this.persistHotelSearchUseCase = persistHotelSearchUseCase;
    }

    @Override
    public String apply(SearchEvent searchEvent) {

        return persistHotelSearchUseCase.apply(searchEvent);
    }
}
