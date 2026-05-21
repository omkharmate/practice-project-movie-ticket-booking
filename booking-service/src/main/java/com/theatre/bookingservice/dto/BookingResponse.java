package com.theatre.bookingservice.dto;

import com.theatre.bookingservice.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
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

    private LocalDateTime lockExpiresAt;
}