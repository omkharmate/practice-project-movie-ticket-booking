package com.theatre.bookingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookedSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long seatId;

    private Long showId;

    @Enumerated(EnumType.STRING)
    private SeatLockStatus status;

    private LocalDateTime lockExpiresAt;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}