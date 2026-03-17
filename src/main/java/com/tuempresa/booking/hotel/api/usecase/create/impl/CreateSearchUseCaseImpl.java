package com.tuempresa.booking.hotel.api.usecase.create.impl;

import com.tuempresa.booking.hotel.api.model.SearchRequest;
import com.tuempresa.booking.hotel.api.usecase.create.CreateSearchUseCase;
import com.tuempresa.booking.hotel.api.usecase.create.HotelSearchEventPublisher;
import org.springframework.stereotype.Service;


@Service
public class CreateSearchUseCaseImpl implements CreateSearchUseCase {

    private final HotelSearchEventPublisher hotelSearchEventPublisher;

    public CreateSearchUseCaseImpl(HotelSearchEventPublisher hotelSearchEventPublisher) {
        this.hotelSearchEventPublisher = hotelSearchEventPublisher;
    }

    @Override
    public String apply(SearchRequest searchRequest) {
        return hotelSearchEventPublisher.publish(searchRequest);
    }
}
