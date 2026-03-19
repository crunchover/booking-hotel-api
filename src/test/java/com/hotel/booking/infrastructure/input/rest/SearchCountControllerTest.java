package com.hotel.booking.infrastructure.input.rest;

import com.hotel.booking.domain.model.SearchCountResponse;
import com.hotel.booking.domain.port.input.GetSearchCountUseCase;
import com.hotel.booking.infrastructure.input.rest.advice.GlobalExceptionHandler;
import com.hotel.booking.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SearchCountControllerTest {

    private static final String SEARCH_ID = "test-hash-abc";
    private static final SearchCountResponse RESPONSE = new SearchCountResponse(
            SEARCH_ID,
            new SearchCountResponse.SearchPayload("hotel1", "01/01/2024", "05/01/2024", List.of(30)),
            3L
    );

    private GetSearchCountUseCase getSearchCountUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        getSearchCountUseCase = mock(GetSearchCountUseCase.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new SearchCountController(getSearchCountUseCase))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturnCountResponse() throws Exception {
        when(getSearchCountUseCase.apply(SEARCH_ID)).thenReturn(RESPONSE);

        mockMvc.perform(get("/count").param("searchId", SEARCH_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value(SEARCH_ID))
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.search.hotelId").value("hotel1"));
    }

    @Test
    void shouldReturn404WhenSearchNotFound() throws Exception {
        when(getSearchCountUseCase.apply(SEARCH_ID)).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/count").param("searchId", SEARCH_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenSearchIdIsMissing() throws Exception {
        mockMvc.perform(get("/count"))
                .andExpect(status().isBadRequest());
    }
}
