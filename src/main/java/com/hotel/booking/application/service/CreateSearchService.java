package com.hotel.booking.application.service;

import com.hotel.booking.domain.port.input.CreateSearchUseCase;
import com.hotel.booking.domain.port.output.SearchEventPublisherPort;
import com.hotel.booking.domain.model.SearchRequest;
import org.springframework.stereotype.Service;

@Service
public class CreateSearchService implements CreateSearchUseCase {

    private final SearchEventPublisherPort publisherPort;

    public CreateSearchService(SearchEventPublisherPort publisherPort) {
        this.publisherPort = publisherPort;
    }

    @Override
    public String apply(SearchRequest request) {
        return publisherPort.publish(request);
    }
}
