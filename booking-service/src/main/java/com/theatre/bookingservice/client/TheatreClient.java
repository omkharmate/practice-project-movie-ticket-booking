package com.theatre.bookingservice.client;

import com.theatre.bookingservice.dto.SeatResponse;
import com.theatre.bookingservice.dto.ShowResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "theatre-service",
        url = "http://localhost:8082",
        fallbackFactory = TheatreClientFallback.class   // ← was: fallback
)
public interface TheatreClient {

    @GetMapping("/shows/{id}")
    ShowResponse getShow(
            @PathVariable Long id
    );

    @GetMapping("/seats/{id}")
    SeatResponse getSeat(
            @PathVariable Long id
    );
}
