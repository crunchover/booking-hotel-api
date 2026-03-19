package com.hotel.booking.domain.port.input;

import com.hotel.booking.domain.model.SearchEvent;

import java.util.function.Consumer;

public interface ProcessSearchEventUseCase extends Consumer<SearchEvent> {}
