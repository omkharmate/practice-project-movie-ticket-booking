package com.theatre.bookingservice.client;

import com.theatre.bookingservice.dto.SeatResponse;
import com.theatre.bookingservice.dto.ShowResponse;
import com.theatre.bookingservice.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TheatreClientFallback implements FallbackFactory<TheatreClient> {

    @Override
    public TheatreClient create(Throwable cause) {

        String reason = cause.getMessage();

        return new TheatreClient() {

            @Override
            public ShowResponse getShow(Long id) {
                log.error("FALLBACK — theatre-service is DOWN, cannot get show id={}, cause={}", id, reason);
                throw new ServiceUnavailableException(
                        "Theatre service is unavailable. Cannot fetch show details. Please try again later."
                );
            }

            @Override
            public SeatResponse getSeat(Long id) {
                log.error("FALLBACK — theatre-service is DOWN, cannot get seat id={}, cause={}", id, reason);
                throw new ServiceUnavailableException(
                        "Theatre service is unavailable. Cannot fetch seat details. Please try again later."
                );
            }
        };
    }
}