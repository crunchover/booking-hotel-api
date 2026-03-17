package com.tuempresa.booking.hotel.api.endpoint;

import com.tuempresa.booking.hotel.api.model.CreateSearchResponse;
import com.tuempresa.booking.hotel.api.model.SearchRequest;
import com.tuempresa.booking.hotel.api.usecase.create.CreateSearchUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(CreateSearchEndPoint.PATH)
public class CreateSearchEndPoint {

    public static final String PATH = "/booking";
    private static final String SEARCH_SUB_PATH = "/search";

    private final CreateSearchUseCase createSearchUseCase;

    public CreateSearchEndPoint(CreateSearchUseCase createSearchUseCase) {
        this.createSearchUseCase = createSearchUseCase;
    }

    @PostMapping(SEARCH_SUB_PATH)
    public ResponseEntity<CreateSearchResponse> search(@Valid @RequestBody SearchRequest searchRequest) {
        final String searchId = createSearchUseCase.apply(searchRequest);
        URI location = URI.create(PATH + SEARCH_SUB_PATH + "/" + searchId);
        return ResponseEntity.created(location)
                .body(new CreateSearchResponse(searchId));
    }
}
