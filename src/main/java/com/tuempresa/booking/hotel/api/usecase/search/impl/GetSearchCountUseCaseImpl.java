package com.tuempresa.booking.hotel.api.usecase.search.impl;

import com.tuempresa.booking.hotel.api.model.SearchCountResponse;
import com.tuempresa.booking.hotel.api.model.exception.ResourceNotFoundException;
import com.tuempresa.booking.hotel.api.repository.HotelSearchRepository;
import com.tuempresa.booking.hotel.api.usecase.search.GetSearchCountUseCase;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;


@Service
public class GetSearchCountUseCaseImpl implements GetSearchCountUseCase {

    private final HotelSearchRepository hotelSearchRepository;

    public GetSearchCountUseCaseImpl(HotelSearchRepository hotelSearchRepository) {
        this.hotelSearchRepository = hotelSearchRepository;
    }

    @Override
    public SearchCountResponse execute(String hash) {

        return hotelSearchRepository.findByHash(hash)
                .map(hotelSearchEntity -> {
                    SearchCountResponse.SearchPayload payload = new SearchCountResponse.SearchPayload(
                            hotelSearchEntity.getHotelId(),
                            hotelSearchEntity.getCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            hotelSearchEntity.getCheckOut().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            hotelSearchEntity.getAges()
                    );
                    return new SearchCountResponse(
                            String.valueOf(hotelSearchEntity.getId()),
                            payload,
                            hotelSearchEntity.getSearchCount()
                    );
                })
                .orElseThrow(() -> new ResourceNotFoundException("Search not found"));
    }
}
