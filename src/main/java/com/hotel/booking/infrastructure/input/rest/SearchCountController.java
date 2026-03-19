package com.hotel.booking.infrastructure.input.rest;

import com.hotel.booking.domain.model.SearchCountResponse;
import com.hotel.booking.domain.port.input.GetSearchCountUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "Hotel Search")
public class SearchCountController {

    private final GetSearchCountUseCase getSearchCountUseCase;

    public SearchCountController(GetSearchCountUseCase getSearchCountUseCase) {
        this.getSearchCountUseCase = getSearchCountUseCase;
    }

    @GetMapping("/count")
    @Operation(summary = "Get count of identical searches")
    public ResponseEntity<SearchCountResponse> getCount(
            @Parameter(description = "Search identifier returned by POST /search")
            @RequestParam @NotBlank(message = "searchId is required") String searchId) {
        return ResponseEntity.ok(getSearchCountUseCase.apply(searchId));
    }
}
