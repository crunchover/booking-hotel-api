package com.hotel.booking.application.service;

import com.hotel.booking.domain.model.SearchEvent;
import com.hotel.booking.domain.port.input.ProcessSearchEventUseCase;
import com.hotel.booking.domain.port.output.SearchPersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessSearchEventService implements ProcessSearchEventUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessSearchEventService.class);

    private final SearchPersistencePort persistencePort;

    public ProcessSearchEventService(SearchPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    @Transactional
    public void accept(SearchEvent event) {
        // TODO: add metrics here
        boolean exists = persistencePort.findByHash(event.hash()).isPresent();
        if (exists) {
            log.debug("Incrementing count for hash={}", event.hash());
            persistencePort.incrementCount(event.hash());
        } else {
            log.debug("Persisting new search for hash={}", event.hash());
            persistencePort.persist(event);
        }
    }
}
