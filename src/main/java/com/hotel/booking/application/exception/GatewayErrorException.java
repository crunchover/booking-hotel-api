package com.hotel.booking.application.exception;

public class GatewayErrorException extends RuntimeException {
    public GatewayErrorException(String message) {
        super(message);
    }
}
