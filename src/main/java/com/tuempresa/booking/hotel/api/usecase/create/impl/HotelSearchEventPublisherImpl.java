package com.tuempresa.booking.hotel.api.usecase.create.impl;

import com.tuempresa.booking.hotel.api.model.SearchEvent;
import com.tuempresa.booking.hotel.api.model.SearchRequest;
import com.tuempresa.booking.hotel.api.model.exception.GatewayErrorException;
import com.tuempresa.booking.hotel.api.usecase.create.CalculateHash;
import com.tuempresa.booking.hotel.api.usecase.create.HotelSearchEventPublisher;
import com.tuempresa.booking.hotel.api.util.Utils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.logging.Logger;

@Service
public class HotelSearchEventPublisherImpl implements HotelSearchEventPublisher {

    private static final Logger logger = Logger.getLogger(HotelSearchEventPublisherImpl.class.getName());
    private static final String TOPIC = "hotel_availability_searches";

    private final KafkaTemplate<String, SearchEvent> kafkaTemplate;
    private final CalculateHash calculateHash;

    public HotelSearchEventPublisherImpl(
            KafkaTemplate<String, SearchEvent> kafkaTemplate,
            CalculateHash calculateHash
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.calculateHash = calculateHash;
    }

    @Override
    public String publish(SearchRequest searchRequest) {
        final String hash = calculateHash.calculateSearchHash(searchRequest);

        SearchEvent event = new SearchEvent(
                hash,
                searchRequest.hotelId(),
                Utils.formatDate(searchRequest.checkIn()),
                Utils.formatDate(searchRequest.checkOut()),
                searchRequest.ages()
        );

        kafkaTemplate.send(TOPIC, hash, event)
                .whenComplete((result, ex) -> {
                    if (Objects.nonNull(ex)) {
                        logger.warning("Error sending message: " + ex.getMessage());
                        throw new GatewayErrorException(String.format(
                                "Error while publishing search event for hotelId=%s. Please try again later.",
                                searchRequest.hotelId()
                        ));
                    } else {
                        logger.info(String.format(
                                "Message sent successfully: record=%s, hash=%s",
                                result.getProducerRecord().value(),
                                hash
                        ));
                    }
                });

        return hash;
    }
}
