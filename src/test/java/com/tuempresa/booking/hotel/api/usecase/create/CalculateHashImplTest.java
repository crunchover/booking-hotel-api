package com.tuempresa.booking.hotel.api.usecase.create;

import com.tuempresa.booking.hotel.api.model.SearchRequest;
import com.tuempresa.booking.hotel.api.usecase.create.impl.CalculateHashImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CalculateHashImplTest {
    private final CalculateHash calculateHash = new CalculateHashImpl();

    @Test
    void shouldReturnHashWhenValidRequestGiven() {
        // Given
        SearchRequest request = new SearchRequest(
                "1234aBc",
                "29/12/2023",
                "31/12/2023",
                List.of(30, 29, 1, 3)
        );

        // When
        String hash = calculateHash.calculateSearchHash(request);

        // Then
        assertNotNull(hash);
        assertFalse(hash.isBlank());

    }
}

