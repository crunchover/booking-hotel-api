package com.hotel.booking.infrastructure.output.persistence.adapter;

import com.hotel.booking.domain.model.HotelSearchData;
import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.domain.port.output.SearchPersistencePort;
import com.hotel.booking.infrastructure.output.persistence.entity.HotelSearchEntity;
import com.hotel.booking.infrastructure.output.persistence.repository.HotelSearchJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class SearchJpaAdapter implements SearchPersistencePort {

    private final HotelSearchJpaRepository repository;

    public SearchJpaAdapter(HotelSearchJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<HotelSearchData> findByHash(String hash) {
        return repository.findByHash(hash).map(this::toHotelSearchData);
    }

    @Override
    @Transactional
    public void persist(SearchEvent event) {
        HotelSearchEntity entity = new HotelSearchEntity.Builder()
                .hotelId(event.hotelId())
                .checkIn(event.checkIn())
                .checkOut(event.checkOut())
                .ages(event.ages())
                .hash(event.hash())
                .searchCount(1L)
                .build();
        repository.save(entity);
    }

    @Override
    @Transactional
    public void incrementCount(String hash) {
        repository.incrementSearchCount(hash);
    }

    private HotelSearchData toHotelSearchData(HotelSearchEntity e) {
        return new HotelSearchData(
                e.getHash(),
                e.getHotelId(),
                e.getCheckIn(),
                e.getCheckOut(),
                e.getAges(),
                e.getSearchCount()
        );
    }
}
