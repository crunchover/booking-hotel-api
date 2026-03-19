package com.hotel.booking.shared.exception;

public class GatewayErrorException extends RuntimeException {
    public GatewayErrorException(String message) {
        super(message);
    }
}
