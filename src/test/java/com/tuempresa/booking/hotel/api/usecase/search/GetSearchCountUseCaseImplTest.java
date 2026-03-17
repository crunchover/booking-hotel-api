package com.tuempresa.booking.hotel.api.usecase.search;

import com.tuempresa.booking.hotel.api.model.SearchCountResponse;
import com.tuempresa.booking.hotel.api.model.entity.HotelSearchEntity;
import com.tuempresa.booking.hotel.api.model.exception.ResourceNotFoundException;
import com.tuempresa.booking.hotel.api.repository.HotelSearchRepository;
import com.tuempresa.booking.hotel.api.usecase.search.impl.GetSearchCountUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetSearchCountUseCaseImplTest {

    private HotelSearchRepository repository;
    private GetSearchCountUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        repository = mock(HotelSearchRepository.class);
        useCase = new GetSearchCountUseCaseImpl(repository);
    }

    @Test
    void shouldReturnSearchCountResponseWhenSearchExists() {
        // Given
        String hash = "123";
        HotelSearchEntity entity = new HotelSearchEntity.Builder()
                .id(123L)
                .searchId(hash)
                .hotelId("HOTEL-XYZ")
                .checkIn(LocalDate.of(2024, 3, 1))
                .checkOut(LocalDate.of(2024, 3, 5))
                .ages(List.of(5, 30))
                .searchCount(42L)
                .build();

        when(repository.findByHash(hash)).thenReturn(Optional.of(entity));

        // When
        SearchCountResponse response = useCase.execute(hash);

        // Then
        assertNotNull(response);
        assertEquals("123", response.searchId());
        assertEquals(42L, response.count());
        assertEquals("HOTEL-XYZ", response.search().hotelId());
        assertEquals("01/03/2024", response.search().checkIn());
        assertEquals("05/03/2024", response.search().checkOut());
        assertEquals(List.of(5, 30), response.search().ages());

        verify(repository).findByHash(hash);
    }

    @Test
    void shouldThrowExceptionWhenSearchDoesNotExist() {
        // Given
        String hash = "not-found-id";
        when(repository.findByHash(hash)).thenReturn(Optional.empty());

        // When / Then
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            useCase.execute(hash);
        });

        assertEquals("Resource Search not found not found", ex.getMessage());
        verify(repository).findByHash(hash);
    }
}
