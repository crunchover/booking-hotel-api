package com.tuempresa.booking.hotel.api.endpoint;

import com.tuempresa.booking.hotel.api.model.SearchCountResponse;
import com.tuempresa.booking.hotel.api.usecase.search.GetSearchCountUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(GetSearchCountEndpoint.PATH)
public class GetSearchCountEndpoint {

    public static final String PATH = "/booking";
    private static final String COUNT_SUB_PATH = "/count";

    private final GetSearchCountUseCase getSearchCountUseCase;

    public GetSearchCountEndpoint(GetSearchCountUseCase getSearchCountUseCase) {
        this.getSearchCountUseCase = getSearchCountUseCase;
    }

    @GetMapping(COUNT_SUB_PATH)
    public ResponseEntity<SearchCountResponse> getSearchCount(@RequestParam final String searchId) {
        SearchCountResponse response = getSearchCountUseCase.execute(searchId);
        return ResponseEntity.ok(response);
    }
}