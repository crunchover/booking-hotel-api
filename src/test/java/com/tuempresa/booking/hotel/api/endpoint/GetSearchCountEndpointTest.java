package com.tuempresa.booking.hotel.api.endpoint;

import com.tuempresa.booking.hotel.api.model.SearchCountResponse;
import com.tuempresa.booking.hotel.api.model.exception.ResourceNotFoundException;
import com.tuempresa.booking.hotel.api.usecase.search.GetSearchCountUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetSearchCountEndpointTest {
    private final GetSearchCountUseCase useCase = mock(GetSearchCountUseCase.class);
    private final GetSearchCountEndpoint endpoint = new GetSearchCountEndpoint(useCase);

    @Test
    void shouldReturnOkWhenSearchExists() {
        // Given
        String searchId = "abc123";
        SearchCountResponse response = new SearchCountResponse(searchId, null, 42);
        when(useCase.execute(searchId)).thenReturn(response);

        // When
        ResponseEntity<SearchCountResponse> result = endpoint.getSearchCount(searchId);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(useCase).execute(searchId);
    }

    @Test
    void shouldReturnNotFoundWhenSearchDoesNotExist() {
        // Given
        String searchId = "missing123";
        when(useCase.execute(searchId)).thenThrow(new ResourceNotFoundException("Search not found"));

        // When / Then
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            endpoint.getSearchCount(searchId);
        });

        assertEquals("Resource Search not found not found", ex.getMessage());
    }

    @Test
    void shouldReturnInternalServerErrorWhenUnexpectedExceptionOccurs() {
        // Given
        String searchId = "any";
        when(useCase.execute(searchId)).thenThrow(new RuntimeException("Error occurred"));

        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            endpoint.getSearchCount(searchId);
        });

        assertEquals("Error occurred", ex.getMessage());
    }
}
