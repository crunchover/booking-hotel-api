package com.tuempresa.booking.hotel.api.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

  public BadRequestException(final String message) {
    super(String.format("Error %s invalid parameter", message));
  }
}
