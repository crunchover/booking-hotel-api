package com.hotel.booking.infrastructure.input.kafka;

import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.domain.port.input.ProcessSearchEventUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SearchEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(SearchEventConsumer.class);

    private final ProcessSearchEventUseCase processSearchEventUseCase;

    public SearchEventConsumer(ProcessSearchEventUseCase processSearchEventUseCase) {
        this.processSearchEventUseCase = processSearchEventUseCase;
    }

    @KafkaListener(topics = "hotel_availability_searches", groupId = "hotel-search-consumer-group")
    public void consume(SearchEvent event) {
        log.info("Received search event for hotelId={}, hash={}", event.hotelId(), event.hash());
        processSearchEventUseCase.accept(event);
    }
}
