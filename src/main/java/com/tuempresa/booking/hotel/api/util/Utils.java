package com.tuempresa.booking.hotel.api.util;

import com.tuempresa.booking.hotel.api.model.exception.GatewayErrorException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;

public class Utils {

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes); // o usa bytesToHex
        } catch (Exception e) {
            throw new GatewayErrorException("Hashing error", e);
        }
    }
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate formatDate(String date) {
        try {
            return LocalDate.parse(date, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected dd/MM/yyyy, got: " + date, e);
        }
    }
}
