package com.tuempresa.booking.hotel.api.usecase.create.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuempresa.booking.hotel.api.model.SearchRequest;
import com.tuempresa.booking.hotel.api.model.exception.GatewayErrorException;
import com.tuempresa.booking.hotel.api.usecase.create.CalculateHash;
import com.tuempresa.booking.hotel.api.util.Utils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalculateHashImpl implements CalculateHash {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String calculateSearchHash(SearchRequest request) {
        return toJson(
                new SearchRequest(
                        request.hotelId(),
                        request.checkIn(),
                        request.checkOut(),
                        request.ages().stream().sorted().toList()
                )
        )
                .map(Utils::sha256)
                .orElseThrow(() -> new GatewayErrorException("Could not generate hash"));
    }

    private static Optional<String> toJson(SearchRequest request) {
        try {
            return Optional.of(CalculateHashImpl.mapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            return java.util.Optional.empty();
        }
    }
}
