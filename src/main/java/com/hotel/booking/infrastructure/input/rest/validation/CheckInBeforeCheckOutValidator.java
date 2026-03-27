package com.hotel.booking.infrastructure.input.rest.validation;

import com.hotel.booking.infrastructure.input.rest.dto.SearchRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CheckInBeforeCheckOutValidator implements ConstraintValidator<CheckInBeforeCheckOut, SearchRequestDto> {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public boolean isValid(SearchRequestDto request, ConstraintValidatorContext context) {
        if (request == null) return true;
        if (request.checkIn() == null || request.checkOut() == null) return true;

        try {
            LocalDate checkinDate = LocalDate.parse(request.checkIn(), fmt);
            LocalDate checkOutDate = LocalDate.parse(request.checkOut(), fmt);
            return checkinDate.isBefore(checkOutDate);
        } catch (DateTimeParseException e) {
            return true;
        }
    }
}
