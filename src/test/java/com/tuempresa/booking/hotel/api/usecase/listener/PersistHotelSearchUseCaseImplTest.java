package com.tuempresa.booking.hotel.api.usecase.listener;

import com.tuempresa.booking.hotel.api.model.SearchEvent;
import com.tuempresa.booking.hotel.api.model.entity.HotelSearchEntity;
import com.tuempresa.booking.hotel.api.repository.HotelSearchRepository;
import com.tuempresa.booking.hotel.api.usecase.listener.impl.PersistHotelSearchUseCaseImpl;
import com.tuempresa.booking.hotel.api.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersistHotelSearchUseCaseImplTest {
    private HotelSearchRepository repository;
    private PersistHotelSearchUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        repository = mock(HotelSearchRepository.class);
        useCase = new PersistHotelSearchUseCaseImpl(repository);
    }

    @Test
    void shouldUpdateSearchCountWhenHashAlreadyExists() {
        // Given
        String hash = "abc123";
        SearchEvent request = new SearchEvent("hash","HOTEL1",  Utils.formatDate("05/01/2024"),  Utils.formatDate("05/01/2024"), List.of(30, 5));

        HotelSearchEntity existing = new HotelSearchEntity.Builder()
                .id(1L)
                .hotelId("HOTEL1")
                .checkIn(LocalDate.of(2024, 1, 1))
                .checkOut(LocalDate.of(2024, 1, 5))
                .ages(List.of(5, 30))
                .hash(hash)
                .searchCount(1L)
                .build();

        when(repository.findByHash(any())).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        // When
        String result = useCase.apply(request);

        // Then
        assertEquals("1", result);
        assertEquals(2L, existing.getSearchCount());
        verify(repository).save(existing);
    }

    @Test
    void shouldCreateNewSearchWhenHashDoesNotExist() {
        // Given
        String hash = "def456";
        SearchEvent request = new SearchEvent("hash","HOTEL2", Utils.formatDate("10/02/2024"),  Utils.formatDate("15/02/2024"), List.of(25, 1));

        HotelSearchEntity saved = new HotelSearchEntity.Builder()
                .id(99L)
                .hotelId("HOTEL2")
                .checkIn(LocalDate.of(2024, 2, 10))
                .checkOut(LocalDate.of(2024, 2, 15))
                .ages(List.of(1, 25))
                .hash(hash)
                .searchCount(1L)
                .build();

        when(repository.findByHash(hash)).thenReturn(Optional.empty());
        when(repository.save(any(HotelSearchEntity.class))).thenReturn(saved);

        // When
        String result = useCase.apply(request);

        // Then
        assertEquals("99", result);
        verify(repository).save(any(HotelSearchEntity.class));
    }
}
