package com.theatre.bookingservice.dto;

import com.theatre.bookingservice.entity.BookingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long bookingId;

    private Long userId;

    private Long showId;

    private BookingStatus bookingStatus;

    private BigDecimal totalPrice;

    private LocalDateTime bookedAt;

    private List<Long> seatIds;

    // NEW

    private String status;

    private LocalDateTime lockExpiresAt;
}