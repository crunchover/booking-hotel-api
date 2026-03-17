package com.tuempresa.booking.hotel.api.util;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @Test
    void shouldReturnValidBase64Sha256Hash() {
        // Given
        String input = "test-input";
        String expectedHash = calculateExpectedSha256Base64(input);

        // When
        String actualHash = Utils.sha256(input);

        // Then
        assertEquals(expectedHash, actualHash);
    }

    private String calculateExpectedSha256Base64(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
