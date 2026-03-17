package com.tuempresa.booking.hotel.api.usecase.create;

import com.tuempresa.booking.hotel.api.model.SearchRequest;
import com.tuempresa.booking.hotel.api.usecase.create.impl.CreateSearchUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateSearchUseCaseImplTest {
    private HotelSearchEventPublisher hotelSearchEventPublisher;
    private CreateSearchUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        hotelSearchEventPublisher = mock(HotelSearchEventPublisher.class);
        useCase = new CreateSearchUseCaseImpl(hotelSearchEventPublisher);
    }

    @Test
    void shouldPublishSearchRequestAndReturnSearchId() {
        // Given
        SearchRequest request = new SearchRequest(
                "1234aBc",
                "29/12/2023",
                "31/12/2023",
                List.of(30, 29, 1, 3)
        );

        when(hotelSearchEventPublisher.publish(request)).thenReturn("abc123");

        // When
        String result = useCase.apply(request);

        // Then
        assertEquals("abc123", result);
        verify(hotelSearchEventPublisher).publish(request);
    }
}
