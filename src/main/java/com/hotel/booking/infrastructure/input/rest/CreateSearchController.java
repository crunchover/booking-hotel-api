package com.hotel.booking.infrastructure.input.rest;

import com.hotel.booking.domain.model.CreateSearchResponse;
import com.hotel.booking.domain.model.SearchRequest;
import com.hotel.booking.domain.port.input.CreateSearchUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Tag(name = "Hotel Search")
public class CreateSearchController {

    private final CreateSearchUseCase createSearchUseCase;

    public CreateSearchController(CreateSearchUseCase createSearchUseCase) {
        this.createSearchUseCase = createSearchUseCase;
    }

    @PostMapping("/search")
    @Operation(summary = "Register a hotel search")
    public ResponseEntity<CreateSearchResponse> createSearch(@Valid @RequestBody SearchRequest request) {
        String searchId = createSearchUseCase.apply(request);
        return ResponseEntity
                .created(URI.create("/search/" + searchId))
                .body(new CreateSearchResponse(searchId));
    }
}
