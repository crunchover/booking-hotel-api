package com.hotel.booking.infrastructure.output.kafka;

import com.hotel.booking.application.service.HashService;
import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.domain.model.SearchRequest;
import com.hotel.booking.domain.port.output.SearchEventPublisherPort;
import com.hotel.booking.shared.exception.GatewayErrorException;
import com.hotel.booking.shared.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SearchEventProducer implements SearchEventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(SearchEventProducer.class);
    private static final String TOPIC = "hotel_availability_searches";

    private final KafkaTemplate<String, SearchEvent> kafkaTemplate;
    private final HashService hashService;

    public SearchEventProducer(KafkaTemplate<String, SearchEvent> kafkaTemplate, HashService hashService) {
        this.kafkaTemplate = kafkaTemplate;
        this.hashService = hashService;
    }

    @Override
    public String publish(SearchRequest searchRequest) {
        final String hash = hashService.calculateSearchHash(searchRequest);
        SearchEvent event = buildEvent(searchRequest, hash);

        kafkaTemplate.send(TOPIC, hash, event)
                .whenComplete((result, ex) -> {
                    if (Objects.nonNull(ex)) {
                        log.warn("Failed to send kafka message: {}", ex.getMessage());
                        throw new GatewayErrorException(String.format(
                                "Error publishing search event for hotelId=%s. Please retry later.",
                                searchRequest.hotelId()
                        ));
                    } else {
                        log.info("Message sent: hash={}", hash);
                    }
                });

        return hash;
    }

    private SearchEvent buildEvent(SearchRequest req, String hash) {
        return new SearchEvent(
                hash,
                req.hotelId(),
                DateUtils.parseDate(req.checkIn()),
                DateUtils.parseDate(req.checkOut()),
                req.ages()
        );
    }
}
