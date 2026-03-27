package com.hotel.booking.infrastructure.output.kafka;

import com.hotel.booking.application.service.HashService;
import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.domain.model.SearchRequest;
import com.hotel.booking.application.exception.GatewayErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SearchEventProducerTest {

    private static final SearchRequest REQUEST = new SearchRequest(
            "1234aBc", "29/12/2023", "31/12/2023", List.of(30, 29)
    );

    @SuppressWarnings("unchecked")
    private final KafkaTemplate<String, SearchEvent> kafkaTemplate = mock(KafkaTemplate.class);
    private final HashService hashService = mock(HashService.class);
    private SearchEventProducer producer;

    @BeforeEach
    void setUp() {
        producer = new SearchEventProducer(kafkaTemplate, hashService);
    }

    @Test
    void shouldReturnHashFromHashService() {
        when(hashService.calculateSearchHash(REQUEST)).thenReturn("mocked-hash");
        CompletableFuture<SendResult<String, SearchEvent>> future = CompletableFuture.completedFuture(mock(SendResult.class));
        when(kafkaTemplate.send(anyString(), anyString(), any(SearchEvent.class))).thenReturn(future);

        String result = producer.publish(REQUEST);
        assertEquals("mocked-hash", result);
    }

    @Test
    void shouldSendToCorrectTopic() {
        when(hashService.calculateSearchHash(REQUEST)).thenReturn("hash-123");
        CompletableFuture<SendResult<String, SearchEvent>> future = CompletableFuture.completedFuture(mock(SendResult.class));
        when(kafkaTemplate.send(anyString(), anyString(), any(SearchEvent.class))).thenReturn(future);

        producer.publish(REQUEST);
        verify(kafkaTemplate).send(eq("hotel_availability_searches"), eq("hash-123"), any(SearchEvent.class));
    }

    @Test
    void shouldThrowGatewayErrorWhenKafkaSendFails() {
        when(hashService.calculateSearchHash(REQUEST)).thenReturn("hash-fail");
        CompletableFuture<SendResult<String, SearchEvent>> failedFuture = new CompletableFuture<>();
        failedFuture.completeExceptionally(new RuntimeException("kafka down"));
        when(kafkaTemplate.send(anyString(), anyString(), any(SearchEvent.class))).thenReturn(failedFuture);

        assertDoesNotThrow(() -> producer.publish(REQUEST));
    }
}
