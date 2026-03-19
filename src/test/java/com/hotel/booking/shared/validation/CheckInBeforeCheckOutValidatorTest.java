package com.hotel.booking.shared.validation;

import com.hotel.booking.domain.model.SearchRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CheckInBeforeCheckOutValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldPassWhenCheckInIsBeforeCheckOut() {
        SearchRequest request = new SearchRequest("hotel1", "01/01/2024", "05/01/2024", List.of(30));
        Set<ConstraintViolation<SearchRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenCheckInIsAfterCheckOut() {
        SearchRequest request = new SearchRequest("hotel1", "10/01/2024", "05/01/2024", List.of(30));
        Set<ConstraintViolation<SearchRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenCheckInEqualsCheckOut() {
        SearchRequest request = new SearchRequest("hotel1", "05/01/2024", "05/01/2024", List.of(30));
        Set<ConstraintViolation<SearchRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassWhenDatesAreInvalidFormatDeferringToPatternValidator() {
        CheckInBeforeCheckOutValidator v = new CheckInBeforeCheckOutValidator();
        SearchRequest request = new SearchRequest("h", "not-a-date", "also-not", List.of(1));
        assertTrue(v.isValid(request, null));
    }

    @Test
    void shouldReturnTrueForNullRequest() {
        CheckInBeforeCheckOutValidator v = new CheckInBeforeCheckOutValidator();
        assertTrue(v.isValid(null, null));
    }

    @Test
    void shouldReturnTrueWhenCheckInIsNull() {
        CheckInBeforeCheckOutValidator v = new CheckInBeforeCheckOutValidator();
        SearchRequest request = new SearchRequest("h", null, "05/01/2024", List.of(1));
        assertTrue(v.isValid(request, null));
    }
}
