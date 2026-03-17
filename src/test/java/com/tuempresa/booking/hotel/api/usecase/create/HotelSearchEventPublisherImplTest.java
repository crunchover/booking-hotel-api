package com.tuempresa.booking.hotel.api.usecase.create;

import com.tuempresa.booking.hotel.api.model.SearchEvent;
import com.tuempresa.booking.hotel.api.model.SearchRequest;
import com.tuempresa.booking.hotel.api.usecase.create.impl.HotelSearchEventPublisherImpl;
import com.tuempresa.booking.hotel.api.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelSearchEventPublisherImplTest {
    private KafkaTemplate<String, SearchEvent> kafkaTemplate;
    private CalculateHash calculateHash;
    private HotelSearchEventPublisherImpl publisher;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        calculateHash = mock(CalculateHash.class);
        publisher = new HotelSearchEventPublisherImpl(kafkaTemplate, calculateHash);
    }

    @Test
    void shouldPublishEventSuccessfullyAndReturnHash() {
        // Given
        SearchRequest request = new SearchRequest(
                "1234aBc",
                "29/12/2023",
                "31/12/2023",
                List.of(30, 29, 1, 3)
        );
        String expectedHash = "hash-abc-123";

        when(calculateHash.calculateSearchHash(request)).thenReturn(expectedHash);

        CompletableFuture<SendResult<String, SearchEvent>> future = CompletableFuture.completedFuture(mock(SendResult.class));
        when(kafkaTemplate.send(anyString(), anyString(), any(SearchEvent.class))).thenReturn(future);

        // When
        String actualHash = publisher.publish(request);

        // Then
        assertEquals(expectedHash, actualHash);

        ArgumentCaptor<SearchEvent> eventCaptor = ArgumentCaptor.forClass(SearchEvent.class);
        verify(kafkaTemplate).send(eq("hotel_availability_searches"), eq(expectedHash), eventCaptor.capture());

        SearchEvent sentEvent = eventCaptor.getValue();
        assertEquals(expectedHash, sentEvent.hash());
        assertEquals("1234aBc", sentEvent.hotelId());
        assertEquals(Utils.formatDate("29/12/2023"), sentEvent.checkIn());
        assertEquals(Utils.formatDate("31/12/2023"), sentEvent.checkOut());
        assertEquals(List.of(30, 29, 1, 3), sentEvent.ages());
    }

    @Test
    void shouldLogErrorWhenKafkaFailsButNotThrowImmediately() {
        // Given
        SearchRequest request = new SearchRequest(
                "1234aBc", "29/12/2023", "31/12/2023", List.of(1)
        );

        when(calculateHash.calculateSearchHash(request)).thenReturn("hash-xyz");

        CompletableFuture<SendResult<String, SearchEvent>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka down"));

        when(kafkaTemplate.send(anyString(), anyString(), any(SearchEvent.class))).thenReturn(future);

        // When
        String result = publisher.publish(request);

        // Then
        assertEquals("hash-xyz", result);
    }
}
