package com.hotel.booking.shared.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void shouldParseDateCorrectly() {
        LocalDate result = DateUtils.parseDate("29/12/2023");
        assertAll(
                () -> assertEquals(2023, result.getYear()),
                () -> assertEquals(12, result.getMonthValue()),
                () -> assertEquals(29, result.getDayOfMonth())
        );
    }

    @Test
    void shouldGenerateSha256Hash() {
        String hash = DateUtils.sha256("test-input");
        assertNotNull(hash);
        assertFalse(hash.isBlank());
    }

    @Test
    void shouldProduceSameHashForSameInput() {
        String h1 = DateUtils.sha256("same");
        String h2 = DateUtils.sha256("same");
        assertEquals(h1, h2);
    }

    @Test
    void shouldProduceDifferentHashForDifferentInput() {
        String h1 = DateUtils.sha256("input-a");
        String h2 = DateUtils.sha256("input-b");
        assertNotEquals(h1, h2);
    }
}
