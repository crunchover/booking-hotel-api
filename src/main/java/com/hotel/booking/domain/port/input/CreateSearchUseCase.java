package com.hotel.booking.domain.port.input;

import com.hotel.booking.domain.model.SearchRequest;

import java.util.function.Function;

public interface CreateSearchUseCase extends Function<SearchRequest, String> {}
