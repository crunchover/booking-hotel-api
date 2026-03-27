package com.hotel.booking.infrastructure.input.rest.validation;

import com.hotel.booking.infrastructure.input.rest.dto.SearchRequestDto;
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
        SearchRequestDto dto = new SearchRequestDto("hotel1", "01/01/2024", "05/01/2024", List.of(30));
        Set<ConstraintViolation<SearchRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenCheckInIsAfterCheckOut() {
        SearchRequestDto dto = new SearchRequestDto("hotel1", "10/01/2024", "05/01/2024", List.of(30));
        Set<ConstraintViolation<SearchRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenCheckInEqualsCheckOut() {
        SearchRequestDto dto = new SearchRequestDto("hotel1", "05/01/2024", "05/01/2024", List.of(30));
        Set<ConstraintViolation<SearchRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassWhenDatesAreInvalidFormatDeferringToPatternValidator() {
        CheckInBeforeCheckOutValidator v = new CheckInBeforeCheckOutValidator();
        SearchRequestDto dto = new SearchRequestDto("h", "not-a-date", "also-not", List.of(1));
        assertTrue(v.isValid(dto, null));
    }

    @Test
    void shouldReturnTrueForNullRequest() {
        CheckInBeforeCheckOutValidator v = new CheckInBeforeCheckOutValidator();
        assertTrue(v.isValid(null, null));
    }

    @Test
    void shouldReturnTrueWhenCheckInIsNull() {
        CheckInBeforeCheckOutValidator v = new CheckInBeforeCheckOutValidator();
        SearchRequestDto dto = new SearchRequestDto("h", null, "05/01/2024", List.of(1));
        assertTrue(v.isValid(dto, null));
    }
}
