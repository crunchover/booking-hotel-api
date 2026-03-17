package com.tuempresa.booking.hotel.api.usecase.create;

import com.tuempresa.booking.hotel.api.model.SearchRequest;

@FunctionalInterface
public interface CalculateHash {
    String calculateSearchHash(SearchRequest request);
}
