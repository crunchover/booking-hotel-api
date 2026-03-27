package com.hotel.booking.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.booking.domain.model.SearchRequest;
import com.hotel.booking.application.exception.GatewayErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HashServiceTest {

    private static final SearchRequest REQUEST_A = new SearchRequest("hotel1", "01/01/2024", "05/01/2024", List.of(30, 25));
    private static final SearchRequest REQUEST_DIFF_AGES = new SearchRequest("hotel1", "01/01/2024", "05/01/2024", List.of(25, 30));

    private HashService hashService;

    @BeforeEach
    void setUp() {
        hashService = new HashService(new ObjectMapper());
    }

    @Test
    void shouldReturnNonNullHash() {
        String hash = hashService.calculateSearchHash(REQUEST_A);
        assertNotNull(hash);
    }

    @Test
    void shouldBeDeterministic() {
        assertAll(
                () -> assertEquals(hashService.calculateSearchHash(REQUEST_A), hashService.calculateSearchHash(REQUEST_A)),
                () -> assertNotNull(hashService.calculateSearchHash(REQUEST_A))
        );
    }

    @Test
    void shouldProduceDifferentHashWhenAgesOrderDiffers() {
        String hashA = hashService.calculateSearchHash(REQUEST_A);
        String hashB = hashService.calculateSearchHash(REQUEST_DIFF_AGES);
        assertNotEquals(hashA, hashB);
    }

    @Test
    void shouldThrowGatewayErrorWhenSerializationFails() throws Exception {
        ObjectMapper failingMapper = mock(ObjectMapper.class);
        when(failingMapper.writeValueAsString(any())).thenThrow(new com.fasterxml.jackson.core.JsonProcessingException("fail") {});
        HashService failingService = new HashService(failingMapper);
        assertThrows(GatewayErrorException.class, () -> failingService.calculateSearchHash(REQUEST_A));
    }
}
