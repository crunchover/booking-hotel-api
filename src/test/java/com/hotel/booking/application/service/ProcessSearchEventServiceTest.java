package com.hotel.booking.application.service;

import com.hotel.booking.domain.model.HotelSearchData;
import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.domain.port.output.SearchPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ProcessSearchEventServiceTest {

    private static final SearchEvent EVENT = new SearchEvent(
            "hash-xyz",
            "hotel1",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 25)
    );

    private static final HotelSearchData EXISTING_DATA = new HotelSearchData(
            "hash-xyz", "hotel1",
            LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31),
            List.of(30, 25), 1L
    );

    private SearchPersistencePort persistencePort;
    private ProcessSearchEventService service;

    @BeforeEach
    void setUp() {
        persistencePort = mock(SearchPersistencePort.class);
        service = new ProcessSearchEventService(persistencePort);
    }

    @Test
    void shouldPersistWhenSearchDoesNotExist() {
        when(persistencePort.findByHash(EVENT.hash())).thenReturn(Optional.empty());
        service.accept(EVENT);
        verify(persistencePort).persist(EVENT);
        verify(persistencePort, never()).incrementCount(any());
    }

    @Test
    void shouldIncrementCountWhenSearchExists() {
        when(persistencePort.findByHash(EVENT.hash())).thenReturn(Optional.of(EXISTING_DATA));
        service.accept(EVENT);
        verify(persistencePort).incrementCount(EVENT.hash());
        verify(persistencePort, never()).persist(any());
    }
}
