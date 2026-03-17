package com.tuempresa.booking.hotel.api.endpoint;

import com.tuempresa.booking.hotel.api.model.SearchEvent;
import com.tuempresa.booking.hotel.api.usecase.listener.HotelSearchEventListenerUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelSearchEventListenerEndpointTest {

    private HotelSearchEventListenerUseCase useCase;
    private HotelSearchEventListenerEndpoint listener;

    @BeforeEach
    void setUp() {
        useCase = mock(HotelSearchEventListenerUseCase.class);
        listener = new HotelSearchEventListenerEndpoint(useCase);
    }

    @Test
    void shouldConsumeEventAndPersistIt() {
        // Given
        SearchEvent event = new SearchEvent(
                "hash123",
                "HOTEL-123",
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 5),
                List.of(30, 10)
        );

        when(useCase.apply(event)).thenReturn("event-id-789");

        // When
        listener.consume(event);

        // Then
        verify(useCase, times(1)).apply(event);
    }
}
