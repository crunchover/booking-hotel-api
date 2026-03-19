package com.hotel.booking.domain.port.input;

import com.hotel.booking.domain.model.SearchCountResponse;

import java.util.function.Function;

public interface GetSearchCountUseCase extends Function<String, SearchCountResponse> {}
