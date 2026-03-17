package com.tuempresa.booking.hotel.api.usecase.listener;

import com.tuempresa.booking.hotel.api.model.SearchEvent;
import com.tuempresa.booking.hotel.api.usecase.listener.impl.HotelSearchEventListenerUseCaseImpl;
import com.tuempresa.booking.hotel.api.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelSearchEventListenerUseCaseImplTest {
    private PersistHotelSearchUseCase persistHotelSearchUseCase;
    private HotelSearchEventListenerUseCaseImpl listener;

    @BeforeEach
    void setUp() {
        persistHotelSearchUseCase = mock(PersistHotelSearchUseCase.class);
        listener = new HotelSearchEventListenerUseCaseImpl(persistHotelSearchUseCase);
    }

    @Test
    void shouldDelegateToPersistUseCaseAndReturnResult() {
        // Given
        SearchEvent event = new SearchEvent(
                "hash123",
                "HOTEL-ABC",
                Utils.formatDate("01/01/2024"),
                Utils.formatDate( "03/01/2024"),
                java.util.List.of(25, 3)
        );

        when(persistHotelSearchUseCase.apply(event)).thenReturn("persisted-123");

        // When
        String result = listener.apply(event);

        // Then
        assertEquals("persisted-123", result);
        verify(persistHotelSearchUseCase, times(1)).apply(event);
    }
}
