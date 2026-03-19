package com.hotel.booking.application.service;

import com.hotel.booking.domain.model.HotelSearchData;
import com.hotel.booking.domain.model.SearchCountResponse;
import com.hotel.booking.domain.port.output.SearchPersistencePort;
import com.hotel.booking.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetSearchCountServiceTest {

    private static final String HASH = "test-hash-123";
    private static final HotelSearchData SEARCH_DATA = new HotelSearchData(
            HASH,
            "1234aBc",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 29, 1, 3),
            5L
    );

    private SearchPersistencePort persistencePort;
    private GetSearchCountService service;

    @BeforeEach
    void setUp() {
        persistencePort = mock(SearchPersistencePort.class);
        service = new GetSearchCountService(persistencePort);
    }

    @Test
    void shouldReturnSearchCountResponse() {
        when(persistencePort.findByHash(HASH)).thenReturn(Optional.of(SEARCH_DATA));
        SearchCountResponse response = service.apply(HASH);
        assertAll(
                () -> assertEquals(HASH, response.searchId()),
                () -> assertEquals(5L, response.count()),
                () -> assertEquals("1234aBc", response.search().hotelId()),
                () -> assertEquals("29/12/2023", response.search().checkIn()),
                () -> assertEquals("31/12/2023", response.search().checkOut()),
                () -> assertEquals(List.of(30, 29, 1, 3), response.search().ages())
        );
    }

    @Test
    void shouldThrowResourceNotFoundWhenHashNotFound() {
        when(persistencePort.findByHash(HASH)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.apply(HASH));
    }
}
