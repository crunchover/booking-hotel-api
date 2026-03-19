package com.hotel.booking.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.booking.domain.model.SearchRequest;
import com.hotel.booking.domain.port.input.CreateSearchUseCase;
import com.hotel.booking.infrastructure.input.rest.advice.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CreateSearchControllerTest {

    private static final SearchRequest VALID_REQUEST = new SearchRequest(
            "1234aBc", "29/12/2023", "31/12/2023", List.of(30, 29, 1, 3)
    );

    private CreateSearchUseCase createSearchUseCase;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        createSearchUseCase = mock(CreateSearchUseCase.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CreateSearchController(createSearchUseCase))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturn201WithSearchIdAndLocationHeader() throws Exception {
        when(createSearchUseCase.apply(VALID_REQUEST)).thenReturn("abc123");

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/search/abc123"))
                .andExpect(jsonPath("$.searchId").value("abc123"));
    }

    @Test
    void shouldReturn400WhenHotelIdIsBlank() throws Exception {
        SearchRequest invalidRequest = new SearchRequest("", "29/12/2023", "31/12/2023", List.of(30));

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenAgesIsEmpty() throws Exception {
        var body = "{\"hotelId\":\"h1\",\"checkIn\":\"01/01/2024\",\"checkOut\":\"05/01/2024\",\"ages\":[]}";
        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenCheckInFormatIsWrong() throws Exception {
        var body = "{\"hotelId\":\"h1\",\"checkIn\":\"2024-01-01\",\"checkOut\":\"05/01/2024\",\"ages\":[30]}";
        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
