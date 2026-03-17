package com.tuempresa.booking.hotel.api.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GatewayErrorException extends RuntimeException {

  public GatewayErrorException(final String message) {
    super(String.format("Error %s", message));
  }
  public GatewayErrorException(final String message, final Throwable cause) {
    super(String.format("Error %s detail %s", message, cause.getMessage()));
  }
}
