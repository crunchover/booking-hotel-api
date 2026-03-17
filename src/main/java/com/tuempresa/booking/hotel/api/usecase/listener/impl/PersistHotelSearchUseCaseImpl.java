package com.tuempresa.booking.hotel.api.usecase.listener.impl;

import com.tuempresa.booking.hotel.api.model.SearchEvent;
import com.tuempresa.booking.hotel.api.model.entity.HotelSearchEntity;
import com.tuempresa.booking.hotel.api.repository.HotelSearchRepository;
import com.tuempresa.booking.hotel.api.usecase.listener.PersistHotelSearchUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;


@Service
public class PersistHotelSearchUseCaseImpl implements PersistHotelSearchUseCase {

    private final HotelSearchRepository hotelSearchRepository;
    private static final Logger logger = Logger.getLogger(PersistHotelSearchUseCaseImpl.class.getName());

    public PersistHotelSearchUseCaseImpl(HotelSearchRepository hotelSearchRepository) {
        this.hotelSearchRepository = hotelSearchRepository;
    }

    @Override
    public String apply(SearchEvent request) {

        Optional<HotelSearchEntity> existingSearch = hotelSearchRepository.findByHash(request.hash());

        if (existingSearch.isPresent()) {
            HotelSearchEntity entity = existingSearch.get();
            entity.setSearchCount(entity.getSearchCount() + 1);
            logger.info(() -> String.format("Search found for hotelId=%s, incrementing count to %d",
                    entity.getHotelId(), entity.getSearchCount()));
            return String.valueOf(hotelSearchRepository.save(entity).getId());
        }

        logger.info(() -> String.format("Creating new hotel search for hotelId=%s", request.hotelId()));

        HotelSearchEntity newSearch = new HotelSearchEntity.Builder()
                .hotelId(request.hotelId())
                .checkIn(request.checkIn())
                .checkOut(request.checkOut())
                .ages(request.ages())
                .hash(request.hash())
                .searchCount(1L)
                .build();

        return String.valueOf(hotelSearchRepository.save(newSearch).getId());
    }
}
