package com.hotel.booking.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hotelSearchOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotel Availability Search API")
                        .version("1.0.0")
                        .description("API for registering and counting hotel availability searches"));
    }
}
