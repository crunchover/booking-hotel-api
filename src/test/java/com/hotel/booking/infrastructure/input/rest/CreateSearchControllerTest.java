package com.hotel.booking.infrastructure.input.rest;

import com.hotel.booking.domain.model.SearchRequest;
import com.hotel.booking.domain.port.input.CreateSearchUseCase;
import com.hotel.booking.infrastructure.input.rest.advice.GlobalExceptionHandler;
import com.hotel.booking.infrastructure.input.rest.dto.SearchRequestDto;
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

    private static final SearchRequest VALID_DOMAIN_REQUEST = new SearchRequest(
            "1234aBc", "29/12/2023", "31/12/2023", List.of(30, 29, 1, 3)
    );

    private static final String VALID_REQUEST_JSON = """
            {
              "hotelId": "1234aBc",
              "checkIn": "29/12/2023",
              "checkOut": "31/12/2023",
              "ages": [30, 29, 1, 3]
            }
            """;

    private static final String BLANK_HOTEL_ID_JSON = """
            {
              "hotelId": "",
              "checkIn": "29/12/2023",
              "checkOut": "31/12/2023",
              "ages": [30]
            }
            """;

    private static final String EMPTY_AGES_JSON = """
            {
              "hotelId": "h1",
              "checkIn": "01/01/2024",
              "checkOut": "05/01/2024",
              "ages": []
            }
            """;

    private static final String WRONG_CHECK_IN_FORMAT_JSON = """
            {
              "hotelId": "h1",
              "checkIn": "2024-01-01",
              "checkOut": "05/01/2024",
              "ages": [30]
            }
            """;

    private CreateSearchUseCase createSearchUseCase;
    private MockMvc mockMvc;

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
        when(createSearchUseCase.apply(VALID_DOMAIN_REQUEST)).thenReturn("abc123");

        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_REQUEST_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/search/abc123"))
                .andExpect(jsonPath("$.searchId").value("abc123"));
    }

    @Test
    void shouldReturn400WhenHotelIdIsBlank() throws Exception {
        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BLANK_HOTEL_ID_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenAgesIsEmpty() throws Exception {
        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(EMPTY_AGES_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenCheckInFormatIsWrong() throws Exception {
        mockMvc.perform(post("/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(WRONG_CHECK_IN_FORMAT_JSON))
                .andExpect(status().isBadRequest());
    }
}
