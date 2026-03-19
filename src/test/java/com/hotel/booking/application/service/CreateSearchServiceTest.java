package com.hotel.booking.application.service;

import com.hotel.booking.domain.model.SearchRequest;
import com.hotel.booking.domain.port.output.SearchEventPublisherPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateSearchServiceTest {

    private static final SearchRequest VALID_REQUEST = new SearchRequest(
            "1234aBc", "29/12/2023", "31/12/2023", List.of(30, 29, 1, 3)
    );

    private SearchEventPublisherPort publisherPort;
    private CreateSearchService service;

    @BeforeEach
    void setUp() {
        publisherPort = mock(SearchEventPublisherPort.class);
        service = new CreateSearchService(publisherPort);
    }

    @Test
    void shouldReturnHashFromPublisher() {
        when(publisherPort.publish(VALID_REQUEST)).thenReturn("abc123");
        String result = service.apply(VALID_REQUEST);
        assertAll(
                () -> assertEquals("abc123", result),
                () -> verify(publisherPort).publish(VALID_REQUEST)
        );
    }

    @Test
    void shouldDelegateToPublisherPort() {
        service.apply(VALID_REQUEST);
        verify(publisherPort, times(1)).publish(VALID_REQUEST);
    }
}
