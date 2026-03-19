package com.hotel.booking.infrastructure.input.kafka;

import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.domain.port.input.ProcessSearchEventUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

class SearchEventConsumerTest {

    private static final SearchEvent EVENT = new SearchEvent(
            "hash-abc",
            "hotel1",
            LocalDate.of(2023, 12, 29),
            LocalDate.of(2023, 12, 31),
            List.of(30, 25)
    );

    private ProcessSearchEventUseCase processSearchEventUseCase;
    private SearchEventConsumer consumer;

    @BeforeEach
    void setUp() {
        processSearchEventUseCase = mock(ProcessSearchEventUseCase.class);
        consumer = new SearchEventConsumer(processSearchEventUseCase);
    }

    @Test
    void shouldDelegateToUseCase() {
        consumer.consume(EVENT);
        verify(processSearchEventUseCase, times(1)).accept(EVENT);
    }
}
