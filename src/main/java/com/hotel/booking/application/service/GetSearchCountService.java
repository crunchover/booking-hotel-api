package com.hotel.booking.application.service;

import com.hotel.booking.domain.model.HotelSearchData;
import com.hotel.booking.domain.model.SearchCountResponse;
import com.hotel.booking.domain.port.input.GetSearchCountUseCase;
import com.hotel.booking.domain.port.output.SearchPersistencePort;
import com.hotel.booking.application.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
public class GetSearchCountService implements GetSearchCountUseCase {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final SearchPersistencePort persistencePort;

    public GetSearchCountService(SearchPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    @Transactional(readOnly = true)
    public SearchCountResponse apply(String searchId) {
        HotelSearchData searchData = persistencePort.findByHash(searchId)
                .orElseThrow(() -> new ResourceNotFoundException("Search not found for id: " + searchId));

        var payload = new SearchCountResponse.SearchPayload(
                searchData.hotelId(),
                searchData.checkIn().format(DATE_FORMATTER),
                searchData.checkOut().format(DATE_FORMATTER),
                searchData.ages()
        );

        return new SearchCountResponse(searchId, payload, searchData.searchCount());
    }
}
