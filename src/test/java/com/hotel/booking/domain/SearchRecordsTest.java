package com.hotel.booking.domain;

import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.domain.model.SearchRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchRecordsTest {

    @Test
    void searchRequestShouldCopyAgesList() {
        List<Integer> ages = new java.util.ArrayList<>(List.of(30, 25));
        SearchRequest request = new SearchRequest("hotel1", "01/01/2024", "05/01/2024", ages);
        ages.add(99);
        assertEquals(2, request.ages().size());
    }

    @Test
    void searchRequestWithNullAgesShouldNotThrow() {
        SearchRequest request = new SearchRequest("hotel1", "01/01/2024", "05/01/2024", null);
        assertNull(request.ages());
    }

    @Test
    void searchEventShouldCopyAgesList() {
        List<Integer> ages = new java.util.ArrayList<>(List.of(30, 25));
        SearchEvent event = new SearchEvent("hash", "hotel1",
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), ages);
        ages.add(99);
        assertEquals(2, event.ages().size());
    }

    @Test
    void searchEventWithNullAgesShouldNotThrow() {
        SearchEvent event = new SearchEvent("hash", "hotel1",
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), null);
        assertNull(event.ages());
    }
}
