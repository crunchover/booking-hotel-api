package com.tuempresa.booking.hotel.api.endpoint;

import com.tuempresa.booking.hotel.api.model.SearchEvent;
import com.tuempresa.booking.hotel.api.usecase.listener.HotelSearchEventListenerUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class HotelSearchEventListenerEndpoint {

    private static final Logger logger = Logger.getLogger(HotelSearchEventListenerEndpoint.class.getName());
    private final HotelSearchEventListenerUseCase hotelSearchEventListenerUseCase;

    public HotelSearchEventListenerEndpoint(HotelSearchEventListenerUseCase hotelSearchEventListenerUseCase) {
        this.hotelSearchEventListenerUseCase = hotelSearchEventListenerUseCase;
    }

    @KafkaListener(
            topics = "hotel_availability_searches",
            groupId = "hotel-search-consumer-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(SearchEvent event) {
        logger.info(() -> String.format(
                "Received SearchEvent: hotelId=%s, checkIn=%s, checkOut=%s, ages=%s",
                event.hotelId(),
                event.checkIn(),
                event.checkOut(),
                event.ages()
        ));
        final var id = hotelSearchEventListenerUseCase.apply(event);
        logger.info(() -> String.format(
                "Persist Successfully SearchEvent: hotelId=%s, checkIn=%s, checkOut=%s, ages=%s with id=%s",
                event.hotelId(),
                event.checkIn(),
                event.checkOut(),
                event.ages(),
                id
        ));

    }
}
