package com.hotel.booking.infrastructure.input.rest.advice;

import com.hotel.booking.shared.exception.BadRequestException;
import com.hotel.booking.shared.exception.GatewayErrorException;
import com.hotel.booking.shared.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldReturn404ForResourceNotFoundException() {
        ProblemDetail result = handler.handleNotFound(new ResourceNotFoundException("not found"));
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus()),
                () -> assertEquals("not found", result.getDetail())
        );
    }

    @Test
    void shouldReturn500ForGatewayErrorException() {
        ProblemDetail result = handler.handleGatewayError(new GatewayErrorException("upstream error"));
        assertAll(
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatus()),
                () -> assertEquals("upstream error", result.getDetail())
        );
    }

    @Test
    void shouldReturn400ForBadRequestException() {
        ProblemDetail result = handler.handleBadRequest(new BadRequestException("bad input"));
        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus()),
                () -> assertEquals("bad input", result.getDetail())
        );
    }

    @Test
    void shouldReturn400WithFieldErrorsForMethodArgumentNotValid() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("obj", "hotelId", "is required");
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));
        when(bindingResult.getGlobalErrors()).thenReturn(List.of());

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ProblemDetail result = handler.handleValidation(ex);
        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus()),
                () -> assertNotNull(result.getProperties())
        );
    }

    @Test
    void shouldReturn400WithGlobalErrorsForMethodArgumentNotValid() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        org.springframework.validation.ObjectError globalError =
                new org.springframework.validation.ObjectError("SearchRequest", "checkIn must be before checkOut");
        when(bindingResult.getAllErrors()).thenReturn(List.of(globalError));
        when(bindingResult.getGlobalErrors()).thenReturn(List.of(globalError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ProblemDetail result = handler.handleValidation(ex);
        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus()),
                () -> assertNotNull(result.getProperties())
        );
    }

    @Test
    void shouldReturn400ForConstraintViolationException() {
        ProblemDetail result = handler.handleConstraintViolation(new ConstraintViolationException("violation", Set.of()));
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
    }
}
