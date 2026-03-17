package com.tuempresa.booking.hotel.api.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuempresa.booking.hotel.api.model.SearchRequest;
import com.tuempresa.booking.hotel.api.usecase.create.CreateSearchUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateSearchEndPointTest {
    private CreateSearchUseCase createSearchUseCase;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        createSearchUseCase = mock(CreateSearchUseCase.class);
        CreateSearchEndPoint controller = new CreateSearchEndPoint(createSearchUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateSearchAndReturn201WithLocation() throws Exception {
        // Given
        SearchRequest request = new SearchRequest(
                "1234aBc",
                "29/12/2023",
                "31/12/2023",
                List.of(30, 29, 1, 3)
        );

        when(createSearchUseCase.apply(request)).thenReturn("abc123");

        // When / Then
        mockMvc.perform(post("/booking/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/booking/search/abc123"))
                .andExpect(jsonPath("$.searchId").value("abc123"));

        verify(createSearchUseCase).apply(request);
    }
}
