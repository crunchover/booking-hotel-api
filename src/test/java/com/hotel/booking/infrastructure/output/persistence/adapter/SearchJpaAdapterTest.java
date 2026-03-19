package com.hotel.booking.infrastructure.output.persistence.adapter;

import com.hotel.booking.domain.model.HotelSearchData;
import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.infrastructure.output.persistence.entity.HotelSearchEntity;
import com.hotel.booking.infrastructure.output.persistence.repository.HotelSearchJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchJpaAdapterTest {

    private static final String HASH = "test-hash";
    private static final SearchEvent EVENT = new SearchEvent(
            HASH,
            "hotel1",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 25)
    );

    private HotelSearchJpaRepository repository;
    private SearchJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(HotelSearchJpaRepository.class);
        adapter = new SearchJpaAdapter(repository);
    }

    @Test
    void shouldReturnEmptyWhenHashNotFound() {
        when(repository.findByHash(HASH)).thenReturn(Optional.empty());
        Optional<HotelSearchData> result = adapter.findByHash(HASH);
        assertAll(
                () -> assertTrue(result.isEmpty()),
                () -> verify(repository).findByHash(HASH)
        );
    }

    @Test
    void shouldPersistNewEvent() {
        adapter.persist(EVENT);
        verify(repository).save(any(HotelSearchEntity.class));
    }

    @Test
    void shouldIncrementCount() {
        adapter.incrementCount(HASH);
        verify(repository).incrementSearchCount(HASH);
    }

    @Test
    void shouldReturnHotelSearchDataWhenFound() {
        HotelSearchEntity entity = new HotelSearchEntity.Builder()
                .hotelId("hotel1")
                .checkIn(LocalDate.of(2023, 12, 29))
                .checkOut(LocalDate.of(2023, 12, 31))
                .ages(List.of(30, 25))
                .hash(HASH)
                .searchCount(3L)
                .build();
        when(repository.findByHash(HASH)).thenReturn(Optional.of(entity));

        Optional<HotelSearchData> result = adapter.findByHash(HASH);
        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(HASH, result.get().hash()),
                () -> assertEquals("hotel1", result.get().hotelId()),
                () -> assertEquals(3L, result.get().searchCount())
        );
    }
}
