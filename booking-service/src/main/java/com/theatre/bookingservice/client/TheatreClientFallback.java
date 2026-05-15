package com.theatre.bookingservice.client;

import com.theatre.bookingservice.dto.SeatResponse;
import com.theatre.bookingservice.dto.ShowResponse;
import com.theatre.bookingservice.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TheatreClientFallback implements TheatreClient {

    @Override
    public ShowResponse getShow(Long id) {
        // This method is called automatically by Feign when theatre-service is unreachable.
        // We throw a typed exception so GlobalExceptionHandler can return a clean 503.
        log.error("FALLBACK — theatre-service is DOWN, cannot get show id={}", id);
        throw new ServiceUnavailableException(
                "Theatre service is unavailable. Cannot fetch show details. Please try again later."
        );
    }

    @Override
    public SeatResponse getSeat(Long id) {
        log.error("FALLBACK — theatre-service is DOWN, cannot get seat id={}", id);
        throw new ServiceUnavailableException(
                "Theatre service is unavailable. Cannot fetch seat details. Please try again later."
        );
    }
}