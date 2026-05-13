package com.theatre.bookingservice.dto;

import com.theatre.bookingservice.entity.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long bookingId;

    private Long userId;

    private Long showId;

    private BookingStatus bookingStatus;

    private Double totalPrice;

    private LocalDateTime bookedAt;

    private List<Long> seatIds;
}
