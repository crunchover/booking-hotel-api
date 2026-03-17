package com.tuempresa.booking.hotel.api.usecase.search;

import com.tuempresa.booking.hotel.api.model.SearchCountResponse;

public interface GetSearchCountUseCase {
    SearchCountResponse execute(String hash);
}
