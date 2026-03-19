package com.hotel.booking.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.booking.domain.model.SearchRequest;
import com.hotel.booking.shared.exception.GatewayErrorException;
import com.hotel.booking.shared.util.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class HashService {

    private final ObjectMapper mapper;

    public HashService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String calculateSearchHash(SearchRequest request) {
        try {
            String srchJson = mapper.writeValueAsString(request);
            return DateUtils.sha256(srchJson);
        } catch (JsonProcessingException e) {
            throw new GatewayErrorException("Could not generate search hash");
        }
    }
}
